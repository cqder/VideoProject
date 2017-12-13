package com.cq.videoproject;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class WatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        String  path = getIntent().getStringExtra("path");
        Uri uri = Uri.parse(path);

        VideoView videoView =  findViewById(R.id.vv_watch);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        Log.w("test","uri -> "+uri.toString());

        videoView.setVideoURI(uri);
        videoView.start();
    }
}
