package salma.elbahlouli.elderhealthappmobile;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import salma.elbahlouli.elderhealthappmobile.api.ApiClient;
import salma.elbahlouli.elderhealthappmobile.api.ApiService;
import salma.elbahlouli.elderhealthappmobile.DTO.RegisterResponse;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtName, txtLogin, txtTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialisation des vues
        txtName = findViewById(R.id.txtName);
        txtLogin = findViewById(R.id.txtLogin);
        txtTelephone = findViewById(R.id.txtTelephone);

        // Récupérer les données passées par l'Intent (login et user_id)
        String username = getIntent().getStringExtra("login");
        String userId = getIntent().getStringExtra("user_id");

        // Vérifier si les données sont valides
        if (username != null && !username.isEmpty()) {
            fetchUserData(username);
        } else {
            Toast.makeText(this, "Utilisateur non trouvé !", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchUserData(String username) {
        // Création de l'instance Retrofit et ApiService
        Retrofit retrofit = ApiClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Appel de l'API pour récupérer les détails de l'utilisateur
        Call<RegisterResponse> call = apiService.getUserDetails(username);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Récupération des données utilisateur depuis la réponse
                    RegisterResponse user = response.body();

                    // Mise à jour des champs TextView avec des vérifications de nullité
                    txtName.setText("Nom: " + (user.getNom() != null ? user.getNom() : "Non spécifié"));
                    txtLogin.setText("Login: " + (user.getLogin() != null ? user.getLogin() : "Non spécifié"));
                    txtTelephone.setText("Téléphone: " + (user.getTelephone() != null ? user.getTelephone() : "Non spécifié"));
                } else {
                    // Gestion d'une réponse non réussie
                    String errorMessage = "Impossible de récupérer les détails de l'utilisateur.";
                    try {
                        // Tenter d'extraire un message d'erreur JSON
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            JSONObject errorJson = new JSONObject(errorBody);
                            errorMessage = errorJson.optString("error", "Erreur inconnue");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Afficher l'erreur sous une forme plus lisible
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                // Gestion des erreurs réseau ou autres exceptions
                Toast.makeText(ProfileActivity.this, "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
