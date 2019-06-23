package com.example.englishapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishapp.databinding.FragmentPronunciationBinding;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class PronunciationFragment extends Fragment {

    public static final String TAG = PronunciationFragment.class.getSimpleName();

    FragmentPronunciationBinding binding;
    ArrayList<String> sentences = Repository.getSentences();

    TextToSpeech textToSpeech;

    private int currentSentence = -1;
    private int percentResult = 0;

    public int getCurrentSentence(){
        return currentSentence;
    }

    public int getNumberOfSentence() {
        return sentences.size();
    }

    public int getPercentResult() {
        return percentResult;
    }

    public boolean isRemain(){
        return currentSentence< sentences.size() - 1;
    }

    public void nextSentence() {
        currentSentence++;
        binding.tvNo.setText("Sentence " + (currentSentence + 1) + ":");
        binding.tvSentence.setText(sentences.get(currentSentence));
        percentResult = 0;
        for (int i = 0; i < binding.llPages.getChildCount(); i++) {
            binding.llPages.getChildAt(i).setBackground(getActivity().getDrawable(R.drawable.bg_page));
            ((TextView) binding.llPages.getChildAt(i)).setTextColor(getActivity().getColor(android.R.color.black));
        }
        binding.llPages.getChildAt(currentSentence).setBackground(getActivity().getDrawable(R.drawable.bg_page_selected));
        ((TextView) binding.llPages.getChildAt(currentSentence)).setTextColor(getActivity().getColor(android.R.color.white));
        binding.tvPercent.setText("0 %");
        binding.tvSpeech.setText("");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pronunciation, container, false);
        binding.ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");

                try {
                    getActivity().startActivityForResult(i, 111);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Sorry! Your device doesn't support speech language", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.ivVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(binding.tvSentence.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.llPages.removeAllViews();
        for (int i = 1; i < sentences.size() + 1; i++) {
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
        nextSentence();
    }

    private int dpToPixels(int dps) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public void updateResult(String text){
        // compare
        String textToCompare = binding.tvSentence.getText().toString().toLowerCase();
        String textLower = text.toLowerCase();
        int total = textToCompare.length();
        int same = 0;
        for(int i = 0; i < textToCompare.length(); i++) {
            if(i > textLower.length() - 1){
                break;
            }
            if(textToCompare.charAt(i) == textLower.charAt(i)){
                same++;
            }


        }

        double percent = same * 1D / total;
        binding.tvPercent.setText(String.valueOf(Math.round(percent * 100)) + " %");
        Log.d(TAG, "updateResult: " + same + "   " + total + "   " + percent + "  " + Math.round(percent * 100));

        SpannableString spannableString = new SpannableString(textLower);
        String[] arr1 = textLower.split(" ");
        String[] arr2 = textToCompare.split(" ");
        int min = arr1.length > arr2.length ? arr2.length : arr1.length;
        int current = 0;
        for(int i = 0; i < min; i++) {
            if(arr1[i].equals(arr2[i])){
                spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), current, current + arr1[i].length(), SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            current = current + arr1[i].length() + 1;
        }

        binding.tvSpeech.setText(spannableString);
    }
}
