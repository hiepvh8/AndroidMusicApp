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

public class LoginActivity extends AppCompatActivity {
    private loginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(com.example.androidmusicapp.viewmodel.authViewModel.loginViewModel.class);

        EditText editTextLoginEmail = activityLoginBinding.editTextLoginEmail;
        EditText editTextLoginPassword = activityLoginBinding.editTextLoginPassword;
        ProgressBar progressBar = activityLoginBinding.progressBar;
        //register
        activityLoginBinding.textViewRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        //forgot password
        activityLoginBinding.textViewForgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        //show hide pass
        ImageView imageViewShowHidePass = activityLoginBinding.imageViewShowHidePass;
        imageViewShowHidePass.setImageResource(R.drawable.ic_hide_pass);
        imageViewShowHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextLoginPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if pass is visible then hide it
                    editTextLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change icon
                    imageViewShowHidePass.setImageResource(R.drawable.ic_hide_pass);
                } else {
                    editTextLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePass.setImageResource(R.drawable.ic_show_pass);
                }
            }
        });
        //Login
        activityLoginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPassword =editTextLoginPassword.getText().toString();
                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Nhâp email");
                    editTextLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    editTextLoginPassword.setError("Nhập mật khẩu");
                    editTextLoginPassword.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    User user = new User(textEmail,textPassword);
                    loginViewModel.loginUser(user);
                    loginViewModel.getIsLoginSuccessful().observe(LoginActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean isLoginSuccessful) {
                            if (isLoginSuccessful) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    loginViewModel.getErrorMessage().observe(LoginActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String errorMessage) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}