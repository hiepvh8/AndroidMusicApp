package com.example.androidmusicapp.view.fragment;

import android.content.Intent;
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

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.adapter.PlaylistAdapter;
import com.example.androidmusicapp.adapter.PlaylistAdapter2;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.databinding.FragmentPersonalBinding;
import com.example.androidmusicapp.model.entity.Playlist;
import com.example.androidmusicapp.viewmodel.personalViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalFragment extends Fragment {
    private FragmentPersonalBinding fragmentPersonalBinding;
    private personalViewModel personalViewModel;
    private RecyclerView recyclerView;
    private PlaylistAdapter2 playlistAdapter2;
    private ArrayList<Playlist> playlistArrayList;
    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentPersonalBinding = FragmentPersonalBinding.inflate(inflater, container, false);
        personalViewModel = new ViewModelProvider(this).get(com.example.androidmusicapp.viewmodel.personalViewModel.class);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String token = arguments.getString("TOKEN_KEY");
            personalViewModel.fetchUsernameFromToken(token);
        }
        observeViewModel();
        fragmentPersonalBinding.buttonUpdatePersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = fragmentPersonalBinding.textViewName.getText().toString();
                Intent intent = new Intent(requireActivity(), PersonalUpdateActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
        fragmentPersonalBinding.settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = fragmentPersonalBinding.PlaylistDisplay;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        playlistAdapter2 = new PlaylistAdapter2(getActivity(), playlistArrayList);
        recyclerView.setAdapter(playlistAdapter2);
        personalViewModel = new ViewModelProvider(this).get(com.example.androidmusicapp.viewmodel.personalViewModel.class);
        return fragmentPersonalBinding.getRoot();
    }

    private void observeViewModel() {
        personalViewModel.getUsernameLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String username) {
                fragmentPersonalBinding.textViewName.setText(username);
                ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
                Call<ArrayList<Playlist>> call = apiService.getPlaylistByUsername(username);
                call.enqueue(new Callback<ArrayList<Playlist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Playlist>> call, Response<ArrayList<Playlist>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Playlist> playlists = response.body();
                            playlistAdapter2.setPlaylists(playlists);
                        } else {
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Playlist>> call, Throwable t) {
                    }
                });
            }
        });
    }
}