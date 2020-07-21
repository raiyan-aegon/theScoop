package com.mukhtarinc.thescoop.ui.activities;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.ActivityMainBinding;
import com.mukhtarinc.thescoop.ui.fragments.following.FollowingFragment;
import com.mukhtarinc.thescoop.ui.fragments.foryou.ForYouFragment;
import com.mukhtarinc.thescoop.ui.fragments.shelf.ShelfFragment;
import com.mukhtarinc.thescoop.ui.fragments.today.TodayFragment;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.NotificationWorker;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends DaggerAppCompatActivity {

    ActivityMainBinding binding ;

   private final TodayFragment todayFragment = new TodayFragment();
   private final ForYouFragment forYouFragment = new ForYouFragment();
   private final FollowingFragment followingFragment = new FollowingFragment();
   private  final ShelfFragment shelfFragment = new ShelfFragment();

   final FragmentManager fm = getSupportFragmentManager();

   Fragment active = forYouFragment;


    private static final String TAG = "MainActivity";

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {

            case R.id.foryou:


                    fm.beginTransaction().hide(active).show(forYouFragment).commit();
                    active = forYouFragment;


                break;
            case R.id.today:

                    fm.beginTransaction().hide(active).show(todayFragment).commit();
                    active = todayFragment;




                break;
            case R.id.follow:


                    fm.beginTransaction().hide(active).show(followingFragment).commit();
                    active = followingFragment;




                break;
            case R.id.shelf:

                    Fragment fragmentShelf = new ShelfFragment();

                    openFragment(fragmentShelf);


                fm.beginTransaction().add(R.id.container,followingFragment,"3").hide(followingFragment).commit();
                fm.beginTransaction().add(R.id.container,todayFragment,"2").hide(todayFragment).commit();
                fm.beginTransaction().add(R.id.container,forYouFragment,"1").hide(forYouFragment).commit();


                break;


        }

        return true;
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        fm.beginTransaction().add(R.id.container,shelfFragment,"4").hide(shelfFragment).commit();
        fm.beginTransaction().add(R.id.container,followingFragment,"3").hide(followingFragment).commit();
        fm.beginTransaction().add(R.id.container,todayFragment,"2").hide(todayFragment).commit();
        fm.beginTransaction().add(R.id.container,forYouFragment,"1").commit();


        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


    }

    void openFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }




    @Override
    protected void onStop() {
        super.onStop();
    }
}
