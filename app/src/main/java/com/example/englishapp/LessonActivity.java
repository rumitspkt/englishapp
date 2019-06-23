package com.example.englishapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.example.englishapp.databinding.ActivityLessonBinding;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LessonActivity extends AppCompatActivity implements View.OnClickListener {


    ActivityLessonBinding binding;

    private int currentType = -1;

    public static final int WATCHING = 0;
    public static final int EXERCISE = 1;
    public static final int PRONUNCIATION = 2;
    public static final int GRAMMAR = 3;
    public static final int RESULT = 4;

    private int excerciseScore = 0;
    private int grammarScore = 0;
    private int pronunciationScore = 0;

    public void startFragment(int type) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        currentType++;
        binding.btnNext.setVisibility(View.VISIBLE);
        switch (type) {
            case WATCHING:
                binding.btnNext.setVisibility(View.GONE);
                WatchingFragment watchingFragment = new WatchingFragment();
                fragmentTransaction.replace(R.id.fragment, watchingFragment);
                break;
            case EXERCISE:
                binding.tvType.setText("Exercise");
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                fragmentTransaction.replace(R.id.fragment, exerciseFragment, ExerciseFragment.TAG);
                break;
            case PRONUNCIATION:
                binding.tvType.setText("Pronunciation");
                PronunciationFragment pronunciationFragment = new PronunciationFragment();
                fragmentTransaction.replace(R.id.fragment, pronunciationFragment, PronunciationFragment.TAG);
                break;
            case GRAMMAR:
                binding.tvType.setText("Grammar");
                GrammarFragment grammarFragment = new GrammarFragment();
                fragmentTransaction.replace(R.id.fragment, grammarFragment, GrammarFragment.TAG);
                break;
            case RESULT:
                binding.btnNext.setVisibility(View.GONE);
                binding.tvType.setText("Finish");
                Repository.totalScore = Repository.getSentences().size() * 10 + Repository.getExerciseQuestions().size() * 10 + Repository.getGrammarQuestions().size() * 10;
                Repository.totalScoreGained = excerciseScore + grammarScore + pronunciationScore;
                ResultFragment resultFragment = new ResultFragment();
                fragmentTransaction.replace(R.id.fragment, resultFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 111:
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    PronunciationFragment pronunciationFragment = (PronunciationFragment) getSupportFragmentManager().findFragmentByTag(PronunciationFragment.TAG);
                    if(pronunciationFragment != null) {
                        pronunciationFragment.updateResult(results.get(0));
                    }

                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson);
        startFragment(WATCHING);
        binding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                updateProgressbar();
                switch (currentType) {
                    case EXERCISE:
                        ExerciseFragment exerciseFragment = (ExerciseFragment) getSupportFragmentManager().findFragmentByTag(ExerciseFragment.TAG);

                        if (exerciseFragment.checkAnswer()) {
                            excerciseScore += 10;
                        }
                        if (exerciseFragment.isRemain()) {
                            exerciseFragment.nextQuestion();
                        } else {
                            startFragment(PRONUNCIATION);
                        }
                        break;
                    case PRONUNCIATION:
                        PronunciationFragment pronunciationFragment = (PronunciationFragment) getSupportFragmentManager().findFragmentByTag(PronunciationFragment.TAG);

                        pronunciationScore += pronunciationFragment.getPercentResult() / 10;
                        if(pronunciationFragment.isRemain()) {
                            pronunciationFragment.nextSentence();
                        } else {
                            startFragment(GRAMMAR);
                        }
                        break;
                    case GRAMMAR:
                        GrammarFragment grammarFragment = (GrammarFragment) getSupportFragmentManager().findFragmentByTag(GrammarFragment.TAG);

                        if (grammarFragment.checkAnswer()) {
                            grammarScore += 10;
                        }
                        if (grammarFragment.isRemain()) {
                            grammarFragment.nextQuestion();
                        } else {
                            startFragment(RESULT);
                        }
                        break;
                }
                break;
        }
    }

    public void updateProgressbar() {
        int current = binding.pb.getProgress();
        binding.pb.setProgress(current + 1);
    }
}
