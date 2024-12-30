package salma.elbahlouli.elderhealthappmobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        CardView exit = findViewById(R.id.cardFDBack);
        exit.setOnClickListener(view -> startActivity(new Intent(FindDoctorActivity.this, HomeActivity.class)));

        CardView familyphysician = findViewById(R.id.cardFDFamilyPhysician);
        familyphysician.setOnClickListener(view -> openMapsSearch("Geriatrician"));

        CardView dietician = findViewById(R.id.cardFDDietician);
        dietician.setOnClickListener(view -> openMapsSearch("Rheumatologist"));

        CardView dentist = findViewById(R.id.cardFDDentist);
        dentist.setOnClickListener(view -> openMapsSearch("Psychiatrist"));

        CardView surgeon = findViewById(R.id.cardFDSurgeon);
        surgeon.setOnClickListener(view -> openMapsSearch("Surgeon"));

        CardView cardiologist = findViewById(R.id.cardFDCardiologist);
        cardiologist.setOnClickListener(view -> openMapsSearch("Cardiologist"));
    }

    /**
     * Opens Google Maps with a search query for the specified doctor type.
     *
     * @param doctorType The type of doctor to search for (e.g., "Family Physician").
     */
    private void openMapsSearch(String doctorType) {
        String query = Uri.encode(doctorType + " near me");
        Uri mapsUri = Uri.parse("geo:0,0?q=" + query);
        Intent intent = new Intent(Intent.ACTION_VIEW, mapsUri);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // If Google Maps is not installed, use a browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, mapsUri);
            startActivity(browserIntent);
        }
    }
}
