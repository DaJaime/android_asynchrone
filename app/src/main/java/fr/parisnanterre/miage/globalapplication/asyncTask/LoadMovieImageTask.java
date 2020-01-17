package fr.parisnanterre.miage.globalapplication.asyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import fr.parisnanterre.miage.globalapplication.Movie;
import fr.parisnanterre.miage.globalapplication.MovieAdapter;

public class LoadMovieImageTask extends AsyncTask<String, Void, Bitmap> {
    private Movie movie;
    private MovieAdapter movieAdapter;

    public LoadMovieImageTask(Movie movie, MovieAdapter movieAdapter){
        this.movie = movie;
        this.movieAdapter = movieAdapter;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            System.out.println("LoadImage..." + movie.toString() );
            URL url = new URL(urls[0]);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            return bm;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        movie.setImage(result);
        movieAdapter.notifyDataSetChanged();
    }
}
