package com.example.androidmusicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidmusicapp.R;
import com.example.androidmusicapp.model.entity.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    Context context;
    List<Song> songList;
    private AdapterView.OnItemClickListener listener;
    public SongAdapter(Context context, List<Song> List) {
        this.context = context;
        this.songList = List;
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
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,duration,genre;
        private ImageView art;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.SongTitle);
            //duration = itemView.findViewById(R.id.SongDuration);
            genre = itemView.findViewById(R.id.SongGenre);
            art = itemView.findViewById(R.id.SongArt);
        }
        public void bind(Song song){
            title.setText(String.valueOf(song.getTitle()));
            //duration.setText(String.valueOf(song.getDuration()));
            genre.setText(String.valueOf(song.getGenre()));
            Glide.with(context).load(song.getCoverArt()).into(art);
        }
    }
}
