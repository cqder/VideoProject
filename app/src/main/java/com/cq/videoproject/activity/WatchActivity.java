package com.cq.videoproject.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.cq.videoproject.R;

/**
 * 观看视频
 *
 * @author Administrator
 */
public class WatchActivity extends AppCompatActivity {

    private VideoView videoView;
    private LinearLayout linearLayout;
    private AudioManager audioManager;
    private float x1, y1, x2, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        videoView = findViewById(R.id.vv_watch);
        linearLayout = findViewById(R.id.ll_watch);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        String path = getIntent().getStringExtra("path");
        Uri uri = Uri.parse(path);

        initListener();

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        Log.w("test", "uri -> " + uri.toString());

        videoView.setVideoURI(uri);
        videoView.start();
    }

    /**
     * 初始化事件的监听
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    x1 = motionEvent.getX();
                    y1 = motionEvent.getY();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    x2 = motionEvent.getX();
                    y2 = motionEvent.getY();
                    if ((y2 - y1) > 0 && Math.abs(y2 - y1) > 50) {
                        //下
                        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
                    } else if ((y2 - y1) < 0 && Math.abs(y2 - y1) > 50) {
                        //上
                        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    x2 = motionEvent.getX();
                    y2 = motionEvent.getY();
                    if (Math.abs(x1 - x2) < 50 && Math.abs(y2 - y1) < 50) {
                        //播放和暂停
                        if (videoView.isPlaying()) {
                            videoView.pause();
                        } else {
                            videoView.start();
                        }
                    } else if ((x2 - x1) > 0 && Math.abs(x2 - x1) > 50) {
                        //快进
                        int time = videoView.getCurrentPosition();
                        videoView.seekTo(time + 1000);
                    } else if ((x2 - x1) < 0 && Math.abs(x2 - x1) > 50) {
                        //快退
                        int time = videoView.getCurrentPosition();
                        videoView.seekTo(time - 1000);
                    }
                }
                return true;
            }

        });
    }


}

