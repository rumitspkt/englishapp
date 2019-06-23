package com.example.englishapp;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.englishapp.databinding.FragmentExerciseBinding;
import com.example.englishapp.databinding.FragmentGrammarBinding;
import com.example.englishapp.databinding.ItemPageBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class GrammarFragment extends Fragment {

    public static final String TAG = GrammarFragment.class.getSimpleName();


    private ArrayList<Question> questions = Repository.getGrammarQuestions();
    private FragmentGrammarBinding binding;

    private int currentQuestion = -1;
    private boolean isCorrect = false;

    public int getNumberOfQuestion() {
        return questions.size();
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void nextQuestion() {
        currentQuestion++;
        Question question = questions.get(currentQuestion);
        binding.tvQuestion.setText("Question " + (currentQuestion + 1) + ": " + question.getQuestion());
        binding.rb1.setText(question.getSelections().get(0));
        binding.rb2.setText(question.getSelections().get(1));
        binding.rb3.setText(question.getSelections().get(2));
        isCorrect = false;
        binding.rb1.setChecked(false);
        binding.rb2.setChecked(false);
        binding.rb3.setChecked(false);
        for (int i = 0; i < binding.llPages.getChildCount(); i++) {
            binding.llPages.getChildAt(i).setBackground(getActivity().getDrawable(R.drawable.bg_page));
            ((TextView) binding.llPages.getChildAt(i)).setTextColor(getActivity().getColor(android.R.color.black));
        }
        binding.llPages.getChildAt(currentQuestion).setBackground(getActivity().getDrawable(R.drawable.bg_page_selected));
        ((TextView) binding.llPages.getChildAt(currentQuestion)).setTextColor(getActivity().getColor(android.R.color.white));
    }

    public boolean isRemain(){
        return currentQuestion < questions.size() -1 ;
    }


    public boolean checkAnswer() {
        return isCorrect;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_grammar, container, false);
        binding.rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        isCorrect = questions.get(currentQuestion).getAnswer() == 0;
                        break;
                    case R.id.rb_2:
                        isCorrect = questions.get(currentQuestion).getAnswer() == 1;
                        break;
                    case R.id.rb_3:
                        isCorrect = questions.get(currentQuestion).getAnswer() == 3;
                        break;
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.llPages.removeAllViews();
        for (int i = 1; i < questions.size() + 1; i++) {
            TextView textView = new TextView(getActivity());
            textView.setText(String.valueOf(i));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpToPixels(30), dpToPixels(30));
            layoutParams.setMarginStart(dpToPixels(5));
            layoutParams.setMarginEnd(dpToPixels(5));
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getActivity().getDrawable(R.drawable.bg_page));
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            binding.llPages.addView(textView);
        }
        nextQuestion();
    }

    private int dpToPixels(int dps) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

}
