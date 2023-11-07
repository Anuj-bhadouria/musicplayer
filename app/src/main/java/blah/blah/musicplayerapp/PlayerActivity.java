package blah.blah.musicplayerapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();

        String title = intent.getStringExtra("songTitle");
        String artist = intent.getStringExtra("songArtist");
        String imageUrl = intent.getStringExtra("imageUrl");
        String previewUrl = intent.getStringExtra("previewUrl");

        // song details btavse
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView artistTextView = findViewById(R.id.artistTextView);
        ImageView imageView = findViewById(R.id.imageView);

        titleTextView.setText(title);
        artistTextView.setText(artist);
        Picasso.get().load(imageUrl).into(imageView);

        // preview apse
        imageView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(previewUrl));
            startActivity(browserIntent);
        });
    }
}
