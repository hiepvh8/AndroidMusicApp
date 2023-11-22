package com.example.androidmusicapp.view.authActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.androidmusicapp.R;

public class test extends AppCompatActivity {
    private TextView textViewToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textViewToken = findViewById(R.id.textViewtest); // Ánh xạ TextView từ layout

        // Lấy token từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", ""); // Lấy token, "" là giá trị mặc định nếu không tìm thấy

        // Đặt token lên TextView
        textViewToken.setText(token);
    }
}