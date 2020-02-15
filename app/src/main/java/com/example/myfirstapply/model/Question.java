package com.example.myfirstapply.model;

import java.util.List;

public class Question {

    private String Question;
    private List<String> Reponses;
    private int Valide;

    public Question(String question, List<String> reponses, int valide) {
        Question = question;
        Reponses = reponses;
        Valide = valide;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public List<String> getReponses() {
        return Reponses;
    }

    public void setReponses(List<String> reponses) {
        Reponses = reponses;
    }

    public int getValide() {
        return Valide;
    }

    public void setValide(int valide) {
        Valide = valide;
    }


}
