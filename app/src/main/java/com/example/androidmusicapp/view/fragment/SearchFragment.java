package com.example.androidmusicapp.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.viewmodel.homeViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private homeViewModel homeViewModel;
    private ArrayList<Song> songArrayList;
    private SearchAdapter songAdapter;
    private SearchView searchView;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        songAdapter = new SearchAdapter(getActivity(), songArrayList);
        recyclerView.setAdapter(songAdapter);
        searchView = view.findViewById(R.id.searchView);
        homeViewModel = new ViewModelProvider(this).get(homeViewModel.class);

        homeViewModel.getSongListObserver().observe(getViewLifecycleOwner(), new Observer<ArrayList<Song>>() {
            @Override
            public void onChanged(ArrayList<Song> arrayList) {
                if(homeViewModel != null){
                    songArrayList = arrayList;
                    songAdapter.setSongList(arrayList);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //songAdapter.getFilter();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    searchSongs(newText);
                } else {
                    // If the search query is empty, load all songs
                    songAdapter.setSongList(null);

                }
                return false;
            }

        });
        //homeViewModel.loadSong();

        return view;
    }

    private void searchSongs(String query) {
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);

        Call<ArrayList<Song>> call = apiService.getSongTitle(query);

        call.enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Song> searchedSongs = response.body();
                    songAdapter.setSongList(searchedSongs);
                } else {
                    // Handle error
                    // For example, you can log the error or show a message to the user
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Song>> call, Throwable t) {
                // Handle failure (e.g., network issues)
            }
        });
    }

}