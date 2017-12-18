package com.cq.videoproject.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.cq.videoproject.R;

/**
 * 观看视频
 *
 * @author Administrator
 */
public class WatchActivity extends AppCompatActivity {

    private VideoView videoView;
    private PercentRelativeLayout layout;
    private ImageButton imageButtonPause, imageButtonBack, imageButtonForward;
    private SeekBar seekBarTime;


    //音量控制
    private AudioManager audioManager;
    //手势计算
    private float x1, y1, x2, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        initView();
        initListener();
        playVideo();

    }

    /**
     * 播放视频
     */
    private void playVideo() {
        String path = getIntent().getStringExtra("uri");
        Uri uri = Uri.parse(path);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        Log.w("test", "uri -> " + uri.toString());

        videoView.setVideoURI(uri);
        videoView.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int curtenTime = videoView.getCurrentPosition();
                    int durationTime = videoView.getDuration();
                    if (curtenTime!=0 && durationTime!=0) {
                        int position = (curtenTime*100)/durationTime;
                        Log.w("test", "curtenTime-> " + curtenTime + "durationTime-> " + durationTime + " position-> " + position);
                        seekBarTime.setProgress(position);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }


    /**
     * 初始化控件
     */
    private void initView() {

        videoView = (VideoView) findViewById(R.id.vv_watch);
        layout = (PercentRelativeLayout) findViewById(R.id.ll_watch);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        imageButtonBack = (ImageButton) findViewById(R.id.ibt_play_back);
        imageButtonForward = (ImageButton) findViewById(R.id.ibt_play_forward);
        imageButtonPause = (ImageButton) findViewById(R.id.ibt_play_pause);

        seekBarTime = (SeekBar) findViewById(R.id.sb_play_time);
    }

    /**
     * 初始化事件的监听
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        layout.setOnTouchListener(myOnTouchListener);
        imageButtonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoForward();
            }
        });
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoBack();
            }
        });

        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseOrPlay();
            }
        });
    }


    /**
     * 手势监听
     */
    private View.OnTouchListener myOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                x1 = motionEvent.getX();
                y1 = motionEvent.getY();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                if ((y2 - y1) > 0 && Math.abs(y2 - y1) > 50) {
                    //下
                    audioLow();
                } else if ((y2 - y1) < 0 && Math.abs(y2 - y1) > 50) {
                    //上
                    audioUp();
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                if (Math.abs(x1 - x2) < 50 && Math.abs(y2 - y1) < 50) {
                    //播放和暂停
                    pauseOrPlay();
                } else if ((x2 - x1) > 0 && Math.abs(x2 - x1) > 50) {
                    //快进
                    videoForward();
                } else if ((x2 - x1) < 0 && Math.abs(x2 - x1) > 50) {
                    //快退
                    videoBack();
                }
            }
            return true;
        }
    };

    /**
     * 音量加
     */
    private void audioUp() {
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
    }

    /**
     * 音量减
     */
    private void audioLow() {
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
    }

    /**
     * 快进
     */
    private void videoForward() {
        int time = videoView.getCurrentPosition();
        videoView.seekTo(time + 1000);
    }

    /**
     * 后退
     */
    private void videoBack() {
        int time = videoView.getCurrentPosition();
        videoView.seekTo(time - 1000);
    }

    /**
     * 播放和暂停
     */
    private void pauseOrPlay() {
        if (videoView.isPlaying()) {
            videoView.pause();
            imageButtonPause.setImageResource(R.drawable.ic_play);
        } else {
            videoView.start();
            imageButtonPause.setImageResource(R.drawable.ic_pause);
        }
    }

    /**
     * 更新seekbar
     */
    private void updateSeekBar() {

    }
}

