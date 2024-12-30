// HealthArticlesDetailsActivity.java
package salma.elbahlouli.elderhealthappmobile;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HealthArticlesDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article_details);

        TextView titleTextView = findViewById(R.id.articleTitleTextView);
        TextView contentTextView = findViewById(R.id.articleContentTextView);
        ImageView articleImageView = findViewById(R.id.articleImageView);

        String topic = getIntent().getStringExtra("topic");

        if (topic != null) {
            titleTextView.setText(topic);
            if (topic.equals("Diabetes")) {
                contentTextView.setText(Html.fromHtml("Fasting Blood Sugar (before eating): <b>A normal fasting blood sugar level</b> is between 70 and 99 mg/dL (3.9 to 5.5 mmol/L).\n" +
                        "Post-meal Blood Sugar (2 hours after eating): <b>A normal post-meal blood sugar level</b> should be less than 140 mg/dL (7.8 mmol/L).\n" +
                        "Random Blood Sugar: <b>A random blood sugar level</b>, which is measured at any time of day, should generally be less than 140 mg/dL (7.8 mmol/L)."));

                articleImageView.setImageResource(R.drawable.diabete);
            } else if (topic.equals("Walking and Its Effects")) {
                contentTextView.setText(Html.fromHtml("Walking has numerous health benefits, including improving <b>cardiovascular health</b>, aiding <b>weight loss</b>, and reducing <b>stress</b>. Regular walking can also help lower <b>blood pressure</b>, enhance <b>muscle strength</b>, and improve <b>joint health</b>. It is an excellent low-impact exercise that is accessible to almost anyone, regardless of age or fitness level.\n\n" +
                        "<b>Improving cardiovascular health</b>: Walking increases your heart rate, improving circulation and reducing the risk of <b>heart disease</b>.\n\n" +
                        "<b>Weight loss</b>: Walking regularly helps burn calories, which contributes to weight loss and fat reduction.\n\n" +
                        "<b>Reducing stress</b>: Walking is a great way to reduce stress and clear your mind. It promotes the release of <b>endorphins</b>, the body's natural mood elevators.\n\n" +
                        "<b>Joint health</b>: Walking improves the flexibility of joints and helps prevent conditions like <b>arthritis</b>. It also supports bone health by increasing <b>bone density</b>.\n\n" +
                        "In conclusion, walking is a simple yet effective exercise with a variety of benefits for your overall health."));
                articleImageView.setImageResource(R.drawable.walking);
            }

        } else if (topic.equals("Emergency Guidelines")) {
            contentTextView.setText(Html.fromHtml("In case of a <b>health emergency</b>, follow these steps:\n\n" +
                    "1. <b>Call emergency services</b> immediately. Provide clear information about the situation, including the location and nature of the emergency.\n\n" +
                    "2. <b>Administer first aid</b> if you are trained. Perform the necessary steps to stabilize the person until help arrives. For example, if the person is unconscious, check their airway and provide CPR if needed.\n\n" +
                    "3. If there is <b>severe bleeding</b>, apply pressure to the wound to stop the bleeding. Elevate the affected area if possible.\n\n" +
                    "4. In case of a <b>heart attack</b>, if trained, use <b>CPR</b> and an <b>AED</b> if available.\n\n" +
                    "5. If the person is having a <b>seizure</b>, ensure they are in a safe area and protect their head from injury. Do not try to restrain them.\n\n" +
                    "6. After calling emergency services, stay on the line and follow their instructions until help arrives.\n\n" +
                    "Always be prepared and know the basic emergency guidelines to ensure a prompt and effective response in a critical situation."));
            articleImageView.setImageResource(R.drawable.walking);
        }

    }
    }

