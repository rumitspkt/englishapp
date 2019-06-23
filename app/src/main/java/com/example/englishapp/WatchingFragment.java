package com.example.englishapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.example.englishapp.databinding.FragmentWatchingBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class WatchingFragment extends Fragment implements View.OnClickListener {

    FragmentWatchingBinding binding;
    MediaController mediaController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_watching, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mediaController = new MediaController(getActivity());
        String videoPath = "android.resource://com.example.englishapp/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        binding.videoView.setVideoURI(uri);
        binding.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.videoView);
        binding.videoView.seekTo(1);

        binding.btnNext.setOnClickListener(this);
        binding.btnReplay.setOnClickListener(this);
        binding.ivPlay.setOnClickListener(this);

        ((LessonActivity) getActivity()).updateProgressbar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.btn_replay:
                binding.videoView.seekTo(1);
                if(!binding.videoView.isPlaying()){
                    play();
                }
                break;
            case R.id.btn_next:
                ((LessonActivity) getActivity()).startFragment(LessonActivity.EXERCISE);
                break;
            case R.id.iv_play:
                play();
                break;
        }
    }

    private void play(){
        binding.ivPlay.setVisibility(View.GONE);
        binding.videoView.start();
    }
}
