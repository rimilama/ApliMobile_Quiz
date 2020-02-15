package com.example.myfirstapply.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfirstapply.R;
import com.example.myfirstapply.model.Joueur;
import com.example.myfirstapply.model.JoueurAdapter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView Text;
    private EditText Name;
    private Button Play;
    private Button Imagedl;
    private ImageView Image;
    private Joueur JoueurCourant;
    private ArrayList<Joueur> joueurListe;
    private ListView ListeJ;
    private JoueurAdapter adapter;

    private SharedPreferences Memory;

    public static int GAME_ACTIVITY_REQUEST_CODE = 7;
    public static int IMAGE_ACTIVITY_REQUEST_CODE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Memory = getPreferences(MODE_PRIVATE);

        Text = (TextView) findViewById(R.id.activity_main_text);
        Name = (EditText) findViewById(R.id.activity_main_name);
        Play = (Button) findViewById(R.id.activity_main_play);
        Imagedl = (Button) findViewById(R.id.activity_main_imagedl);
        Image = (ImageView) findViewById(R.id.activity_main_image);
        ListeJ = (ListView) findViewById(R.id.activity_main_liste);



        joueurListe = new ArrayList<>();
        genererJoueur(joueurListe);

        adapter = new JoueurAdapter(MainActivity.this, R.layout.liste_layout ,joueurListe);
        ListeJ.setAdapter(adapter);

        ListeJ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Joueur joueurSelected = (Joueur) adapter.getItemAtPosition(position);
                String textTop = String.format("Content de te revoir %s ! Envie de battre ton meilleur score qui était de %s ?", joueurSelected.getPseudo(), joueurSelected.getScoreInt());
                Name.setText(joueurSelected.getPseudo());
                Name.setSelection(joueurSelected.getPseudo().length());
                Text.setText(textTop);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), joueurSelected.getAvatar());
                    Image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Play.setEnabled(false);

        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Play.setEnabled(s.toString().length() != 0);
                if(s.length() == 0){
                    Text.setText("Bienvenue ! Quel est votre nom ?");
                    Image = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JoueurCourant = new Joueur(null, Name.getText().toString(), 0);
                Intent GameLauncher = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(GameLauncher, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
        Imagedl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            int score = data.getIntExtra(GameActivity.SCORE_END, 0);
            JoueurCourant.setScore(score);
            String memText = String.format("%s.%s¨%s",JoueurCourant.getPseudo(),JoueurCourant.getScoreInt(), JoueurCourant.getAvatarString());
            String key = String.format("Joueur%s",JoueurCourant.getId());
            Memory.edit().putString(key,memText).apply();
            genererJoueur(joueurListe);
            ListeJ.setAdapter(adapter);
        }

        if(IMAGE_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode && data != null && data.getData() != null){
            try {
                Uri SelectedImage = data.getData();
                JoueurCourant.setAvatar(SelectedImage);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), SelectedImage);
                Image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void genererJoueur(ArrayList<Joueur> joueurListe){
        joueurListe.clear();
        int count = 1;
        String memCheck = null;
        String key;
        int middle;
        int middle2;
        do{
            key = String.format("Joueur%s",count);
            memCheck = Memory.getString(key, null);
            if(memCheck!=null) {
                middle = memCheck.lastIndexOf(".");
                middle2 = memCheck.lastIndexOf("¨");
                joueurListe.add(new Joueur(Uri.parse(memCheck.substring(middle2 + 1, memCheck.length())), memCheck.substring(0, middle), Integer.parseInt(memCheck.substring(middle + 1, middle2))));
            }
            count++;
        }while(memCheck != null);

    }
}
