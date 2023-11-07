package blah.blah.musicplayerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {

    public SongAdapter(Context context, List<Song> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_song, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView artistTextView = convertView.findViewById(R.id.artistTextView);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        titleTextView.setText(song.getTitle());
        artistTextView.setText(song.getArtist());


        Picasso.get().load(song.getImageUrl()).into(imageView);

        return convertView;
    }
}

