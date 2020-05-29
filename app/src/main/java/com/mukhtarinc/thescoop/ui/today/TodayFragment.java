package com.mukhtarinc.thescoop.ui.today;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.telephony.AvailableNetworkInfo;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentTodayBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.network.today.TodayApi;
import com.mukhtarinc.thescoop.network.today.TodayResource;
import com.mukhtarinc.thescoop.network.today.TodayResponse;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.TheScoopDateUtils;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;
import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;


public class TodayFragment extends DaggerFragment {

    private static final String TAG = "TodayFragment";

    private TodayViewModel viewModel;

    FragmentTodayBinding binding ;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    ConnectivityManager connectivityManager;


    @Inject
    TodayListAdapter todayListAdapter;


    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance() {

        return new TodayFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today,container,false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        binding.todayList.setLayoutManager(layoutManager);
        binding.todayList.hasFixedSize();




        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(TodayViewModel.class);





        if(hasNetwork()){

            viewModel.getTodayArticle("us",Constants.apiKey);
            subscribeObservers();

        }else {

            binding.noConnection.setVisibility(View.VISIBLE);
            binding.todayList.setVisibility(View.GONE);
        }


    }


    boolean hasNetwork(){


        boolean isConnected = false;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                isConnected = true;
            }

        }
        Log.d(TAG, "hasNetwork: "+isConnected);
        return isConnected;
    }

    void subscribeObservers(){

       // RxJavaPlugins.setErrorHandler(throwable -> Log.e("MainActivity","Uncaught: "+throwable.toString()));



        viewModel.getArticles().observe(this, new Observer<TodayResource<TodayResponse>>() {
            @Override
            public void onChanged(TodayResource<TodayResponse> todayResponseTodayResource) {
                if(todayResponseTodayResource!=null){


                    switch(todayResponseTodayResource.status)
                    {

                        case LOADING: {
                            binding.shimmerLayout.startShimmerAnimation();

                            break;
                        }

                        case SUCCESS: {


                            if(todayResponseTodayResource.data!=null) {



                                todayListAdapter.setData(todayResponseTodayResource.data.getArticles());
                                binding.todayList.setAdapter(todayListAdapter);

                                binding.shimmerLayout.stopShimmerAnimation();
                                binding.shimmerLayout.setVisibility(View.GONE);
                                binding.todayList.setVisibility(View.VISIBLE);
                            }
                            break;
                        }

                        case ERROR: {
                            binding.shimmerLayout.setVisibility(View.GONE);
                            binding.noConnection.setVisibility(View.VISIBLE);
                            binding.todayList.setVisibility(View.GONE);
                            break;
                        }

                    }

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        binding.shimmerLayout.stopShimmerAnimation();
        super.onPause();

    }




}
