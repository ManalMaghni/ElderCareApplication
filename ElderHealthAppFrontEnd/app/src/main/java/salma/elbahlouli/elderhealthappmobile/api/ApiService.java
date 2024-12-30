package salma.elbahlouli.elderhealthappmobile.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import salma.elbahlouli.elderhealthappmobile.model.Bilan;
import salma.elbahlouli.elderhealthappmobile.DTO.LoginRequest;
import salma.elbahlouli.elderhealthappmobile.DTO.LoginResponse;
import salma.elbahlouli.elderhealthappmobile.model.Medicine;
import salma.elbahlouli.elderhealthappmobile.DTO.RegisterRequest;
import salma.elbahlouli.elderhealthappmobile.DTO.RegisterResponse;

public interface ApiService {
    //@GET("/api/data")
    //Call<String> getData()      ;

    //@POST("/api/data")
    //Call<String> postData(@Body String data);

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/users") // Mettez ici le bon endpoint
    Call<RegisterResponse> register(@Body RegisterRequest request);



    @GET("api/bilans/users/{userId}/bilans")
    Call<List<Bilan>> getBilansByUser(@Path("userId") String userId);


    @POST("/api/bilans/users/{userId}")
    Call<Bilan> createBilan(@Path("userId") String userId, @Body Bilan bilan);

    @DELETE("api/bilans/{id}")
    Call<Void> deleteBilan(@Path("id") Long id);
    @PUT("api/bilans/{id}")
    Call<Bilan> updateBilan(@Path("id") Long id, @Body Bilan bilan);


    @GET("/api/users/login/{login}")
    Call<RegisterResponse> getUserDetails(@Path("login") String login);

    @GET("api/medicines")
    Call<List<Medicine>> getAllMedicines();
    @PUT("api/medicines/{id}")
    Call<Medicine> updateMedicine(@Path("id") int id, @Body Medicine medicine);

    @POST("api/medicines")
    Call<Medicine> addMedicine(@Body Medicine medicine);

    @DELETE("api/medicines/{id}")
    Call<Void> deleteMedicine(@Path("id") int id);
    @GET("api/users/{userId}/medicines")
    Call<List<Medicine>> getMedicinesByUser(@Path("userId") String userId);
    @POST("/api/medicines/user/{userId}/medicines")
    Call<Medicine> createMedicine(@Path("userId") String userId, @Body Medicine medicine);







}

