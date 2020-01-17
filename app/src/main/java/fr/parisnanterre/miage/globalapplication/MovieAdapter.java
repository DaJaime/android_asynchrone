package fr.parisnanterre.miage.globalapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(Context ctx, List<Movie> movies) {
        super(ctx, 0, movies);
        this.movieList = movies;
        this.context = ctx;
    }

    @Override
    public View getView(int position, View recup, ViewGroup parent) {

        if (recup == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            recup = inflater.inflate(R.layout.movie_list_item, parent, false);
        }

        TextView item_title = (TextView) recup.findViewById(R.id.item_title);
        TextView item_date = (TextView) recup.findViewById(R.id.item_date);
        TextView item_realisateur = (TextView) recup.findViewById(R.id.item_ralisateur);

        Movie mov = this.movieList.get(position);

        ImageView img = (ImageView) recup.findViewById(R.id.item_image);
        if(mov.getImage() == null)
            img.setImageResource(R.mipmap.ic_launcher);
        else
            img.setImageBitmap(mov.getImage());

        item_title.setText(mov.getTitle());
        item_date.setText("" + mov.getDate());
        item_realisateur.setText("" + mov.getRealisateur());

        return recup;
    }

}
