package salma.elbahlouli.elderhealthappmobile;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import salma.elbahlouli.elderhealthappmobile.api.ApiClient;
import salma.elbahlouli.elderhealthappmobile.api.ApiService;
import salma.elbahlouli.elderhealthappmobile.model.Medicine;

public class MedicineActivity extends AppCompatActivity {
    private EditText editNom, editDosage, editHeurePris;
    private Button buttonAdd, buttonViewMeds;
    private ApiService medicineApi;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        String userId = getIntent().getStringExtra("user_id");

        if (userId == null) {
            Toast.makeText(this, "ID utilisateur invalide", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ID utilisateur correcte", Toast.LENGTH_SHORT).show();
        }


        initializeViews();
        medicineApi = ApiClient.getRetrofitInstance().create(ApiService.class);

        buttonAdd.setOnClickListener(v -> {
            String nom = editNom.getText().toString().trim();
            String dosage = editDosage.getText().toString().trim();
            String heurePris = editHeurePris.getText().toString().trim();

            if (nom.isEmpty() || dosage.isEmpty() || heurePris.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }


            Medicine medicine = new Medicine(nom, dosage, heurePris, null);
            addMedicine(nom, dosage, heurePris);
        });
    }

    private void initializeViews() {
        editNom = findViewById(R.id.editNom);
        editDosage = findViewById(R.id.editDosage);
        editHeurePris = findViewById(R.id.editHeurePris);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonViewMeds = findViewById(R.id.buttonViewMeds);
    }

    private void addMedicine(String nom, String dosage, String heure) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        String userId = getIntent().getStringExtra("user_id");
        Medicine newMedicine = new Medicine(nom, dosage, heure, null);
        newMedicine.setId(4);

        apiService.createMedicine(userId, newMedicine).enqueue(new Callback<Medicine>() {
            @Override
            public void onResponse(Call<Medicine> call, Response<Medicine> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MedicineActivity.this, "Médicament ajouté avec succès", Toast.LENGTH_SHORT).show();
                    clearFields(); // Effacer les champs ici
                } else {
                    Toast.makeText(MedicineActivity.this, "Échec de l'ajout du médicament", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Medicine> call, Throwable t) {
                Log.e("API_ERROR", "Erreur lors de l'ajout du médicament : " + t.getMessage());
                Toast.makeText(MedicineActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void clearFields() {
        editNom.setText("");
        editDosage.setText("");
        editHeurePris.setText("");
    }


}
