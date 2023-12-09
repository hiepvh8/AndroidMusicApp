package com.example.androidmusicapp.viewmodel;

import android.util.Base64;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class personalViewModel extends ViewModel {
    private MutableLiveData<String> usernameLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getUsernameLiveData() {
        return usernameLiveData;
    }
    public void setUsername(String username) {
        usernameLiveData.setValue(username);
    }
    public void fetchUsernameFromToken(String token) {
        if (token != null) {
            String[] jwParts = token.split("\\.");
            String jwtPayload = new String(Base64.decode(jwParts[1], Base64.DEFAULT));
            try {
                JSONObject jsonObject = new JSONObject(jwtPayload);
                String username = jsonObject.optString("sub");
                usernameLiveData.setValue(username);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
