package salma.elbahlouli.elderhealthappmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import salma.elbahlouli.elderhealthappmobile.adapter.MedicineAdapter;
import salma.elbahlouli.elderhealthappmobile.api.ApiClient;
import salma.elbahlouli.elderhealthappmobile.api.ApiService;
import salma.elbahlouli.elderhealthappmobile.model.Medicine;

public class MedicineListActivity extends AppCompatActivity {

    private Button buttonAddMedicine;
    private RecyclerView recyclerView;
    private MedicineAdapter medicineAdapter;
    private List<Medicine> medicines = new ArrayList<>();
    private ApiService medicineApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        initializeViews();
        setupRecyclerView();
        fetchMedicines();

        buttonAddMedicine.setOnClickListener(v -> {

            String userId = getIntent().getStringExtra("user_id");

            // Créer un Intent pour démarrer l'activité MedicineActivity
            Intent intent = new Intent(MedicineListActivity.this, MedicineActivity.class);
            // Passer le user_id en tant que paramètre
            intent.putExtra("user_id", userId);
            // Démarrer l'activité
            startActivity(intent);
        });

    }

    private void initializeViews() {
        buttonAddMedicine = findViewById(R.id.buttonAddMedicine);
        recyclerView = findViewById(R.id.recyclerViewMeds);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicineAdapter = new MedicineAdapter(
                medicines,
                this::confirmDeleteMedicine,
                this::showEditMedicineDialog
        );
        recyclerView.setAdapter(medicineAdapter);
    }

    private void fetchMedicines() {
        String userId = getIntent().getStringExtra("user_id");
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "Identifiant utilisateur introuvable.", Toast.LENGTH_SHORT).show();
            return;
        }

        medicineApi = ApiClient.getRetrofitInstance().create(ApiService.class);
        medicineApi.getMedicinesByUser(userId).enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    medicines.clear();
                    medicines.addAll(response.body());
                    medicineAdapter.updateList(medicines);
                } else {
                    Toast.makeText(MedicineListActivity.this, "Erreur lors du chargement des médicaments.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Medicine>> call, Throwable t) {
                Toast.makeText(MedicineListActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmDeleteMedicine(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation de suppression")
                .setMessage("Voulez-vous vraiment supprimer ce médicament ?")
                .setPositiveButton("Oui", (dialog, which) -> deleteMedicine(position))
                .setNegativeButton("Non", null)
                .show();

    }

    private void deleteMedicine(int position) {
        Medicine medicine = medicines.get(position);

        medicineApi.deleteMedicine(medicine.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    medicines.remove(position);
                    medicineAdapter.notifyItemRemoved(position);
                    medicineAdapter.removeAt(position);
                    Toast.makeText(MedicineListActivity.this, "Médicament supprimé avec succès.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MedicineListActivity.this, "Erreur lors de la suppression.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MedicineListActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditMedicineDialog(Medicine medicine, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_medicine, null);
        builder.setView(dialogView);

        EditText inputNom = dialogView.findViewById(R.id.inputNom);
        EditText inputDosage = dialogView.findViewById(R.id.inputDosage);
        EditText inputHeure = dialogView.findViewById(R.id.inputHeure);
        Button saveButton = dialogView.findViewById(R.id.btnSave);

        inputNom.setText(medicine.getNom());
        inputDosage.setText(medicine.getDosage());
        inputHeure.setText(medicine.getHeure());

        AlertDialog dialog = builder.create();

        saveButton.setOnClickListener(v -> {
            String updatedNom = inputNom.getText().toString().trim();
            String updatedDosage = inputDosage.getText().toString().trim();
            String updatedHeure = inputHeure.getText().toString().trim();

            if (updatedNom.isEmpty() || updatedDosage.isEmpty() || updatedHeure.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            updateMedicine(medicine.getId(), updatedNom, updatedDosage, updatedHeure, position);
            dialog.dismiss();
        });

        dialog.show();
        fetchMedicines();
    }

    private void updateMedicine(int id, String nom, String dosage, String heure, int position) {
        Medicine updatedMedicine = new Medicine(nom, dosage, heure, null);
        medicineApi.updateMedicine(id, updatedMedicine).enqueue(new Callback<Medicine>() {
            @Override
            public void onResponse(Call<Medicine> call, Response<Medicine> response) {
                if (response.isSuccessful() && response.body() != null) {
                    medicines.set(position, response.body());
                    medicineAdapter.notifyItemChanged(position);
                    Toast.makeText(MedicineListActivity.this, "Médicament mis à jour avec succès.", Toast.LENGTH_SHORT).show();
                    fetchMedicines();
                } else {
                    Toast.makeText(MedicineListActivity.this, "Échec de la mise à jour.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Medicine> call, Throwable t) {
                Toast.makeText(MedicineListActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
