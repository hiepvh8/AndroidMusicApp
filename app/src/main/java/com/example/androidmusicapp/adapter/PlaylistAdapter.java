package com.example.androidmusicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidmusicapp.R;
import com.example.androidmusicapp.model.entity.Playlist;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private Context context;
    private List<Playlist> playlistList;

    public PlaylistAdapter(Context context, List<Playlist> playlistList) {
        this.context = context;
        this.playlistList = playlistList;
    }

    public void setPlaylists(List<Playlist> playlistList) {
        this.playlistList = playlistList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_items, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlistList.get(position);
        holder.bind(playlist);
    }

    @Override
    public int getItemCount() {
        if (playlistList != null) {
            return playlistList.size();
        }
        return 0;
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        private CardView layoutPlaylist;
        private TextView titleTextView;
        private ImageView img;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPlaylist = itemView.findViewById(R.id.playlist_item);
            titleTextView = itemView.findViewById(R.id.textView_playlist);
            img = itemView.findViewById(R.id.image_playlist);
            img.setImageResource(R.drawable.ic_playlist);
        }

        public void bind(Playlist playlist) {
            titleTextView.setText(String.valueOf(playlist.getTitle()));
        }
    }

}

