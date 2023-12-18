package com.example.androidmusicapp.viewmodel.authViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginViewModel extends ViewModel {
    private MutableLiveData<String> loginResultLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<String> getLoginResultLiveData() {
        return loginResultLiveData;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void performLogin(String email, String password) {
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
        User user = new User(email, password);
        Call<User> call = apiService.signIn(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userResponse = response.body();
                    String token = userResponse.getToken();
                    loginResultLiveData.setValue(token);
                } else {
                    loginResultLiveData.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorMessage.setValue("có lỗi xảy ra");
            }
        });
    }
}

