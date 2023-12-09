package com.example.androidmusicapp.view.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.databinding.ActivitySettingBinding;
import com.example.androidmusicapp.view.authActivity.LoginActivity;
import com.example.androidmusicapp.view.authActivity.RegisterActivity;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding activitySettingBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(activitySettingBinding.getRoot());
        activitySettingBinding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xóa token từ SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("TOKEN_KEY");
                editor.apply();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                Toast.makeText(SettingActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}