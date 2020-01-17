package fr.parisnanterre.miage.globalapplication.asyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import fr.parisnanterre.miage.globalapplication.Movie;
import fr.parisnanterre.miage.globalapplication.MovieAdapter;

public class LoadMovieImageThread implements Runnable{
    private Movie movie;
    private MovieAdapter movieAdapter;
    private String urlString;

    public LoadMovieImageThread(Movie movie, MovieAdapter movieAdapter, String url) {
        this.movie = movie;
        this.movieAdapter = movieAdapter;
        this.urlString = url;
    }

    @Override
    public void run() {
        try {
            System.out.println("LoadImage..." + movie.toString() );
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            movie.setImage(bm);
            Handler h = new Handler(Looper.getMainLooper());
            h.post (new Runnable () {
                @Override
                public void run() {
                    movieAdapter.notifyDataSetChanged();
                }
            });
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
