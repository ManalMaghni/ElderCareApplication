package salma.elbahlouli.elderhealthappmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import salma.elbahlouli.elderhealthappmobile.api.ApiClient;
import salma.elbahlouli.elderhealthappmobile.api.ApiService;
import salma.elbahlouli.elderhealthappmobile.DTO.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import salma.elbahlouli.elderhealthappmobile.DTO.LoginResponse;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        edUsername = findViewById(R.id.editTextLoginUsername);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        tvRegister = findViewById(R.id.textViewNewUSer);

        // Handle login button click
        btnLogin.setOnClickListener(view -> {
            String login = edUsername.getText().toString();
            String password = edPassword.getText().toString();

            // Validate input
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(login, password);
            }
        });

        // Redirect to registration activity
        tvRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginUser(String login, String password) {
        Retrofit retrofit = ApiClient.getRetrofitInstance();
        ApiService authService = retrofit.create(ApiService.class);

        LoginRequest loginRequest = new LoginRequest(login, password);

        Call<LoginResponse> call = authService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    // Save token and handle successful login
                    //String token = loginResponse != null ? loginResponse.getToken() : null;
                    SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", login);
                    editor.putString("user_id",loginResponse.getUserId()+"");
                    //editor.putString("token", token);
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Login failed", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
