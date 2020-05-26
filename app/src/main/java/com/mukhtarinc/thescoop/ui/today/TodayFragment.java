package com.mukhtarinc.thescoop.ui.today;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentTodayBinding;
import com.mukhtarinc.thescoop.network.today.TodayApi;
import com.mukhtarinc.thescoop.network.today.TodayResponse;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;


import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import retrofit2.Retrofit;


public class TodayFragment extends DaggerFragment {

    private static final String TAG = "TodayFragment";

    private TodayViewModel viewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;




    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance() {

        return new TodayFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTodayBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today,container,false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(TodayViewModel.class);


        viewModel.getTodayArticle("us", Constants.apiKey);

        FragmentTodayBinding binding = DataBindingUtil.getBinding(view);



        subscribeObservers(binding);

    }

    void subscribeObservers(FragmentTodayBinding binding){


        viewModel.getArticles().observe(this, new Observer<TodayResponse>() {
            @Override
            public void onChanged(TodayResponse todayResponse) {

                if(todayResponse!=null){


                    //Toast.makeText(getContext(), "Processing", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onChanged: "+todayResponse.getArticles().get(0).getTitle());

                    binding.articleTitle.setText(todayResponse.getArticles().get(0).getTitle());
                    binding.articleDate.setText(todayResponse.getArticles().get(0).getPublishedAt());



                }
            }
        });
    }
}
