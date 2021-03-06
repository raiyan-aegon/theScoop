package com.mukhtarinc.thescoop.ui.fragments.today;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentTodayBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.activities.LoginScreenActivity;
import com.mukhtarinc.thescoop.ui.activities.SearchActivity;
import com.mukhtarinc.thescoop.ui.activities.TheScoopDetailsActivity;
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;




public class TodayFragment extends DaggerFragment implements OverflowClickListener, ArticleItemClickListener {

    private final String BUNDLE_RECYCLER_LAYOUT = "TodayFragment.recycler.layout";
    private static final String TAG = "TodayFragment";

    private TodayViewModel viewModel;

    FragmentTodayBinding binding ;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    ConnectivityManager connectivityManager;


    @Inject
    TodayListAdapter todayListAdapter;

    SharedPreferences preferences;

    FirebaseAuth auth;

    int lastPosition;

    private  OverflowClickListener overflowClickListener;
    private ArticleItemClickListener articleItemClickListener;


    Parcelable newListState;

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
        binding.todayList.setHasFixedSize(false);
        binding.toolbar.setNavigationOnClickListener(view -> {

            Intent intent = new Intent(getActivity(), SearchActivity.class);
            requireActivity().startActivity(intent);

        });

       preferences = requireActivity().getSharedPreferences("RECYCLER_POSITION", Context.MODE_PRIVATE);

        binding.profImage.setOnClickListener(view -> {

            if(auth.getCurrentUser()!=null) {

                String[] items = new String[]{Objects.requireNonNull(auth.getCurrentUser()).getDisplayName(), "Log out"};


                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Profile")
                        .setItems(items, (dialogInterface, i) -> {

                            if (i == 1) {

                                auth.signOut();
                            }

                        })
                        .show();

            }else {
                String[] items = new String[]{ "Sign In"};


                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Profile")
                        .setItems(items, (dialogInterface, i) -> {

                            if (i == 0) {

                                Intent intent = new Intent(getActivity(), LoginScreenActivity.class);
                                intent.putExtra("inAlready",true);
                                startActivity(intent);
                            }

                        })
                        .show();


            }

        });
        if(auth.getCurrentUser()!=null) {

            Glide.with(requireContext()).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).placeholder(R.drawable.ic_baseline_person_pin_24).dontAnimate().fitCenter().into(binding.profImage);
        }

       overflowClickListener = this;
       articleItemClickListener = this;
        return binding.getRoot();
    }


    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(TAG, "onAttachFragment");
    }

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);
        Log.d(TAG, "onPrimaryNavigationFragmentChanged");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
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

        auth = FirebaseAuth.getInstance();


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);


        pullArticles();





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
                .observe(getActivity(), todayResponseNetworkResource -> {
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
                            todayListAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.ALLOW);
                            binding.todayList.setAdapter(todayListAdapter);

                            binding.progressToday.setVisibility(View.GONE);
                            binding.todayList.setVisibility(View.VISIBLE);
                        }
                        break;
                    }

                    case ERROR: {
                        binding.progressToday.setVisibility(View.GONE);
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
