package com.example.androidmusicapp.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.adapter.SearchAdapter;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.viewmodel.homeViewModel;

import java.util.ArrayList;


public class LibararyFragment extends Fragment {
    ApiService apiService;
    private RecyclerView recyclerView;
    private SearchAdapter playlistAdapter;

    private homeViewModel homeViewModel;
    private ArrayList<Song> songList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        songList = new ArrayList<>();
        View view =  inflater.inflate(R.layout.fragment_libarary, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.PlaylistDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        playlistAdapter = new SearchAdapter(getActivity(), songList);
        recyclerView.setAdapter(playlistAdapter);

        homeViewModel = new ViewModelProvider(this).get(homeViewModel.class);
        homeViewModel.getSongListObserver().observe(getViewLifecycleOwner(), new Observer<ArrayList<Song>>() {
            @Override
            public void onChanged(ArrayList<Song> arrayList) {
                if(homeViewModel != null){
                    songList = arrayList;
                    playlistAdapter.setSongList(arrayList);
                }
            }
        });
        homeViewModel.loadSong();
        return view;
    }
}