package com.example.englishapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import com.example.englishapp.databinding.ActivityHomeBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                binding.ivPlay.setVisibility(View.GONE);
//                binding.videoView.start();
                Intent intent = new Intent(HomeActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });

        mediaController = new MediaController(HomeActivity.this);
        String videoPath = "android.resource://com.example.englishapp/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        binding.videoView.setVideoURI(uri);
        binding.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.videoView);
        binding.videoView.seekTo(1);
    }


}
