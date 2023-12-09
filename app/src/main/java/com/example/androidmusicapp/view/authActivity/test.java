package com.example.androidmusicapp.view.authActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.api.ApiService;
import com.example.androidmusicapp.api.RetroInstane;
import com.example.androidmusicapp.model.entity.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class test extends AppCompatActivity {
    private TextView textViewToken ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textViewToken = findViewById(R.id.text1); // Ánh xạ TextView từ layout
        // Nhận token từ Intent
        String token = getIntent().getStringExtra("TOKEN_KEY");
        String[] jwParts = token.split("\\.");
        String jwtPayLoad = new String(Base64.decode(jwParts[1],Base64.DEFAULT));
        try {
            JSONObject jsonObject = new JSONObject(jwtPayLoad);
            String username = jsonObject.optString("sub");
            textViewToken.setText(username);
            ApiService apiService = RetroInstane.getRetroClient().create(ApiService.class);
            Call<User> call = apiService.getUserByUsername(username);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        // Xử lý dữ liệu trả về khi thành công
                        User user1 = response.body();
                        Gson gson = new Gson();
                        String userJson = gson.toJson(user1);
                        TextView textViewuser = findViewById(R.id.text2);
                        textViewuser.setText(userJson);
                        // Tiến hành xử lý dữ liệu nhận được từ response ở đây
                    } else {
                        // Xử lý khi có lỗi từ server (có thể kiểm tra response code để biết lý do lỗi)
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            // Xử lý khi có lỗi xảy ra khi phân tích JSON
        }
    }
}
