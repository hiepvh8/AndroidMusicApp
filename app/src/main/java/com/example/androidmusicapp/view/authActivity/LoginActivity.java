package com.example.androidmusicapp.view.authActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.databinding.ActivityLoginBinding;
import com.example.androidmusicapp.view.MainActivity;
import com.example.androidmusicapp.model.entity.User;
import com.example.androidmusicapp.viewmodel.authViewModel.loginViewModel;

public class LoginActivity extends AppCompatActivity implements com.example.androidmusicapp.viewmodel.authViewModel.loginViewModel.LoginResultCallback {
    private loginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(loginViewModel.class);
        loginViewModel.setLoginResultCallback(this);

        EditText editTextLoginEmail = activityLoginBinding.editTextLoginEmail;
        EditText editTextLoginPassword = activityLoginBinding.editTextLoginPassword;
        progressBar = activityLoginBinding.progressBar;

        activityLoginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPassword = editTextLoginPassword.getText().toString();
                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Nhập email");
                    editTextLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    editTextLoginPassword.setError("Nhập mật khẩu");
                    editTextLoginPassword.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginViewModel.loginUser(textEmail,textPassword);
                }
            }
        });
    }

    @Override
    public void onSuccess(String token) {
        Intent intent = new Intent(LoginActivity.this, test.class);
        intent.putExtra("TOKEN_KEY", token);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}