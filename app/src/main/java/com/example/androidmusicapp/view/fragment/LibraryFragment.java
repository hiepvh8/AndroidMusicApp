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
import android.widget.Button;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.adapter.PlaylistAdapter;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.Playlist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.androidmusicapp.viewmodel.personalViewModel;

public class LibraryFragment extends Fragment {
    private PlaylistAdapter playlistAdapter;
    private ArrayList<Playlist> playlistArrayList;
    private personalViewModel personalViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.PlaylistDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        playlistAdapter = new PlaylistAdapter(requireContext(), playlistArrayList);
        recyclerView.setAdapter(playlistAdapter);

        personalViewModel = new ViewModelProvider(this).get(com.example.androidmusicapp.viewmodel.personalViewModel.class);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String token = arguments.getString("TOKEN_KEY");
            personalViewModel.fetchUsernameFromToken(token);
        }

        personalViewModel.getUsernameLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String username) {
                if (username != null) {
                    ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
                    Call<ArrayList<Playlist>> call = apiService.getPlaylistByUsername(username);
                    call.enqueue(new Callback<ArrayList<Playlist>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Playlist>> call, Response<ArrayList<Playlist>> response) {
                            if (response.isSuccessful()) {
                                ArrayList<Playlist> playlists = response.body();
                                playlistAdapter.setPlaylists(playlists);
                            } else {
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Playlist>> call, Throwable t) {
                            // Xử lý lỗi khi gọi API
                        }
                    });
                }
            }
        });

        Button addPlaylistButton = view.findViewById(R.id.addplaylist);
        addPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = personalViewModel.getUsernameLiveData().getValue();
                if (username != null) {
                    Intent intent = new Intent(requireActivity(), AddPlaylistActivity.class);
                    intent.putExtra("USERNAME_KEY", username);
                    startActivity(intent);
                } else {
                    // Xử lý khi không có username
                }
            }
        });

        return view;
    }
}


