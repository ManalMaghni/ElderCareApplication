package salma.elbahlouli.elderhealthappmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import salma.elbahlouli.elderhealthappmobile.api.ApiClient;
import salma.elbahlouli.elderhealthappmobile.api.ApiService;
import salma.elbahlouli.elderhealthappmobile.model.Medicine;
import salma.elbahlouli.elderhealthappmobile.Reminder.MedicineAlarmScheduler;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fetchMedicines();
        // Récupérer les préférences partagées
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("login", "");
        String user_id = sharedPreferences.getString("user_id", "");
        Log.d("HomeActivity", "User ID: " + user_id);


        // Afficher un message de bienvenue
        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

        // Si l'utilisateur n'est pas connecté, rediriger vers la page de connexion
        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please log in first.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // CardView pour quitter la session
        CardView exit = findViewById(R.id.cardExit);
        exit.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });

        // CardView pour rechercher un médecin
        findViewById(R.id.cardFindDoctor).setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, FindDoctorActivity.class);
            intent.putExtra("user_id", user_id);  // Passer l'user_id
            intent.putExtra("login", username);   // Passer le login
            startActivity(intent);
        });

        // CardView pour acheter des médicaments
        findViewById(R.id.cardBuyMedicine).setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MedicineListActivity.class);
            intent.putExtra("user_id", user_id);  // Passer l'user_id
            intent.putExtra("login", username);   // Passer le login
            startActivity(intent);
        });

        // CardView pour consulter les articles sur la santé
        findViewById(R.id.cardHealthDoctor).setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, HealthArticlesActivity.class);
            intent.putExtra("user_id", user_id);  // Passer l'user_id
            intent.putExtra("login", username);   // Passer le login
            startActivity(intent);
        });

        // CardView pour les tests médicaux
        findViewById(R.id.cardLabTest).setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MedicalCheckUpActivity.class);
            intent.putExtra("user_id", user_id);  // Passer l'user_id
            intent.putExtra("login", username);   // Passer le login
            startActivity(intent);

        });

        // CardView pour afficher le profil
        findViewById(R.id.viewprofile).setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("user_id", user_id);  // Passer l'user_id
            intent.putExtra("login", username);   // Passer le login
            startActivity(intent);
        });
    }
    private void fetchMedicines() {

        List<Medicine> medicines = new ArrayList<>();
        ApiService medicineApi;
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);

        String userId = sharedPreferences.getString("user_id", "");

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


                    // Programmer les notifications pour chaque médicament
                    for (Medicine medicine : medicines) {
                       /*
                        NotificationScheduler.scheduleMedicineNotification(
                                HomeActivity.this,
                                medicine
                        );

                        */
                        MedicineAlarmScheduler.scheduleAlarm(
                                HomeActivity.this,
                                medicine
                        );

                    }
                } else {
                    Toast.makeText(HomeActivity.this,
                            "Erreur lors du chargement des médicaments.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Medicine>> call, Throwable t) {
                Toast.makeText(HomeActivity.this,
                        "Erreur de connexion : " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
