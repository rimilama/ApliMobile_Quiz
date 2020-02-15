package com.example.myfirstapply.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapply.R;
import com.example.myfirstapply.model.Question;
import com.example.myfirstapply.model.ListeQuestion;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView QuestionText;
    private Button Reponse1;
    private Button Reponse2;
    private Button Reponse3;
    private Button Reponse4;

    private ListeQuestion QuestionListe;
    private Question QuestionEnCours;

    private int NbQuestions;
    private int Score;
    private boolean ActivationClique;

    public static String SCORE_END = "SCORE_END";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        QuestionText = (TextView) findViewById(R.id.activity_game_question_text);
        Reponse1 = (Button) findViewById(R.id.activity_game_reponse1);
        Reponse2 = (Button) findViewById(R.id.activity_game_reponse2);
        Reponse3 = (Button) findViewById(R.id.activity_game_reponse3);
        Reponse4 = (Button) findViewById(R.id.activity_game_reponse4);

        QuestionListe = this.generateQuestions();
        NbQuestions = 5;

        ActivationClique = true;

        Reponse1.setTag(0);
        Reponse1.setOnClickListener(this);
        Reponse2.setTag(1);
        Reponse2.setOnClickListener(this);
        Reponse3.setTag(2);
        Reponse3.setOnClickListener(this);
        Reponse4.setTag(3);
        Reponse4.setOnClickListener(this);

        QuestionEnCours = QuestionListe.getQuestion();
        afficheQuestion(QuestionEnCours);
    }

    @Override
    public void onClick(View v) {
        int Reponse = (int) v.getTag();
        if(Reponse == QuestionEnCours.getValide()){
            Toast.makeText(this,"Félicitation !", Toast.LENGTH_SHORT).show();
            Score += 100;
        }else{
            Toast.makeText(this,"Ptdr t nul !", Toast.LENGTH_SHORT).show();
        }
        NbQuestions--;

        ActivationClique = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivationClique = true;

                if (NbQuestions == 0){
                    EngGame();
                }else{
                    QuestionEnCours = QuestionListe.getQuestion();
                    afficheQuestion(QuestionEnCours);
                }
            }
        }, 2000);
    }

    private void afficheQuestion(Question question){
        QuestionText.setText(question.getQuestion());
        Reponse1.setText(question.getReponses().get(0));
        Reponse2.setText(question.getReponses().get(1));
        Reponse3.setText(question.getReponses().get(2));
        Reponse4.setText(question.getReponses().get(3));

    }

    private void EngGame(){
        AlertDialog.Builder Boite = new AlertDialog.Builder(this);
        Boite.setTitle("Bien joué ");
        Boite.setMessage("Votre score finale est de " + Score);
        Boite.setPositiveButton("Oki", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.putExtra(SCORE_END, Score);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        Boite.create();
        Boite.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return ActivationClique && super.dispatchTouchEvent(ev);
    }

    private ListeQuestion generateQuestions() {
        Question question1 = new Question("Qui est le plus gros fournisseur de pdf du pays ?",
                Arrays.asList("Grace Madembo", "Adobe", "Google Drive", "Nicolas Valentin"),
                0);

        Question question2 = new Question("Où aller si vous souhaitez visiter le lieux qui fournis la plus grosse quantité de sel?",
                Arrays.asList("Le lac du Connemara", "Ludus Academie", "Chez Valentin", "Les Paysans Bretons"),
                1);

        Question question3 = new Question("Qui est le plus grand ecrivain du siècle ?",
                Arrays.asList("J.R.R. Tolkien", "George R.R. Martin", "Anna Todd", "Bastien Proob"),
                3);

        Question question4 = new Question("Qu'est-ce qu'une Jam ?",
                Arrays.asList("Un event fun", "Un examen", "Un examen caché", "De la confiture"),
                2);

        Question question5 = new Question("Qu'est-ce qu'un cours ?",
                Arrays.asList("Une leçon de vie", "Un pdf", "Une mécanique de passation d'éducation", "Un Battle-Royale"),
                2);

        Question question6 = new Question("Who did the Mona Lisa paint?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("In which city is the composer Frédéric Chopin buried?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("What is the country top-level domain of Belgium?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        return new ListeQuestion(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));
    }

}
