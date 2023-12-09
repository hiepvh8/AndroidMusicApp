package com.example.androidmusicapp.view.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.databinding.ActivityPersonalUpdateBinding;
import com.example.androidmusicapp.model.entity.User;
import com.example.androidmusicapp.viewmodel.personalViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalUpdateActivity extends AppCompatActivity {
    private ActivityPersonalUpdateBinding activityPersonalUpdateBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPersonalUpdateBinding = ActivityPersonalUpdateBinding.inflate(getLayoutInflater());
        setContentView(activityPersonalUpdateBinding.getRoot());
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("USERNAME");
            ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
            Call<User> call = apiService.getUserByUsername(username);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        if (user != null) {
                            String name = user.getUsername();
                            String email = user.getEmail();
                            String birthday = user.getBirthday();
                            String gender = user.getGender();
                            String phone = user.getPhoneNumber();
                            activityPersonalUpdateBinding.editTextUpdateName.setText(name);
                            activityPersonalUpdateBinding.editTextUpdateEmail.setText(email);
                            activityPersonalUpdateBinding.editTextUpdateBirthday.setText(birthday);
                            activityPersonalUpdateBinding.editTextUpdateGender.setText(gender);
                            activityPersonalUpdateBinding.editTextUpdatePhone.setText(phone);
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }
}