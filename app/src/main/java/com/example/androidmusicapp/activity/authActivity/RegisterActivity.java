package com.example.androidmusicapp.activity.authActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidmusicapp.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextRegisterPassword,editTextRegisterCfPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextRegisterPassword = findViewById(R.id.editText_register_password);
        editTextRegisterCfPassword = findViewById(R.id.editText_register_cf_password);
        //show hide pass
        ImageView imageViewShowHidePass = findViewById(R.id.imageView_show_hide_pass);
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
        ImageView imageViewShowHideCfPass = findViewById(R.id.imageView_show_hide_cf_pass);
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
        TextView textView_Login_Link =findViewById(R.id.textView_login_link);
        textView_Login_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}