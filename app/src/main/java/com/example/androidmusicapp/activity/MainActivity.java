package com.example.androidmusicapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.databinding.ActivityMainBinding;
import com.example.androidmusicapp.fragment.HomeFragment;
import com.example.androidmusicapp.fragment.LibararyFragment;
import com.example.androidmusicapp.fragment.PersonalFragment;
import com.example.androidmusicapp.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
  //      setContentView(R.layout.activity_main);

//        bottomNavigationView = findViewById(R.id.nav_view);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//
//        binding.navView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.home) {
//               startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//               overridePendingTransition(0,0);
//               return true;
//            } else if (itemId == R.id.search) {
//
//            } else if (itemId == R.id.personal) {
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                overridePendingTransition(0,0);
//                return true;
//
//            }
//            return true;
//        });

//        this.getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        binding.navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.search) {
                replaceFragment(new SearchFragment());
            } else if (itemId == R.id.personal) {
                replaceFragment(new PersonalFragment());
            }else if (itemId == R.id.lib) {
                replaceFragment(new LibararyFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}