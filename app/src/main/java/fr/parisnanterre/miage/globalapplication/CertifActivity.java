package fr.parisnanterre.miage.globalapplication;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class CertifActivity extends Activity implements View.OnClickListener{
    KeyStore keyStore;
    TextView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // On contourne l'interdiction d'aller sur le reseau dans le thread ui
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, "azerty".toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_certif);

        Button getBtn = findViewById(R.id.saveActivity_buttonGet);
        Button getTest = findViewById(R.id.saveActivity_buttonTest);
        list = findViewById(R.id.saveActivity_textViewResult);

        getBtn.setOnClickListener(this);
        getTest.setOnClickListener(this);
        this.updateTextViewCertif();
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.saveActivity_buttonGet) {
            try {
                EditText inputTextGet = findViewById(R.id.saveActivity_inputTextCertif);
                URL url = new URL(inputTextGet.getText().toString());
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.connect();
                Certificate[] cert = conn.getServerCertificates();
                keyStore.setCertificateEntry(inputTextGet.getText().toString(), cert[cert.length-1]);
                //Toast
                Context context = getApplicationContext();
                CharSequence text = "Certifcat ajouté au keyStore";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                this.updateTextViewCertif();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(view.getId() == R.id.saveActivity_buttonTest){
            Context context = getApplicationContext();
            CharSequence text = "Je ne fais pas confiance à ce site";
            int duration = Toast.LENGTH_LONG;
            Toast toastFail = Toast.makeText(context, text, duration);
            try {
                EditText inputTextTest = findViewById(R.id.saveActivity_inputTextTest);
                SSLContext sslContxet = SSLContext.getInstance("TLSv1.2");
                TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManager.init(keyStore);
                sslContxet.init(null, trustManager.getTrustManagers(), new SecureRandom());
                URL url = new URL(inputTextTest.getText().toString());
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(sslContxet.getSocketFactory());
                conn.connect();
                conn.getPeerPrincipal();
                conn.disconnect();
                Toast toastValide = Toast.makeText(context, "Site de confiance", duration);
                toastValide.show();
            } catch (NoSuchAlgorithmException e) {
                toastFail.show();
                e.printStackTrace();
            } catch (KeyStoreException e) {
                toastFail.show();
                e.printStackTrace();
            } catch (KeyManagementException e) {
                toastFail.show();
                e.printStackTrace();
            } catch (MalformedURLException e) {
                toastFail.show();
                e.printStackTrace();
            } catch (IOException e) {
                toastFail.show();
                e.printStackTrace();
            }

        }
    }

    private void updateTextViewCertif(){
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                String certif = keyStore.getCertificate(alias).getPublicKey().toString();
                sb.append(certif);
                sb.append("\n");
            }
            list.setText(sb.toString());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
}