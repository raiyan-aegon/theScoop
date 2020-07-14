package com.mukhtarinc.thescoop.ui.fragments.foryou;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentForYouBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.activities.LoginScreenActivity;
import com.mukhtarinc.thescoop.ui.activities.SearchActivity;
import com.mukhtarinc.thescoop.ui.activities.TheScoopDetailsActivity;
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment;
import com.mukhtarinc.thescoop.ui.fragments.following.FollowingFragment;
import com.mukhtarinc.thescoop.ui.fragments.shelf.ShelfFragment;
import com.mukhtarinc.thescoop.ui.fragments.today.TodayFragment;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForYouFragment extends DaggerFragment implements OverflowClickListener, ArticleItemClickListener{

    private static final String TAG = "ForYouFragment";

    private ForYouViewModel viewModel;

    FragmentForYouBinding binding;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    TodayListAdapter todayListAdapter;

    private TodayFragment todayFragment;
    private ForYouFragment forYouFragment;
    private FollowingFragment followingFragment;
    private  ShelfFragment shelfFragment;



    private OverflowClickListener overflowClickListener;
    private ArticleItemClickListener articleItemClickListener;


    List<String> sourceIds;

    SharedPreferences preferences;

    private FirebaseAuth auth;

    public ForYouFragment() {
        // Required empty public constructor
    }


    public static ForYouFragment newInstance(String param1, String param2) {

        return new ForYouFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ForYouViewModel.class);

        preferences = getContext().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        auth = FirebaseAuth.getInstance();


    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_for_you,container,false);

        binding.toolbar.setNavigationOnClickListener(view -> {

            Intent intent = new Intent(getActivity(), SearchActivity.class);
            Objects.requireNonNull(getActivity()).startActivity(intent);

        });


        binding.profImage.setOnClickListener(view -> {

            if(auth.getCurrentUser()!=null) {

                String[] items = new String[]{Objects.requireNonNull(auth.getCurrentUser()).getDisplayName(), "Log out"};


                new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()))
                        .setTitle("Profile")
                        .setItems(items, (dialogInterface, i) -> {

                            if (i == 1) {

                                auth.signOut();
                            }

                        })
                        .show();

            }else {
                String[] items = new String[]{ "Sign In"};


                new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()))
                        .setTitle("Profile")
                        .setItems(items, (dialogInterface, i) -> {

                            if (i == 0) {

                                Intent intent = new Intent(getActivity(),LoginScreenActivity.class);
                                startActivity(intent);
                            }

                        })
                        .show();


            }

        });

        if(auth.getCurrentUser()!=null) {

            Glide.with(Objects.requireNonNull(getContext())).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).placeholder(R.drawable.ic_baseline_person_pin_24).dontAnimate().fitCenter().into(binding.profImage);

            Toast.makeText(getContext(), auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        binding.todayList.setLayoutManager(layoutManager);
        binding.todayList.hasFixedSize();


        overflowClickListener = this;
        articleItemClickListener = this;

        Map<String, ?> allPrefs = preferences.getAll();


        StringBuilder sb = new StringBuilder();

        if(allPrefs.size()==0){


            binding.pickSource.setVisibility(View.VISIBLE);
            binding.shimmerLayout.setVisibility(View.GONE);


        }else {

            for(int i=0 ;i <128;i++){





                sb.append(allPrefs.get("sourceName " + i)).append(",");


            }


            Log.d(TAG, "sources :"+sb.toString());

            forYouArticles(sb.toString());
        }


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.pickSource.setOnClickListener(view1 -> {



            ArrayList<Fragment> fragments = new ArrayList<>();


            forYouFragment = ForYouFragment.newInstance("","");
            todayFragment = TodayFragment.newInstance();
            followingFragment = FollowingFragment.newInstance();
            shelfFragment = ShelfFragment.newInstance();


            fragments.add(forYouFragment);
            fragments.add(todayFragment);
            fragments.add(followingFragment);
            fragments.add(shelfFragment);




            FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            Fragment fragment;

            fragment = fragments.get(2);
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


        });

    }

    public void forYouArticles(String source_ids){


        viewModel.getTodayForYouArticles(source_ids,Constants.apiKey)
                    .observe(Objects.requireNonNull(getActivity()), todayResponseNetworkResource -> {
                        if(todayResponseNetworkResource !=null){



                            switch(todayResponseNetworkResource.status)
                            {

                                case LOADING: {

                                    break;
                                }

                                case SUCCESS: {


                                    if(todayResponseNetworkResource.data!=null) {




                                        if(auth.getCurrentUser()!=null){

                                            todayListAdapter.setUser(true,auth);
                                        }


                                        todayListAdapter.setOverflowClickListener(overflowClickListener);
                                        todayListAdapter.setArticleItemClickListener(articleItemClickListener);
                                        todayListAdapter.setData(todayResponseNetworkResource.data.getArticles());
                                        todayListAdapter.setForYou(1);
                                        binding.todayList.setAdapter(todayListAdapter);

                                        binding.shimmerLayout.setVisibility(View.GONE);
                                        binding.todayList.setVisibility(View.VISIBLE);
                                        //binding.staticSalute.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                }

                                case ERROR: {
                                    //binding.staticSalute.setVisibility(View.GONE);
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
    public void articleItemClicked(Article article) {
        Intent intent = new Intent(getActivity(), TheScoopDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("article",article);
        bundle.putParcelable("source",article.getGetSource());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void overflowClicked(Article article) {


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        BottomSheetFragment fragment = new BottomSheetFragment();

        Source source =article.getGetSource();

        Bundle bundle = new Bundle();
        bundle.putParcelable("bottomSheet",article);
        bundle.putParcelable("source",source);

        fragment.setArguments(bundle);
        fragment.show(fragmentManager,fragment.getTag());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
