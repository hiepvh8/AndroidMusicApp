package com.example.androidmusicapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.androidmusicapp.R;
import com.example.androidmusicapp.databinding.ActivityMainBinding;
import com.example.androidmusicapp.view.fragment.HomeFragment;
import com.example.androidmusicapp.view.fragment.LibraryFragment;
import com.example.androidmusicapp.view.fragment.PersonalFragment;
import com.example.androidmusicapp.view.fragment.SearchFragment;
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
        String token = getIntent().getStringExtra("TOKEN_KEY");
        Bundle bundle = new Bundle();
        bundle.putString("TOKEN_KEY", token);
        binding.navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.search) {
                replaceFragment(new SearchFragment());
            } else if (itemId == R.id.personal) {
                PersonalFragment personalFragment = new PersonalFragment();
                personalFragment.setArguments(bundle);
                replaceFragment(personalFragment);
            }else if (itemId == R.id.lib) {
                LibraryFragment libraryFragment = new LibraryFragment();
                libraryFragment.setArguments(bundle);
                replaceFragment(libraryFragment);
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