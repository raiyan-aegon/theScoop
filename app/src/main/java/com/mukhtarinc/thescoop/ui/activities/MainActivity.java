package com.mukhtarinc.thescoop.ui.activities;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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


   ArrayList<Fragment> fragments = new ArrayList<>();

    private static final String TAG = "MainActivity";

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            Fragment fragment = null;
            switch (item.getItemId()){

                case R.id.foryou:
                    Toast.makeText(MainActivity.this, "For you", Toast.LENGTH_SHORT).show();
                    //fragment = new ForYouFragment();
                    displayFragment(0);

                    break;
                case R.id.today:
//                    Toast.makeText(MainActivity.this, "Today", Toast.LENGTH_SHORT).show();
//                    fragment = new TodayFragment();
                    displayFragment(1);
                    break;
                case R.id.follow:
//                    Toast.makeText(MainActivity.this, "Following", Toast.LENGTH_SHORT).show();
//                    fragment = new FollowingFragment();
                    displayFragment(2);

                    break;
                case R.id.shelf:
//                    Toast.makeText(MainActivity.this, "Shelf", Toast.LENGTH_SHORT).show();
//                    fragment = new ShelfFragment();
                    displayFragment(3);
                    break;


            }

            return true;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if(savedInstanceState == null){
            forYouFragment = ForYouFragment.newInstance("","");
            todayFragment = TodayFragment.newInstance();
            followingFragment = FollowingFragment.newInstance("","");
            shelfFragment = ShelfFragment.newInstance("","");

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
        Fragment fragment = null;

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
