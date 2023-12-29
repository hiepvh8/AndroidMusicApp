package com.example.androidmusicapp.view.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.Playlist;
import com.example.androidmusicapp.view.MainActivity;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlaylistActivity extends AppCompatActivity {
    private EditText editTextPlaylist;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
        editTextPlaylist = findViewById(R.id.edit_playlist);
        Button createButton = findViewById(R.id.button_create);

        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("USERNAME_KEY");
        }

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPlaylist();
                startActivity(new Intent(AddPlaylistActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void createPlaylist() {
        String playlistTitle = editTextPlaylist.getText().toString();
        if (playlistTitle.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tên playlist", Toast.LENGTH_SHORT).show();
            return;
        }

        callApiToCreatePlaylist(username, playlistTitle);
    }

    private void callApiToCreatePlaylist(String username, String playlistTitle) {
        // Tạo đối tượng Playlist với title được nhập từ EditText
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistTitle);

        // Sử dụng Retrofit để gửi đối tượng Playlist lên server
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.addPlaylist(username, playlist);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                            Toast.makeText(AddPlaylistActivity.this, responseBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddPlaylistActivity.this, "Đã xảy ra lỗi khi gửi yêu cầu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddPlaylistActivity.this, "Đã xảy ra lỗi khi kết nối đến server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

