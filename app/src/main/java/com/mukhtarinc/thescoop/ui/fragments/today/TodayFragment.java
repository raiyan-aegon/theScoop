package com.mukhtarinc.thescoop.ui.fragments.today;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentTodayBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.activities.TheScoopDetailsActivity;
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TodayFragment extends DaggerFragment implements OverflowClickListener, ArticleItemClickListener {

    private static final String TAG = "TodayFragment";

    private TodayViewModel viewModel;

    FragmentTodayBinding binding ;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    ConnectivityManager connectivityManager;


    @Inject
    TodayListAdapter todayListAdapter;

    private  OverflowClickListener overflowClickListener;
    private ArticleItemClickListener articleItemClickListener;


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




       overflowClickListener = this;
       articleItemClickListener = this;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        Log.d(TAG, "onViewCreated");

        binding.noConnection.findViewById(R.id.retry).setOnClickListener(view1 -> {
            pullArticles();
            binding.noConnection.setVisibility(View.GONE);
        });



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(TodayViewModel.class);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        pullArticles();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        binding.todayList.setLayoutManager(layoutManager);
        binding.todayList.hasFixedSize();



        binding.swipeRefresh.setOnRefreshListener(() -> {

            binding.swipeRefresh.setRefreshing(true);
            Observable.timer(3, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new io.reactivex.Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(Long aLong) {
                            pullArticles();
                            Log.d(TAG, "onNext: ");
                        }

                        @Override
                        public void onError(Throwable e) {

                            Log.d(TAG, "onError: ");
                        }

                        @Override
                        public void onComplete() {
                            binding.swipeRefresh.setRefreshing(false);

                            Log.d(TAG, "onComplete: ");
                        }
                    });


        });
    }




    void pullArticles(){



        viewModel.getTodayArticles("us",Constants.apiKey)
                .observe(this, todayResponseNetworkResource -> {
            if(todayResponseNetworkResource !=null){



                switch(todayResponseNetworkResource.status)
                {

                    case LOADING: {

                        break;
                    }

                    case SUCCESS: {


                        if(todayResponseNetworkResource.data!=null) {



                            todayListAdapter.setOverflowClickListener(overflowClickListener);
                            todayListAdapter.setArticleItemClickListener(articleItemClickListener);
                            todayListAdapter.setData(todayResponseNetworkResource.data.getArticles());
                            binding.todayList.setAdapter(todayListAdapter);

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
        });

    }


    @Override
    public void overflowClicked(Article article) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        BottomSheetFragment fragment = new BottomSheetFragment();

        Source source =article.getGetSource();

        Bundle bundle = new Bundle();
        bundle.putParcelable("bottomSheet",article);
        Log.d(TAG, "overflowClicked: "+source.getName());

        bundle.putParcelable("source",source);

        fragment.setArguments(bundle);
        fragment.show(fragmentManager,fragment.getTag());

    }

    @Override
    public void articleItemClicked(Article article) {

        Intent intent = new Intent(getActivity(), TheScoopDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("article",article);
        bundle.putParcelable("source",article.getGetSource());
        intent.putExtras(bundle);
        startActivity(intent);

    }


}
