package com.example.myfirstapply.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myfirstapply.R;
import java.io.IOException;
import java.util.ArrayList;


import com.example.myfirstapply.R;

import java.io.IOException;
import java.util.ArrayList;

public class JoueurAdapter extends ArrayAdapter<Joueur> {

    private ArrayList<Joueur> JoueurList;

    public JoueurAdapter(Context context, int TextViewRessourceId ,ArrayList<Joueur> JoueurList){
        super(context, TextViewRessourceId, JoueurList);
        this.JoueurList = JoueurList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_layout, parent, false);
        }

        JoueurViewHolder viewHolder = (JoueurViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new JoueurViewHolder();
            viewHolder.Avatar = (ImageView) convertView.findViewById(R.id.liste_layout_image);
            viewHolder.Pseudo = (TextView) convertView.findViewById(R.id.liste_layout_pseudo);
            viewHolder.Text = (TextView) convertView.findViewById(R.id.liste_layout_text);
            convertView.setTag(viewHolder);
        }

        Joueur joueur = getItem(position);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), joueur.getAvatar()); // regarder glide
            viewHolder.Avatar.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.Pseudo.setText(joueur.getPseudo());
        viewHolder.Text.setText(joueur.getScore());

        return convertView;
    }

    public Joueur getItemAtPosition(int position){
        return JoueurList.get(position);
    }
}
