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
    private MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private LoginResultCallback loginResultCallback;
    public void setLoginResultCallback(LoginResultCallback callback) {
        loginResultCallback = callback;
    }

    public void loginUser(String email, String password) {
        User user = new User(email, password);
        ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
        Call<User> call = apiService.signIn(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userResponse = response.body();
                    String token = userResponse.getToken();
                    loginSuccess.postValue(true);
                    if (loginResultCallback != null) {
                        loginResultCallback.onSuccess(token);
                    }
                } else {
                    errorMessage.postValue("Tài khoản hoặc mật khẩu không chính xác");
                    if (loginResultCallback != null) {
                        loginResultCallback.onError("Tài khoản hoặc mật khẩu không chính xác");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorMessage.postValue("Có lỗi xảy ra");
                if (loginResultCallback != null) {
                    loginResultCallback.onError("Có lỗi xảy ra");
                }
            }
        });
    }

    public interface LoginResultCallback {
        void onSuccess(String token);
        void onError(String errorMessage);
    }
}
