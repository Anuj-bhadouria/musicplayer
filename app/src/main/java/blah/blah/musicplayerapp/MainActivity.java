package blah.blah.musicplayerapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LAST_FM_API_URL = "http://ws.audioscrobbler.com/2.0/";
    private static final String API_KEY = "9947dc09d788e930e7b07f7d28a2f9f0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new FetchSongsTask().execute();
    }

    private class FetchSongsTask extends AsyncTask<Void, Void, ArrayList<Song>> {
        @Override
        protected ArrayList<Song> doInBackground(Void... voids) {
            return fetchSongsFromApi();
        }

        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);

            //Adapter bnayo
            SongAdapter adapter = new SongAdapter(MainActivity.this, songs);

            // eni id api nd connect karyo
            ListView listView = findViewById(R.id.songList);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener((parent, view, position, id) -> {
                // select song
                Song selectedSong = songs.get(position);

                // now strt player activity
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("songTitle", selectedSong.getTitle());
                intent.putExtra("songArtist", selectedSong.getArtist());
                intent.putExtra("imageUrl", selectedSong.getImageUrl());
                intent.putExtra("previewUrl", selectedSong.getPreviewUrl());
                startActivity(intent);
            });
        }

        private ArrayList<Song> fetchSongsFromApi() {
            ArrayList<Song> songs = new ArrayList<>();

            try {
                String urlString = LAST_FM_API_URL +
                        "?method=chart.gettoptracks" +
                        "&api_key=" + API_KEY +
                        "&format=json";

                String jsonString = NetworkUtils.fetchData(urlString);

                // Parse JSON response
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject tracksObject = jsonObject.getJSONObject("tracks");
                JSONArray tracksArray = tracksObject.getJSONArray("track");

                for (int i = 0; i < tracksArray.length(); i++) {
                    JSONObject trackObject = tracksArray.getJSONObject(i);
                    String title = trackObject.getString("name");
                    String artist = trackObject.getJSONObject("artist").getString("name");
                    String imageUrl = trackObject.getJSONArray("image").getJSONObject(2).getString("#text");
                    String previewUrl = trackObject.getString("url");

                    songs.add(new Song(title, artist, imageUrl, previewUrl));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return songs;
        }
    }
}
