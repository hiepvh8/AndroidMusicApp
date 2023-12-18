package com.example.androidmusicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidmusicapp.R;
import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.view.PlayerActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private MediaPlayer mediaPlayer;
    Context context;
    ArrayList<Song> songList;
    private boolean isPlaying = false;
    private AdapterView.OnItemClickListener listener;
    public SongAdapter(Context context, ArrayList<Song> List) {
        this.context = context;
        this.songList = List;
    }

    public void setSongList(ArrayList<Song> songList){
        this.songList = songList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        holder.bind(songList.get(position));
        int songIndex = holder.getAdapterPosition();
        holder.layoutSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, PlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("object_song", songList.get(songIndex));
                    bundle.putInt("position", songIndex);
                    bundle.putInt("size", songList.size());
                    bundle.putSerializable("songList", songList);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
        });

    }


    public void release(){
        context = null;
    }

    @Override
    public int getItemCount() {
        if (this.songList != null){
            return this.songList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView layoutSong;
        private TextView title,duration,genre;
        private ImageView art;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutSong = itemView.findViewById(R.id.song_item);
            title = itemView.findViewById(R.id.SongTitle);
            duration = itemView.findViewById(R.id.SongDuration);
            genre = itemView.findViewById(R.id.SongGenre);
            art = itemView.findViewById(R.id.SongArt);
        }
        public void bind(Song song){
            title.setText(String.valueOf(song.getTitle()));
            duration.setText((getDuration(song.getDuration())));
            genre.setText(String.valueOf(song.getGenre()));
            Glide.with(context).load(song.getCoverArt()).into(art);
        }

        private String getDuration (int duration){
            String totalDurationText;
            int hrs = duration / 3600;
            int min = (duration % 3600) / 60;
            int sec = duration % 60;

            if (hrs < 1){
                totalDurationText = String.format("%02d:%02d", min,sec);
            }
            else {
                totalDurationText = String.format("%1d:%02d:%02d",hrs,min,sec);
            }
            return totalDurationText;
        }
    }
}
