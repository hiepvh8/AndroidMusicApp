package com.example.androidmusicapp.view.authActivity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.model.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextRegisterName , editTextRegisterEmail, editTextRegisterPassword, editTextRegisterCfPassword;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextRegisterName = findViewById(R.id.editText_register_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterPassword = findViewById(R.id.editText_register_password);
        editTextRegisterCfPassword = findViewById(R.id.editText_register_cf_password);

        progressBar = findViewById(R.id.progressBar);
        //register
        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
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
                    ApiService.apiService.signUp(user).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                // Xử lý phản hồi thành công từ server
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                editTextRegisterEmail.setText("");
                                editTextRegisterName.setText("");
                                editTextRegisterPassword.setText("");
                                editTextRegisterCfPassword.setText("");
                            } else {
                                try {
                                    String errorBody = response.errorBody().string();
                                    JSONObject jsonObject = new JSONObject(errorBody);
                                    if (jsonObject.has("status") && jsonObject.getString("status").equals("NOT_FOUND")) {
                                        String message = jsonObject.getString("message");
                                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                        if (message.equals("Username đã tồn tại")) {
                                            editTextRegisterName.setError("Tên đã tồn tại");
                                            editTextRegisterName.requestFocus();
                                        } else if (message.equals("Email đã tồn tại")) {
                                            editTextRegisterEmail.setError("Email đã tồn tại");
                                            editTextRegisterEmail.requestFocus();
                                        } else {
                                            editTextRegisterName.setError("Tên đã tồn tại");
                                            editTextRegisterEmail.setError("Email đã tồn tại");
                                        }
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Đã xảy ra lỗi khi đăng ký", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(RegisterActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });


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