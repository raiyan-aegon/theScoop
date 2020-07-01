package com.mukhtarinc.thescoop.ui.fragments.shelf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentShelfBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.ui.fragments.foryou.ForYouViewModel;
import com.mukhtarinc.thescoop.utils.ShelfListAdapter;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ShelfFragment extends DaggerFragment {


    FragmentShelfBinding binding;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    ShelfListAdapter shelfListAdapter;

    ShelfViewModel viewModel;



    public ShelfFragment() {
        // Required empty public constructor
    }

    public static ShelfFragment newInstance() {

        return new ShelfFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ShelfViewModel.class);

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shelf,container,false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        binding.shelfArticles.setLayoutManager(linearLayoutManager);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getArticles();


    }


    void getArticles(){


        viewModel.getArticles.observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if(articles!=null){

                    binding.offlineText.setVisibility(View.GONE);
                    shelfListAdapter.setData(articles);
                    binding.shelfArticles.setAdapter(shelfListAdapter);
                }else{
                    binding.offlineText.setVisibility(View.VISIBLE);
                    binding.shelfArticles.setVisibility(View.GONE);
                }

            }
        });

    }
}
