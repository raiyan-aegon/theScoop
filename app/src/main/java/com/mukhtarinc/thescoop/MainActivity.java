package com.mukhtarinc.thescoop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mukhtarinc.thescoop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        ForYouFragment forYouFragment = ForYouFragment.newInstance("","");
        openFragment(forYouFragment);


        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.foryou:
                        Toast.makeText(MainActivity.this, "For you", Toast.LENGTH_SHORT).show();
                        ForYouFragment forYouFragment = ForYouFragment.newInstance("","");
                        openFragment(forYouFragment);
                        break;
                    case R.id.today:
                        Toast.makeText(MainActivity.this, "Today", Toast.LENGTH_SHORT).show();
                        TodayFragment todayFragment = TodayFragment.newInstance("","");
                        openFragment(todayFragment);
                        break;
                    case R.id.follow:
                        Toast.makeText(MainActivity.this, "Following", Toast.LENGTH_SHORT).show();
                        FollowingFragment followingFragment = FollowingFragment.newInstance("","");
                        openFragment(followingFragment);
                        break;
                    case R.id.shelf:
                        Toast.makeText(MainActivity.this, "Shelf", Toast.LENGTH_SHORT).show();
                        ShelfFragment shelfFragment = ShelfFragment.newInstance("","");
                        openFragment(shelfFragment);
                        break;

                }
                return true;
            }
        });


    }

    void openFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }
}
