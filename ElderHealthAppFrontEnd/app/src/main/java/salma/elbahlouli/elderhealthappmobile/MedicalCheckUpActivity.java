package salma.elbahlouli.elderhealthappmobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import salma.elbahlouli.elderhealthappmobile.adapter.BilanAdapter;
import salma.elbahlouli.elderhealthappmobile.api.ApiClient;
import salma.elbahlouli.elderhealthappmobile.api.ApiService;
import salma.elbahlouli.elderhealthappmobile.model.Bilan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MedicalCheckUpActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Bilan> bilanList = new ArrayList<>();
    private List<Bilan> originalBilanList = new ArrayList<>(); // Liste originale
    private BilanAdapter bilanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_check_up);

        recyclerView = findViewById(R.id.recyclerViewBilans);
        setupRecyclerView();

        FloatingActionButton addTestButton = findViewById(R.id.fabAddTest);
        addTestButton.setOnClickListener(view -> showAddTestDialog());

        setupSearchView();
        fetchBilans();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bilanAdapter = new BilanAdapter(bilanList,
                (bilan, position) -> deleteBilanFromServer(bilan.getId(), position),
                (bilan, position) -> showEditTestDialog(bilan, position)); // Passer l'écouteur pour la modification
        recyclerView.setAdapter(bilanAdapter);
    }

    private void fetchBilans() {
        String userId = getIntent().getStringExtra("user_id");
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "Identifiant utilisateur introuvable.", Toast.LENGTH_SHORT).show();
            return;

        }
        Log.d("MedicalCheckUpActivity", "User ID: " + userId);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        apiService.getBilansByUser(userId).enqueue(new Callback<List<Bilan>>() {
            @Override
            public void onResponse(Call<List<Bilan>> call, Response<List<Bilan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bilanList.clear();
                    originalBilanList.clear();
                    bilanList.addAll(response.body());
                    originalBilanList.addAll(response.body()); // Conserver les données originales
                    bilanAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MedicalCheckUpActivity.this, "Failed to fetch bilans.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bilan>> call, Throwable t) {
                Log.e("API_ERROR", "Error fetching bilans: " + t.getMessage());
                Toast.makeText(MedicalCheckUpActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterTests(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTests(newText);
                return false;
            }
        });
    }

    private void filterTests(String query) {
        List<Bilan> filteredList = new ArrayList<>();
        for (Bilan bilan : originalBilanList) { // Filtrer la liste originale
            String nomTest = bilan.getNomTest();

            // Vérification si nomTest est nul avant d'appeler toLowerCase
            if (nomTest != null && nomTest.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(bilan);
            } else if (nomTest == null && query.isEmpty()) {
                // Ajouter les éléments dont le nomTest est nul seulement si la recherche est vide
                filteredList.add(bilan);
            }
        }
        bilanAdapter.updateList(filteredList); // Mettre à jour l'adaptateur avec la liste filtrée
    }

    private void showAddTestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialolog_add_test, null);
        builder.setView(dialogView);

        EditText inputNomTest = dialogView.findViewById(R.id.inputNomTest);
        EditText inputValeurTest = dialogView.findViewById(R.id.inputValeurTest);
        Button addButton = dialogView.findViewById(R.id.btnAddTest);

        AlertDialog dialog = builder.create();

        addButton.setOnClickListener(view -> {
            String nomTest = inputNomTest.getText().toString().trim();
            String valeurTest = inputValeurTest.getText().toString().trim();

            if (nomTest.isEmpty() || valeurTest.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            addBilan(nomTest, valeurTest);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void deleteBilanFromServer(Long bilanId, int position) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.deleteBilan(bilanId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    bilanList.remove(position);
                    originalBilanList.remove(position); // Mettre à jour la liste originale
                    bilanAdapter.notifyItemRemoved(position);
                    Toast.makeText(MedicalCheckUpActivity.this, "Test supprimé avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MedicalCheckUpActivity.this, "Échec de la suppression", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MedicalCheckUpActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBilan(String nomTest, String valeurTest) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        String userId = getIntent().getStringExtra("user_id");
        Bilan newBilan = new Bilan(null, nomTest, valeurTest);

        apiService.createBilan(userId, newBilan).enqueue(new Callback<Bilan>() {
            @Override
            public void onResponse(Call<Bilan> call, Response<Bilan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bilanList.add(response.body());
                    originalBilanList.add(response.body());
                    bilanAdapter.notifyDataSetChanged();
                    Toast.makeText(MedicalCheckUpActivity.this, "Bilan ajouté avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MedicalCheckUpActivity.this, "Échec de l'ajout du bilan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bilan> call, Throwable t) {
                Log.e("API_ERROR", "Erreur lors de l'ajout du bilan : " + t.getMessage());
                Toast.makeText(MedicalCheckUpActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showEditTestDialog(Bilan bilan, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Assure-toi que le bon layout est utilisé
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_test, null);
        builder.setView(dialogView);

        // Récupérer les vues du layout du dialogue
        EditText inputNomTest = dialogView.findViewById(R.id.inputNomTest);
        EditText inputValeurTest = dialogView.findViewById(R.id.inputValeurTest);
        Button saveButton = dialogView.findViewById(R.id.btnSaveTest);

        // Pré-remplir les champs avec les valeurs existantes du test
        inputNomTest.setText(bilan.getNomTest());
        inputValeurTest.setText(bilan.getValeur());

        // Créer l'alerte et l'afficher
        AlertDialog dialog = builder.create();

        saveButton.setOnClickListener(view -> {
            String updatedNomTest = inputNomTest.getText().toString().trim();
            String updatedValeurTest = inputValeurTest.getText().toString().trim();

            if (updatedNomTest.isEmpty() || updatedValeurTest.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Mettre à jour le test
            updateBilan(bilan.getId(), updatedNomTest, updatedValeurTest, position);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateBilan(Long id, String nomTest, String valeurTest, int position) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Bilan updatedBilan = new Bilan(id, nomTest, valeurTest);

        apiService.updateBilan(id, updatedBilan).enqueue(new Callback<Bilan>() {
            @Override
            public void onResponse(Call<Bilan> call, Response<Bilan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bilanList.set(position, response.body());
                    originalBilanList.set(position, response.body()); // Mettre à jour la liste originale
                    bilanAdapter.notifyItemChanged(position);
                    Toast.makeText(MedicalCheckUpActivity.this, "Bilan mis à jour avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MedicalCheckUpActivity.this, "Échec de la mise à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bilan> call, Throwable t) {
                Toast.makeText(MedicalCheckUpActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
