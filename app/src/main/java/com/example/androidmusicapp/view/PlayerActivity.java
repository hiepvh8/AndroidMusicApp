package com.example.androidmusicapp.view;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.androidmusicapp.model.entity.Song;

public class PlayerActivity extends AppCompatActivity {

    private ImageView imagePlayPause, img_player;
    private TextView textCurrentTime, textTotalDuration, title_player, artist_player;
    private SeekBar playerSeekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    private static final String KEY_PLAYBACK_POSITION = "playback_position";
    private static final String KEY_IS_PLAYING = "is_playing";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        Song song = (Song) bundle.get("object_song");

        String filePath = song.getFilePath();

        playerSeekBar = findViewById(R.id.playerSeekBar);
        title_player = findViewById(R.id.title_player);
        artist_player = findViewById(R.id.artist_player);
        img_player = findViewById(R.id.img_player);
        textCurrentTime = findViewById(R.id.textCurrentTime);
        textTotalDuration = findViewById(R.id.textTotalDuration);
        mediaPlayer = new MediaPlayer();

        title_player.setText(song.getTitle());
        artist_player.setText(song.getGenre());
        Glide.with(this).load(song.getCoverArt()).into(img_player);
        imagePlayPause = findViewById(R.id.imagePlayPause);

        playerSeekBar.setMax(100);

        imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    imagePlayPause.setImageResource(R.drawable.baseline_play_circle_24);
                }
                else
                {
                    mediaPlayer.start();
                    imagePlayPause.setImageResource(R.drawable.baseline_pause_circle_filled_24);
                    updateSeekBar();
                }
            }
        });


        prepareMediaPlayer(filePath);

        playerSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                textCurrentTime.setText(timer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                playerSeekBar.setSecondaryProgress(i);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playerSeekBar.setProgress(0);
                imagePlayPause.setImageResource(R.drawable.baseline_play_circle_24);
                textCurrentTime.setText(R.string.zero);
                textTotalDuration.setText(R.string.zero);
                mediaPlayer.reset();
                prepareMediaPlayer(filePath);
            }
        });
    }

    private void prepareMediaPlayer(String filePath){
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            textTotalDuration.setText(timer(mediaPlayer.getDuration()));
        }catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            int currentDuration = mediaPlayer.getCurrentPosition();
            textCurrentTime.setText(timer(currentDuration));
        }
    };

    private void updateSeekBar(){
        if(mediaPlayer.isPlaying()){
            playerSeekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration() * 100)));
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