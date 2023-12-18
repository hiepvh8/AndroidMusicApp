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

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.adapter.PlaylistAdapter;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.Playlist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LibraryFragment extends Fragment {

    private ApiService apiService;
    private RecyclerView recyclerView;
    private PlaylistAdapter playlistAdapter;
    private ArrayList<Playlist> playlistArrayList = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.PlaylistDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        playlistAdapter = new PlaylistAdapter(getActivity(), playlistArrayList);
        recyclerView.setAdapter(playlistAdapter);


        String username = "hieutest2"; // Thay thế bằng username cần lấy danh sách Playlist
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
        Call<ArrayList<Playlist>> call = apiService.getPlaylistByUsername("hieutest3");
        call.enqueue(new Callback<ArrayList<Playlist>>() {
            @Override
            public void onResponse(Call<ArrayList<Playlist>> call, Response<ArrayList<Playlist>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Playlist> playlists = response.body();
                    playlistAdapter.setPlaylists(playlists); // Cập nhật dữ liệu mới cho adapter
                } else {
                    // Xử lý khi không thành công
                    // Ví dụ: Hiển thị thông báo lỗi
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Playlist>> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                // Ví dụ: Hiển thị thông báo lỗi
            }
        });

        return rootView;
    }
}


