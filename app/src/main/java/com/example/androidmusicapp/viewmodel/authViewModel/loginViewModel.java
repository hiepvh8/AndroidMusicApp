package com.example.androidmusicapp.viewmodel.authViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLoginSuccessful() {
        return isLoginSuccessful;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loginUser(User user){
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
        Call<User> call = apiService.signIn(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    isLoginSuccessful.setValue(true);
                } else {
                    isLoginSuccessful.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorMessage.setValue("có lỗi xảy ra");
            }
        });
    }
}
