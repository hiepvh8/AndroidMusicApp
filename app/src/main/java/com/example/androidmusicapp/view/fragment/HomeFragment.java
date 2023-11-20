package com.example.androidmusicapp.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ApiService apiService;
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    private List<Song> songList;


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
        loadSong();
        return view;

    }

    private void loadSong() {
        ApiService.apiService.getSongs().enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                if (response.isSuccessful()) {
                    // Update the existing songList
                    songList.clear(); // Clear the previous data
                    songList.addAll(response.body());

                    // Notify the adapter that the data has changed
                    songAdapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(getActivity(), "Text!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Song>> call, Throwable t) {
                // Handle failure
                Toast.makeText(getActivity(), "Fail!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

