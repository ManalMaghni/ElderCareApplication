package salma.elbahlouli.elderhealthappmobile.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    // Singleton method to get Retrofit instance
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Create logging interceptor
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Log the body of the request and response

            // Create OkHttpClient with the logging interceptor
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor) // Add the interceptor
                    .build();

            // Build Retrofit instance with the custom OkHttpClient
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Your base URL
                    .client(okHttpClient) // Use the OkHttpClient with interceptor
                    .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter
                    .build();
        }
        return retrofit;
    }
}
