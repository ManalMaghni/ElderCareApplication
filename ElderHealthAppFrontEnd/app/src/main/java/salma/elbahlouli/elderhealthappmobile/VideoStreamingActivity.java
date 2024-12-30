package salma.elbahlouli.elderhealthappmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class VideoStreamingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);
        WebView webView = findViewById(R.id.webView1);

// Échapper les guillemets dans la chaîne pour éviter des erreurs
        String video = "<iframe width=\"100%\" height=\"315\" src=\"https://www.youtube.com/embed/ffu93KhnSKg?si=aL0TMirjLp2pIqwU\" " +
                "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" " +
                "referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";

        webView.loadData(video, "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

    }}