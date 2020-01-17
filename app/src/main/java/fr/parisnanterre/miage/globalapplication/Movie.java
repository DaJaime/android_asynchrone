package fr.parisnanterre.miage.globalapplication;

import android.graphics.Bitmap;

public class Movie {
    private String title;
    private String date;
    private String realisateur;
    private Bitmap image;

    public Movie(String title, String date, String realisateur, Bitmap image) {
        this.title = title;
        this.date = date;
        this.realisateur = realisateur;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setData(String data) {
        this.date = data;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
