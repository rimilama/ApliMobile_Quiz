package com.example.myfirstapply.model;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;

import java.net.URI;

public class Joueur {
    static int UNIQUE_ID = 0;
    private int id = ++UNIQUE_ID;
    private Uri Avatar;
    private String Pseudo;
    private int Score;

    public Joueur(){
        this.Avatar = null;
        this.Pseudo = null;
        this.Score = 0;
    }

    public Joueur(Uri Avatar, String Pseudo, int Score){
        this.Avatar = Avatar;
        this.Pseudo = Pseudo;
        this.Score = Score;
    }

    public void setAvatar(Uri Avatar){
        this.Avatar = Avatar;
    }

    public Uri getAvatar(){
        return this.Avatar;
    }

    public String getAvatarString(){
        return this.Avatar.toString();
    }

    public void setPseudo(String Pseudo){
        this.Pseudo = Pseudo;
    }

    public String getPseudo(){
        return this.Pseudo;
    }

    public void setScore(int Score){
        this.Score = Score;
    }

    public String getScore(){
        return String.format("Score : %s", this.Score);
    }

    public int getScoreInt(){
        return this.Score;
    }

    public int getId(){
        return id;
    }
}
