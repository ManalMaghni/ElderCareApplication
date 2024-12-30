package salma.elbahlouli.elderhealthappmobile;





import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HealthArticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article);

        // List of health topics
        ArrayList<String> healthTopics = new ArrayList<>();
        healthTopics.add("Diabetes");
        healthTopics.add("Walking and Its Effects");
        healthTopics.add("Emergency Guidelines");

        // Setting up the ListView
        ListView listView = findViewById(R.id.healthTopicsListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, healthTopics);
        listView.setAdapter(adapter);

        // Handling item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTopic = healthTopics.get(position);
                Intent intent = new Intent(HealthArticlesActivity.this, HealthArticlesDetailsActivity.class);
                intent.putExtra("topic", selectedTopic);
                startActivity(intent);
            }
        });
    }
}
