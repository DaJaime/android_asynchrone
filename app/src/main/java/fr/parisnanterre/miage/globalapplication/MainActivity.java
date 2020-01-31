package fr.parisnanterre.miage.globalapplication;

import androidx.appcompat.app.AppCompatActivity;
import fr.parisnanterre.miage.globalapplication.asyncTask.LoadMovieImageTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<Movie> movieListe = new ArrayList();
    public Movie[] initData = new Movie[]{
            new Movie("Damien ", "1997", "Jaime", null),
            new Movie("Chien", "2019", "Chien", null),
            new Movie("Poisson", "2019", "Poisson", null),
            new Movie("Chat", "2019", "Chat", null),
            new Movie("Vache", "2019", "Vache", null),
            new Movie("Poule", "2019", "Poule", null),
            new Movie("Coq", "2019", "Coq", null)
    };
    public MovieAdapter movieAdapter;
    ExecutorService fixedPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (Movie movie: initData) {
            movieListe.add(movie);
        }

        movieAdapter = new MovieAdapter(this.getBaseContext(), movieListe);
        ListView lv = (ListView) findViewById(R.id.mainActivity_movieList);
        lv.setAdapter(movieAdapter);

        Button loadBtn = findViewById(R.id.mainActivity_btnLoad);
        Button addBtn = findViewById(R.id.mainActivity_btnAdd);
        Button delBtn = findViewById(R.id.mainActivity_btnDelete);
        Button saveBtn = findViewById(R.id.mainActivity_btnSave);
        loadBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        // Create executor
        fixedPool = Executors.newFixedThreadPool(2);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.mainActivity_btnLoad) {

            for (Movie m : movieListe) {
                /* **************** With Async Task ************************************/
                LoadMovieImageTask imgTask = new LoadMovieImageTask(m, movieAdapter);
                imgTask.executeOnExecutor(fixedPool, "https://picsum.photos/100/100");
                // imgTask.execute("https://picsum.photos/100/100");

                /* **************** With Thread ************************************
                LoadMovieImageThread imgThread = new LoadMovieImageThread(m, movieAdapter, "https://picsum.photos/100/100");
                Thread t = new Thread(imgThread);
                t.start();
                ***********************************************************************/
            }
        }
        if(view.getId() == R.id.mainActivity_btnAdd) {
            movieListe.add(new Movie("New", "2020", "new", null));
            movieAdapter.notifyDataSetChanged();
        }
        if(view.getId() == R.id.mainActivity_btnDelete) {
            movieListe.clear();
            movieAdapter.notifyDataSetChanged();
        }
        if(view.getId() == R.id.mainActivity_btnSave){
            Intent myIntent = new Intent(MainActivity.this, CertifActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
    }
}
