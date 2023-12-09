package com.example.androidmusicapp.view.authActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.databinding.ActivityForgotPasswordBinding;
import com.example.androidmusicapp.model.entity.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding activityForgotPasswordBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityForgotPasswordBinding.getRoot());
        EditText editTextForgotPassEmail = activityForgotPasswordBinding.editTextForgotpassEmail;
        activityForgotPasswordBinding.buttonForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}