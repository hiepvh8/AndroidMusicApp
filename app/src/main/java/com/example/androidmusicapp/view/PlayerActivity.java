package com.example.androidmusicapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidmusicapp.R;
import com.example.androidmusicapp.databinding.ActivityPlayerBinding;
import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.viewmodel.playerViewModel;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding activityPlayerBinding;
    //    private SeekBar playerSeekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityPlayerBinding =ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(activityPlayerBinding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        Song song = (Song) bundle.get("object_song");

        String filePath = song.getFilePath();

        mediaPlayer = new MediaPlayer();
        Glide.with(this).load(song.getCoverArt()).into(activityPlayerBinding.imgPlayer);
        activityPlayerBinding.playerSeekBar.setMax(100);

        activityPlayerBinding.imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    activityPlayerBinding.imagePlayPause.setImageResource(R.drawable.baseline_play_circle_24);
                }
                else
                {
                    mediaPlayer.start();
                    activityPlayerBinding.imagePlayPause.setImageResource(R.drawable.baseline_pause_circle_filled_24);
                    updateSeekBar();
                }
            }
        });

        prepareMediaPlayer(filePath);

        activityPlayerBinding.playerSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                activityPlayerBinding.textCurrentTime.setText(timer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                activityPlayerBinding.playerSeekBar.setSecondaryProgress(i);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                activityPlayerBinding.playerSeekBar.setProgress(0);
                activityPlayerBinding.imagePlayPause.setImageResource(R.drawable.baseline_play_circle_24);
                activityPlayerBinding.textCurrentTime.setText(R.string.zero);
                activityPlayerBinding.textTotalDuration.setText(R.string.zero);
                mediaPlayer.reset();
                prepareMediaPlayer(filePath);
            }
        });

        activityPlayerBinding.imageAddLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void prepareMediaPlayer(String filePath){
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            activityPlayerBinding.textTotalDuration.setText(timer(mediaPlayer.getDuration()));
        }catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            int currentDuration = mediaPlayer.getCurrentPosition();
            activityPlayerBinding.textCurrentTime.setText(timer(currentDuration));
        }
    };

    private void updateSeekBar(){
        if(mediaPlayer.isPlaying()){
            activityPlayerBinding.playerSeekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration() * 100)));
            handler.postDelayed(updater, 1000);
        }
    }

    private String timer (long miliSecond){
        String timerString = "";
        String secondString;

        int hours = (int) (miliSecond / (1000*60*60));
        int minute = (int) (miliSecond %(1000*60*60)) / (1000*60);
        int second = (int) ((miliSecond % (1000*60*60)) % (1000*60) / 1000);

        if (hours > 0){
            timerString = hours + ":";
        }

        if (second < 10){
            secondString = "0" + second;
        }
        else {
            secondString = "" + second;
        }
        timerString = timerString + minute + ":" + secondString;
        return timerString;
    }
}