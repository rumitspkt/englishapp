package com.example.englishapp;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    public static ArrayList<Question> getGrammarQuestions() {
        ArrayList<Question> list = new ArrayList<>();

        Question question = new Question("I _____ (be) a student.", null, -1);
        ArrayList<String> selections = new ArrayList<>();
        selections.add("am");
        selections.add("is");
        selections.add("are");
        question.setSelections(selections);
        question.setAnswer(0);

        Question question1 = new Question("My father __________ excuses when I feel like going to the cinema.", null, -1);
        selections = new ArrayList<>();
        selections.add("make always");
        selections.add("make alway");
        selections.add("always makes");
        question1.setSelections(selections);
        question1.setAnswer(2);

        Question question2 = new Question("His students ________ (not, speak) German in class.", null, -1);
        selections = new ArrayList<>();
        selections.add("don’t speak");
        selections.add("doesn’t speak");
        selections.add("not speak");
        question2.setSelections(selections);
        question2.setAnswer(1);

        list.add(question);
        list.add(question1);
        list.add(question2);

        return list;

    }

    public static ArrayList<Question> getExerciseQuestions() {
        ArrayList<Question> list = new ArrayList<>();

        Question question = new Question("Where does he live?", null, -1);
        ArrayList<String> selections = new ArrayList<>();
        selections.add("Ha Noi");
        selections.add("New York");
        selections.add("London");
        question.setSelections(selections);
        question.setAnswer(2);

        Question question1 = new Question("How old is he?", null, -1);
        selections = new ArrayList<>();
        selections.add("17 years old");
        selections.add("71 years old");
        selections.add("70 years old");
        question1.setSelections(selections);
        question1.setAnswer(0);

        Question question2 = new Question("What time does he usually wake up?", null, -1);
        selections = new ArrayList<>();
        selections.add("7pm");
        selections.add("7am");
        selections.add("12am");
        question2.setSelections(selections);
        question2.setAnswer(1);

        list.add(question);
        list.add(question1);
        list.add(question2);

        return list;
    }

    public static ArrayList<String> getSentences(){
        ArrayList<String> list = new ArrayList<>();
        list.add("How old are you");
        list.add("Can you help me");
        list.add("I go to school by car");
        return list;
    }

    public static int totalScore = 0;
    public static int totalScoreGained = 0;
}
