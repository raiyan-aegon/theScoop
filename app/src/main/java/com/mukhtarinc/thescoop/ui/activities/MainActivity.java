package com.mukhtarinc.thescoop.ui.activities;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    SharedPreferences.Editor editor;
    SharedPreferences preferences;
   ArrayList<Fragment> fragments = new ArrayList<>();

    private static final String TAG = "MainActivity";

    private FirebaseAuth auth;

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = item -> {

        switch (item.getItemId()){

            case R.id.foryou:
//                int clicks =preferences.getInt("click_for_you",0); ;
//
//                if(clicks==0){
////
//
//
//                    Fragment forYouFragment  = new ForYouFragment();
//
//                    openFragment(forYouFragment);
//
//                    clicks =1;
//                    editor.putInt("click_for_you",clicks);
//                    editor.commit();
//
//                    Log.d(TAG, "CLICKS FOR YOU OPEN FRAGMENT");
//
//
//
//                }else {
//
//
//                    Log.d(TAG, "CLICKS FOR YOU DISPLAY FRAGMENT");
//
//                    displayFragment(0);
//
//
//                }


                fm.beginTransaction().hide(active).show(forYouFragment).commit();
                active = forYouFragment;



                break;
            case R.id.today:
//                    Toast.makeText(MainActivity.this, "Today", Toast.LENGTH_SHORT).show();


//                int clicks_today =preferences.getInt("click_today",0);
//
//                if(clicks_today==0) {
//
//
//                    Fragment fragmentToday = new TodayFragment();
//                    openFragment(fragmentToday);
//
//                    clicks_today = 1;
//                    editor.putInt("click_today", clicks_today);
//                    editor.commit();
//
//
//
//                }else {
//
//                                    displayFragment(1);
//
//                }

                fm.beginTransaction().hide(active).show(todayFragment).commit();
                active = todayFragment;





                break;
            case R.id.follow:
//                    Toast.makeText(MainActivity.this, "Following", Toast.LENGTH_SHORT).show();
//                int clicks_follow =preferences.getInt("click_follow",0);
//
//                if(clicks_follow==0) {
//                    Fragment fragmentFollow = new FollowingFragment();
//                    openFragment(fragmentFollow);
//                    clicks_follow = 1;
//                    editor.putInt("click_follow", clicks_follow);
//                    editor.commit();
//
//
//
//
//
//                }else {
//                                    displayFragment(2);
//
//
//                }

                fm.beginTransaction().hide(active).show(followingFragment).commit();
                active = followingFragment;





                break;
            case R.id.shelf:

//                int clicks_shelf =preferences.getInt("click_shelf",0);
//
//                if(clicks_shelf==0) {
//                    Fragment fragmentShelf = new ShelfFragment();
//
//                    openFragment(fragmentShelf);
//
//
//                    clicks_shelf = 1;
//                    editor.putInt("click_shelf", clicks_shelf);
//                    editor.commit();
//
//
//
//                }else {
//
//                                    displayFragment(3);
//
//                }


                fm.beginTransaction().hide(active).show(shelfFragment).commit();
                active = shelfFragment;




                break;


        }

        return true;
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        auth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences(Constants.SHARED_PREFS_CLICK,MODE_PRIVATE);
        editor = preferences.edit();

        fm.beginTransaction().add(R.id.container,shelfFragment,"4").hide(shelfFragment).commit();
        fm.beginTransaction().add(R.id.container,followingFragment,"3").hide(followingFragment).commit();
        fm.beginTransaction().add(R.id.container,todayFragment,"2").hide(todayFragment).commit();
        fm.beginTransaction().add(R.id.container,forYouFragment,"1").commit();


        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

//        if(savedInstanceState == null){
//            forYouFragment = ForYouFragment.newInstance("","");
//            todayFragment = TodayFragment.newInstance();
//            followingFragment = FollowingFragment.newInstance();
//            shelfFragment = ShelfFragment.newInstance();
//
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.container,forYouFragment)
//                    .addToBackStack(null)
//                    .commit();
//
//
//        }
//        fragments.add(forYouFragment);
//        fragments.add(todayFragment);
//        fragments.add(followingFragment);
//        fragments.add(shelfFragment);

    }

    void openFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }

    void displayFragment(int position){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;


        switch (position){

                case 0:



                    fragment = fragments.get(position);
                    if(fragment.isAdded()){

                        transaction.show(fragment);


                    }else {
                        transaction.add(R.id.container,fragment);
                    }

                   if(fragments.get(1).isAdded()){
                       transaction.hide(fragments.get(1));
                   }
                    if(fragments.get(2).isAdded()){
                        transaction.hide(fragments.get(2));
                    }
                    if(fragments.get(3).isAdded()){
                        transaction.hide(fragments.get(3));
                    }
                    transaction.commit();
                    break;

            case 1:
                fragment = fragments.get(position);
                if(fragment.isAdded()){

                    transaction.show(fragment);

                }else {
                    transaction.add(R.id.container,fragment);
                }

                if(fragments.get(0).isAdded()){
                    transaction.hide(fragments.get(0));
                }
                if(fragments.get(2).isAdded()){
                    transaction.hide(fragments.get(2));
                }
                if(fragments.get(3).isAdded()){
                    transaction.hide(fragments.get(3));
                }
                transaction.commit();


                break;
            case 2:
                fragment = fragments.get(position);
                if(fragment.isAdded()){

                    transaction.show(fragment);

                }else {
                    transaction.add(R.id.container,fragment);
                }

                if(fragments.get(0).isAdded()){
                    transaction.hide(fragments.get(0));
                }
                if(fragments.get(1).isAdded()){
                    transaction.hide(fragments.get(1));
                }
                if(fragments.get(3).isAdded()){
                    transaction.hide(fragments.get(3));
                }
                transaction.commit();

                break;
            case 3:
                fragment = fragments.get(position);
                if(fragment.isAdded()){

                    transaction.show(fragment);


                }else {
                    transaction.add(R.id.container,fragment);
                }

                if(fragments.get(0).isAdded()){
                    transaction.hide(fragments.get(0));
                }
                if(fragments.get(1).isAdded()){
                    transaction.hide(fragments.get(1));
                }
                if(fragments.get(2).isAdded()){
                    transaction.hide(fragments.get(2));
                }
                    transaction.commit();

                break;
        }



    }


    @Override
    protected void onStop() {
        super.onStop();
        editor.clear();
        editor.commit();
    }
}
