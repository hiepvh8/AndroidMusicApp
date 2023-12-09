package com.example.androidmusicapp.viewmodel.authViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerViewModel extends ViewModel {
    private MutableLiveData<Boolean> isRegisterSuccessful = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> getIsRegisterSuccessful(){
        return isRegisterSuccessful;
    }
    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void registerUser(User user){
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
        Call<User> call = apiService.signUp(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    isRegisterSuccessful.setValue(true);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        if (jsonObject.has("status") && jsonObject.getString("status").equals("NOT_FOUND")) {
                            String message = jsonObject.getString("message");
                            errorMessage.setValue(message);
                        } else {
                            errorMessage.setValue("Đã xảy ra lỗi khi đăng ký");
                        }
                    } catch (IOException | JSONException e) {
                        errorMessage.setValue("Đã xảy ra lỗi");
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorMessage.setValue("Có lỗi xảy ra ");
            }
        });
    }
}
