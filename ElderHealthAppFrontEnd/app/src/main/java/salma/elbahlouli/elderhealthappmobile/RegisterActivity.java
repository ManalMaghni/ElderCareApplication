package salma.elbahlouli.elderhealthappmobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import salma.elbahlouli.elderhealthappmobile.api.ApiClient;
import salma.elbahlouli.elderhealthappmobile.api.ApiService;
import salma.elbahlouli.elderhealthappmobile.DTO.RegisterRequest;
import salma.elbahlouli.elderhealthappmobile.DTO.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText edNom, edLogin, edTelephone, edPassword, edConfirm;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Liaison des vues XML
        edNom = findViewById(R.id.editTextLTDFullname);
        edLogin = findViewById(R.id.editTextLTDUsername);
        edTelephone = findViewById(R.id.editTextLTDPhone);
        edPassword = findViewById(R.id.editTextLTDPincode);
        edConfirm = findViewById(R.id.editTextLTDContact);  // Champ pour la confirmation du mot de passe
        btn = findViewById(R.id.buttonLTDBooking);  // Bouton d'inscription
        tv = findViewById(R.id.textViewExistingUser);  // Texte pour redirection vers login

        // Action lorsqu'un utilisateur a déjà un compte
        tv.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        // Action du bouton d'inscription
        btn.setOnClickListener(view -> {
            String nom = edNom.getText().toString().trim();
            String login = edLogin.getText().toString().trim();
            String telephone = edTelephone.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            String confirm = edConfirm.getText().toString().trim();

            // Validation des champs
            if (nom.isEmpty() || login.isEmpty() || telephone.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                if (password.equals(confirm)) {
                    if (isValid(password)) {
                        registerUser(nom, login, telephone, password);
                    } else {
                        Toast.makeText(getApplicationContext(), "Le mot de passe doit contenir au moins 8 caractères, une lettre, un chiffre et un symbole spécial", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String nom, String login, String telephone, String password) {
        Retrofit retrofit = ApiClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        RegisterRequest request = new RegisterRequest(nom, login, telephone, password);
        Call<RegisterResponse> call = apiService.register(request);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(getApplicationContext(), "Inscription réussie " + registerResponse.getNom(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Échec de l'inscription. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("RegisterError", t.getMessage());
                Toast.makeText(getApplicationContext(), "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isValid(String passwordhere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordhere.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < passwordhere.length(); p++) {
                if (Character.isLetter(passwordhere.charAt(p))) {
                    f1 = 1;
                }
            }
            for (int r = 0; r < passwordhere.length(); r++) {
                if (Character.isDigit(passwordhere.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < passwordhere.length(); s++) {
                char c = passwordhere.charAt(s);
                if ((c >= 33 && c <= 46) || c == 64) {
                    f3 = 1;
                }
            }
            return f1 == 1 && f2 == 1 && f3 == 1;
        }
    }
}
