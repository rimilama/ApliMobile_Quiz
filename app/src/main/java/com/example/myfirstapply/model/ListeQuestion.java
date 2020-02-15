package com.example.myfirstapply.model;

import java.util.Collections;
import java.util.List;

public class ListeQuestion {

    private List<Question> ListeQ;
    private int NextQestion;

    public ListeQuestion(List<Question> listeQ) {
        ListeQ = listeQ;
        Collections.shuffle(listeQ);
        NextQestion = 0;
    }

    public Question getQuestion(){
        if(NextQestion == ListeQ.size()){
            NextQestion = 0;
        }

        return ListeQ.get(NextQestion++);
    }
}
