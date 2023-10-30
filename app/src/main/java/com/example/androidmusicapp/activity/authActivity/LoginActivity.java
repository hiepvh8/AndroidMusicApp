package com.example.androidmusicapp.activity.authActivity;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail , editTextLoginPassword;
    private ProgressBar progressBar;
//    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPassword =findViewById(R.id.editText_login_password);
        progressBar = findViewById(R.id.progressBar);
        //register
        TextView textView_register_link = findViewById(R.id.textView_register_link);
        textView_register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        //forgot password
        TextView textView_forgot_password = findViewById(R.id.textView_forgot_password_link);
        textView_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        //show hide pass
        ImageView imageViewShowHidePass = findViewById(R.id.imageView_show_hide_pass);
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
        Button buttonLogin=findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPassword =editTextLoginPassword.getText().toString();
                //login
//                Toast.makeText(LoginActivity.this, "you are login now", Toast.LENGTH_SHORT).show();
                //Start the userprofile
//                startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
//                finish(); // close activity login
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
//                    loginUser(textEmail,textPassword);loginUser(textEmail,textPassword);
                }
            }
        });
    }

//    private void loginUser(String textEmail, String textPass) {
//        authProfile.signInWithEmailAndPassword(textEmail , textPass).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    FirebaseUser firebaseUser = authProfile.getCurrentUser();
//                    //check if email is verify before user can access
//                    Toast.makeText(LoginActivity.this, "you are login now", Toast.LENGTH_SHORT).show();
//                    //Start the userprofile
//                    startActivity(new Intent(LoginActivity.this,UserProfileActivity.class));
//                    finish(); // close activity login
//                } else {
//                    Toast.makeText(LoginActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
//                }
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//    }

}
