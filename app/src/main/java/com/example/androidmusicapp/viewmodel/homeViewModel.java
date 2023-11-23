package com.example.androidmusicapp.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.Song;
import com.example.androidmusicapp.view.fragment.HomeFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Song>> songList;
    public homeViewModel(){
        songList = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Song>> getSongListObserver(){
        return songList;
    }

    public void loadSong(){
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
        Call<ArrayList<Song>> call = apiService.getSongs();
        call.enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                if (response.isSuccessful()) {
                    songList.postValue(response.body());
                } else {
                    // Handle error
                    Toast.makeText(HomeFragment.newInstance().getContext(), "Ngon", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Song>> call, Throwable t) {
                songList.postValue(null);
                Toast.makeText(HomeFragment.newInstance().getContext(), "Ngon", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
