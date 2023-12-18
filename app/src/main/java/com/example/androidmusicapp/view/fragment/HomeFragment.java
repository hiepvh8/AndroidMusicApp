package com.example.androidmusicapp.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.adapter.SongAdapter;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.viewmodel.homeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ApiService apiService;
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;

    private homeViewModel homeViewModel;
    static ArrayList<Song> songList;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        songList = new ArrayList<>();
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.SongDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        songAdapter = new SongAdapter(getActivity(), songList);
        recyclerView.setAdapter(songAdapter);


        homeViewModel = new ViewModelProvider(this).get(homeViewModel.class);
        homeViewModel.getSongListObserver().observe(getViewLifecycleOwner(), new Observer<ArrayList<Song>>() {
            @Override
            public void onChanged(ArrayList<Song> arrayList) {
                if(homeViewModel != null){
                    songList = arrayList;
                    songAdapter.setSongList(arrayList);
                }
            }
        });
        homeViewModel.loadSong();
        return view;
    }
}

