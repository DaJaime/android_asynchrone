package fr.parisnanterre.miage.globalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;

import fr.parisnanterre.miage.globalapplication.asyncTask.LoadMovieImageTask;

public class SaveActivity extends Activity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_save);

        Button getBtn = findViewById(R.id.saveActivity_buttonGet);
        getBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.saveActivity_buttonGet) {
            try {
                // On contourne l'interdiction d'aller sur le reseau dans le thread ui
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, "azerty".toCharArray());

                URL url = new URL("https://coursenligne.parisnanterre.fr/");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.connect();
                Certificate[] cert = conn.getServerCertificates();
                System.out.println(cert[cert.length-1]);
                keyStore.setCertificateEntry("certif", cert[cert.length-1]);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}