package com.example.androidmusicapp.viewmodel;

import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.Playlist;
import com.example.androidmusicapp.model.entity.Song;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class libraryViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Playlist>> playlists = new MutableLiveData<>();
    private MutableLiveData<String> usernameLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Playlist>> getPlaylists() {
        return playlists;
    }

    public LiveData<ArrayList<Playlist>> getPlaylistsForUsername() {
        return Transformations.switchMap(usernameLiveData, username -> {
            MutableLiveData<ArrayList<Playlist>> result = new MutableLiveData<>();
            if (username != null) {
                loadPlaylist(username, result);
            }
            return result;
        });
    }

    public MutableLiveData<String> getUsernameLiveData() {
        return usernameLiveData;
    }

    public void setUsername(String username) {
        usernameLiveData.setValue(username);
    }

    public void fetchUsernameFromToken(String token) {
        if (token != null) {
            String[] jwParts = token.split("\\.");
            String jwtPayload = new String(android.util.Base64.decode(jwParts[1], android.util.Base64.DEFAULT));
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(jwtPayload);
                String username = jsonObject.optString("sub");
                usernameLiveData.setValue(username);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadPlaylist(String username, MutableLiveData<ArrayList<Playlist>> resultLiveData) {

    }
}
