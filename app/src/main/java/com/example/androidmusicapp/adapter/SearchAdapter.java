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

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.TestViewholder>  {
    private Context context;
    private List<Song> songList;
    private List<Song> songListOld;


    public SearchAdapter(Context context , List<Song> List) {
        this.context = context;
        this.songList = List;
        this.songListOld = List;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TestViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_items, parent,false);
        return new TestViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewholder holder, int position) {
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

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String strSearch = charSequence.toString();
//                if (strSearch.isEmpty()){
//                    songList = songListOld;
//                }
//                else {
//                    List<Song> list = new ArrayList<>();
//                    for (Song song: songListOld){
//                        if (song.getTitle().toLowerCase().contains(strSearch.toLowerCase())){
//                            list.add(song);
//                        }
//                    }
//                    songList = list;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = songList;
//                return filterResults;
//            }

//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                songList = (List<Song>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public class TestViewholder extends RecyclerView.ViewHolder {
        private CardView layoutSong;
        private ImageView imageViewPlaylist;
        private TextView textViewPlaylist;
        public TestViewholder(@NonNull View itemView) {
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
