package com.example.androidmusicapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidmusicapp.R;
import com.example.androidmusicapp.adapter.SongAdapter;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.databinding.ActivityPlayerBinding;
import com.example.androidmusicapp.model.entity.Song;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;


public class PlayerActivity extends AppCompatActivity {
    private ActivityPlayerBinding activityPlayerBinding;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private int savedSeekBarPosition;
    private SongAdapter songAdapter;
    private ArrayList<Song> songList = new ArrayList<>();
    private int currentSongIndex = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityPlayerBinding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(activityPlayerBinding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        Song song = (Song) bundle.get("object_song");
        currentSongIndex = bundle.getInt("position", 0);
        songList = (ArrayList<Song>) bundle.get("songList");
        String filePath = song.getFilePath();
        mediaPlayer = new MediaPlayer();
        Glide.with(this).load(song.getCoverArt()).into(activityPlayerBinding.imgPlayer);
        //displaySongListJson();
        activityPlayerBinding.artistPlayer.setText(song.getGenre());
        activityPlayerBinding.titlePlayer.setText(song.getTitle());
        activityPlayerBinding.playerSeekBar.setMax(100);
        activityPlayerBinding.playerSeekBar.setProgress(savedSeekBarPosition);

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
                    if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    activityPlayerBinding.imagePlayPause.setImageResource(R.drawable.baseline_pause_circle_filled_24);
                    updateSeekBar();
                }}
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
                autoPlayNextSong();
            }
        });
        activityPlayerBinding.imageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               playNextSong();
            }
        });
        activityPlayerBinding.imagePrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPrevousSong();
            }
        });
        activityPlayerBinding.imageAddLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void playNextSong(){
        if (mediaPlayer != null){
            activityPlayerBinding.imagePlayPause.setImageResource(R.drawable.baseline_pause_circle_filled_24);
        }
        if (currentSongIndex < songList.size() - 1) {
            currentSongIndex++;
        }
        else {
            currentSongIndex = 0;
        }
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        updateUI();
    }
    private void playPrevousSong(){
        if (mediaPlayer != null){
            activityPlayerBinding.imagePlayPause.setImageResource(R.drawable.baseline_pause_circle_filled_24);
        }
        if (currentSongIndex > 0){
            currentSongIndex--;
        }
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        updateUI();
    }
    private void updateUI(){
        String filePath = songList.get(currentSongIndex).getFilePath();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(filePath));
        mediaPlayer.start();
        activityPlayerBinding.imagePlayPause.setImageResource(R.drawable.baseline_pause_circle_filled_24);
        updateSeekBar();
        Glide.with(getApplicationContext()).load(songList.get(currentSongIndex).getCoverArt()).into(activityPlayerBinding.imgPlayer);
        activityPlayerBinding.artistPlayer.setText(songList.get(currentSongIndex).getGenre());
        activityPlayerBinding.titlePlayer.setText(songList.get(currentSongIndex).getTitle());
        activityPlayerBinding.textTotalDuration.setText(timer(mediaPlayer.getDuration()));
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
    private void autoPlayNextSong(){
        if (currentSongIndex < songList.size() - 1) {
            currentSongIndex++;
        } else {
            currentSongIndex = 0;
        }

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        updateUI();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                activityPlayerBinding.playerSeekBar.setProgress(0);
                activityPlayerBinding.imagePlayPause.setImageResource(R.drawable.baseline_play_circle_24);
                activityPlayerBinding.textCurrentTime.setText(R.string.zero);
                activityPlayerBinding.textTotalDuration.setText(R.string.zero);
                mediaPlayer.reset();
                autoPlayNextSong();
            }
        });
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
    public void addSong(){
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
        Call<ArrayList<Song>> call = apiService.getSongs();
        call.enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Song> songs = response.body();
                    if (songs != null && !songs.isEmpty()) {
                        songList = songs;
                        //currentSongIndex = 0; // Chọn bài hát đầu tiên làm bài hát hiện tại
                        //prepareAndStartSong(songList.get(currentSongIndex), false);
                    }
                } else {
                    // Handle error
                    // Toast.makeText(this,"lỗi",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Song>> call, Throwable t) {
                // Handle failure
            }
        });
    }
    private void displaySongListJson() {
        // Tạo đối tượng Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Chuyển đổi songList thành chuỗi JSON
        String json = gson.toJson(songList);

        // Hiển thị chuỗi JSON trong artistPlayer
        activityPlayerBinding.artistPlayer.setText(json);
    }
}