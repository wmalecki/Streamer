package com.example.wm.streamer;

//adb logcat -b crash

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    MediaPlayer mediaPlayer1;
    SurfaceHolder surfaceHolder1;
    SurfaceView playerSurfaceView1;
    String videoSrcLocal = "rtsp://192.168.1.9:554/user=admin&password=dziennik&channel=1&stream=1.sdp?real_stream--rtp-caching=100"; //works well
    //   String videoSrcRemote = "rtsp://wmalecki1.zapto.org:554/user=admin&password=dziennik&channel=1&stream=1.sdp?real_stream--rtp-caching=100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.setContentView(R.layout.activity_main);

        playerSurfaceView1 = findViewById(R.id.videoView);
        surfaceHolder1 = playerSurfaceView1.getHolder();
        surfaceHolder1.addCallback(this);

//      View decorView = getWindow().getDecorView();
//
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
    }

    public void stream1(View view) {
        Intent intent = new Intent(this, Stream1.class);
        String message = "Udalo sie!";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        try {

            mediaPlayer1 = new MediaPlayer();
            mediaPlayer1.setDataSource(videoSrcLocal);
            mediaPlayer1.setDisplay(surfaceHolder1);
            mediaPlayer1.prepare();
            mediaPlayer1.setOnPreparedListener(this);
            mediaPlayer1.setOnBufferingUpdateListener(this);
            mediaPlayer1.setOnErrorListener(this);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        mediaPlayer1.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer1.start();
    }

    @Override
    public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
        mediaPlayer1.release();
        return true;
    }
    //@Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mp = this.mediaPlayer1;
        percent = 90;
    }
}
