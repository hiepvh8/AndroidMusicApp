package com.example.androidmusicapp.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.adapter.PlaylistAdapter;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.Playlist;
import com.example.androidmusicapp.viewmodel.libraryViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LibraryFragment extends Fragment {

    private ApiService apiService;
    private RecyclerView recyclerView;
    private PlaylistAdapter playlistAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_libarary, container, false);

        recyclerView = rootView.findViewById(R.id.PlaylistDisplay);
        playlistAdapter = new PlaylistAdapter(getContext(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(playlistAdapter);

        Retrofit retrofit = RetroInstane.getRetroClient();
        apiService = retrofit.create(ApiService.class);

        // Gọi phương thức API để lấy danh sách Playlist cho một username cụ thể
        String username = "hieutest2"; // Thay thế bằng username cần lấy danh sách Playlist
        Call<Playlist> call = apiService.getPlaylistByUsername(username);
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Playlist playlist = response.body();
                    List<Playlist> playlistList = new ArrayList<>();
                    playlistList.add(playlist);

                    // Cập nhật dữ liệu vào PlaylistAdapter
                    playlistAdapter.setPlaylists(playlistList);
                } else {
                    // Xử lý khi không thành công
                    Log.e("API Call", "Response unsuccessful.");
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                Log.e("API Call", "Failed: " + t.getMessage());
            }
        });

        return rootView;
    }
}


