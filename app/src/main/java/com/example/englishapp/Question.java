package com.example.englishapp;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String question;
    private ArrayList<String> selections;
    private int answer;

    public Question(String question, ArrayList<String> selections, int answer) {
        this.question = question;
        this.selections = selections;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getSelections() {
        return selections;
    }

    public void setSelections(ArrayList<String> selections) {
        this.selections = selections;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
