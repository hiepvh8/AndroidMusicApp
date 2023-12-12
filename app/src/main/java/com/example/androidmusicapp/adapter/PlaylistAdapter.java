package com.example.androidmusicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidmusicapp.R;
import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.view.PlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>{
    private Context context;
    private List<Song> songList;

    public PlaylistAdapter(Context context , List<Song> List) {
        this.context = context;
        this.songList = List;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_items, parent,false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.bind(songList.get(position));
        int songIndex = holder.getAdapterPosition();
        holder.layoutSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_song", songList.get(songIndex));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(songList != null){
            return songList.size();
        }
        return 0;
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        private CardView layoutSong;
        private ImageView imageViewPlaylist;
        private TextView textViewPlaylist;
        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutSong = itemView.findViewById(R.id.playlist_item);
            imageViewPlaylist = itemView.findViewById(R.id.image_playlist);
            textViewPlaylist = itemView.findViewById(R.id.textView_playlist);
        }
        public void bind(Song song) {
            Glide.with(context).load(song.getCoverArt()).into(imageViewPlaylist);
            textViewPlaylist.setText(String.valueOf(song.getTitle()));
        }
    }
}
