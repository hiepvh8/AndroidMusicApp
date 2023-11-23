package com.example.androidmusicapp.view.authActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.databinding.ActivityRegisterBinding;
import com.example.androidmusicapp.model.entity.User;
import com.example.androidmusicapp.viewmodel.authViewModel.registerViewModel;


public class RegisterActivity extends AppCompatActivity {
    private registerViewModel registerViewModel;
    private ActivityRegisterBinding activityRegisterBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());

        registerViewModel = new ViewModelProvider(this).get(com.example.androidmusicapp.viewmodel.authViewModel.registerViewModel.class);

        EditText editTextRegisterName = activityRegisterBinding.editTextRegisterName;
        EditText editTextRegisterEmail = activityRegisterBinding.editTextRegisterEmail;
        EditText editTextRegisterPassword = activityRegisterBinding.editTextRegisterPassword;
        EditText editTextRegisterCfPassword = activityRegisterBinding.editTextRegisterCfPassword;

        ProgressBar progressBar = activityRegisterBinding.progressBar;
        //register
        activityRegisterBinding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = editTextRegisterName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textPassword = editTextRegisterPassword.getText().toString();
                String textCfPassword = editTextRegisterCfPassword.getText().toString();
                if(TextUtils.isEmpty(textName)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                    editTextRegisterName.setError("Nhập tên");
                    editTextRegisterName.requestFocus();
                } else if (textName.contains(" ")) {
                    Toast.makeText(RegisterActivity.this, "Tên không được chứa dấu cách", Toast.LENGTH_SHORT).show();
                    editTextRegisterName.setError("Tên không được chứa dấu cách");
                    editTextRegisterName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập email ", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Nhập email");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(RegisterActivity.this, "Không đúng định dạng email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("email không đúng định dạng");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập mật khẩu ", Toast.LENGTH_SHORT).show();
                    editTextRegisterPassword.setError("Nhập mật khẩu");
                    editTextRegisterPassword.requestFocus();
                }else if (textPassword.length()<6){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập mật khẩu ", Toast.LENGTH_SHORT).show();
                    editTextRegisterPassword.setError("Mật khẩu quá ngắn");
                    editTextRegisterPassword.requestFocus();
                } else if (TextUtils.isEmpty(textCfPassword)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
                    editTextRegisterCfPassword.setError("Xác nhận mật khẩu");
                    editTextRegisterCfPassword.requestFocus();
                } else if (!textPassword.equals(textCfPassword)){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                    editTextRegisterCfPassword.setError("không khớp");
                    editTextRegisterCfPassword.requestFocus();
                    //clear pass
                    editTextRegisterPassword.clearComposingText();
                    editTextRegisterCfPassword.clearComposingText();
                } else {
                    progressBar.setVisibility(view.VISIBLE);
                    User user = new User(textName,textEmail,textPassword);
                    registerViewModel.registerUser(user);
                    registerViewModel.getIsRegisterSuccessful().observe(RegisterActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean isRegisterSuccessful) {
                            if(isRegisterSuccessful){
                                editTextRegisterEmail.setText("");
                                editTextRegisterName.setText("");
                                editTextRegisterPassword.setText("");
                                editTextRegisterCfPassword.setText("");
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Đã xảy ra lỗi khi đăng ký", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    registerViewModel.getErrorMessage().observe(RegisterActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String errorMessage) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        //show hide pass
        ImageView imageViewShowHidePass = activityRegisterBinding.imageViewShowHidePass;
        imageViewShowHidePass.setImageResource(R.drawable.ic_hide_pass);
        imageViewShowHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextRegisterPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if pass is visible then hide it
                    editTextRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change icon
                    imageViewShowHidePass.setImageResource(R.drawable.ic_hide_pass);
                } else {
                    editTextRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePass.setImageResource(R.drawable.ic_show_pass);
                }
            }
        });
        //show hide cf pass
        ImageView imageViewShowHideCfPass = activityRegisterBinding.imageViewShowHideCfPass;
        imageViewShowHideCfPass.setImageResource(R.drawable.ic_hide_pass);
        imageViewShowHideCfPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextRegisterCfPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if pass is visible then hide it
                    editTextRegisterCfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change icon
                    imageViewShowHideCfPass.setImageResource(R.drawable.ic_hide_pass);
                } else {
                    editTextRegisterCfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHideCfPass.setImageResource(R.drawable.ic_show_pass);
                }
            }
        });
        //login
        activityRegisterBinding.textViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }
}