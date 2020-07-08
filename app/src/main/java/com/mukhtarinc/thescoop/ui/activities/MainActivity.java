package com.mukhtarinc.thescoop.ui.activities;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.ActivityMainBinding;
import com.mukhtarinc.thescoop.ui.fragments.following.FollowingFragment;
import com.mukhtarinc.thescoop.ui.fragments.foryou.ForYouFragment;
import com.mukhtarinc.thescoop.ui.fragments.shelf.ShelfFragment;
import com.mukhtarinc.thescoop.ui.fragments.today.TodayFragment;

import java.util.ArrayList;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    ActivityMainBinding binding ;

   private TodayFragment todayFragment;
   private ForYouFragment forYouFragment;
   private FollowingFragment followingFragment;
   private  ShelfFragment shelfFragment;

    SharedPreferences.Editor editor;
    SharedPreferences preferences;
   ArrayList<Fragment> fragments = new ArrayList<>();

    private static final String TAG = "MainActivity";

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = item -> {

        switch (item.getItemId()){

            case R.id.foryou:
//                int clicks =preferences.getInt("click_for_you",0); ;
//
//                if(clicks==0){
//
                    Fragment forYouFragment  = new ForYouFragment();

                    openFragment(forYouFragment);
//
//
//
//                }else {
//                    clicks =1;
//                    editor.putInt("click_for_you",clicks);
//                    editor.commit();
//
//                    displayFragment(0);
//
//                }



                break;
            case R.id.today:
//                    Toast.makeText(MainActivity.this, "Today", Toast.LENGTH_SHORT).show();


//                int clicks_today =preferences.getInt("click_today",0);
//
//                if(clicks_today==0) {
//
//
                    Fragment fragment = new TodayFragment();
                    openFragment(fragment);
//
//                    clicks_today = 1;
//                    editor.putInt("click_today", clicks_today);
//                    editor.commit();
//
//
//
//                }else {
//
//
//                    Observable.timer(10,TimeUnit.MICROSECONDS)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Observer<Long>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(Long aLong) {
//
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//                                    displayFragment(1);
//                                }
//                            });
//                }
//
//



                break;
            case R.id.follow:
//                    Toast.makeText(MainActivity.this, "Following", Toast.LENGTH_SHORT).show();
                   Fragment fragmentFollow = new FollowingFragment();
                    openFragment(fragmentFollow);
//                Observable.timer(10,TimeUnit.MICROSECONDS)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<Long>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(Long aLong) {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                displayFragment(2);
//                            }
//                        });


                break;
            case R.id.shelf:

//                int clicks_shelf =preferences.getInt("click_shelf",0);
//
//                if(clicks_shelf==0) {
//
//
//
//                    clicks_shelf = 1;
//                    editor.putInt("click_shelf", clicks_shelf);
//                    editor.commit();
//
                    Fragment fragmentShelf = new ShelfFragment();

                    openFragment(fragmentShelf);
//
//                }else {
//
//
//                    Observable.timer(10, TimeUnit.MICROSECONDS)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Observer<Long>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(Long aLong) {
//
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//                                    displayFragment(3);
//                                }
//                            });
//                }
                break;


        }

        return true;
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if(savedInstanceState == null){
            forYouFragment = ForYouFragment.newInstance("","");
            todayFragment = TodayFragment.newInstance();
            followingFragment = FollowingFragment.newInstance();
            shelfFragment = ShelfFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container,forYouFragment)
                    .addToBackStack(null)
                    .commit();


        }
        fragments.add(forYouFragment);
        fragments.add(todayFragment);
        fragments.add(followingFragment);
        fragments.add(shelfFragment);

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

}
