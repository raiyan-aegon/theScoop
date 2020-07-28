package com.mukhtarinc.thescoop.ui.fragments.following;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentFollowingBinding;
import com.mukhtarinc.thescoop.databinding.FragmentForYouBinding;
import com.mukhtarinc.thescoop.databinding.FragmentShelfBinding;
import com.mukhtarinc.thescoop.databinding.SourceItemBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Category;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.activities.CategoryActivity;
import com.mukhtarinc.thescoop.ui.activities.LoginScreenActivity;
import com.mukhtarinc.thescoop.ui.activities.MoreSourcesActivity;
import com.mukhtarinc.thescoop.ui.activities.SearchActivity;
import com.mukhtarinc.thescoop.ui.activities.TheScoopDetailsActivity;
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment;
import com.mukhtarinc.thescoop.ui.fragments.foryou.ForYouViewModel;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.CategoryClickListener;
import com.mukhtarinc.thescoop.utils.CategoryListAdapter;
import com.mukhtarinc.thescoop.utils.AddClickListener;
import com.mukhtarinc.thescoop.utils.CheckClickListener;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.SourceListAdapter;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by Raiyan Mukhtar on 7/8/2020.
 */
public class FollowingFragment extends DaggerFragment implements View.OnClickListener, AddClickListener, CheckClickListener , CategoryClickListener , ArticleItemClickListener, OverflowClickListener {

    private static final String TAG = "FollowingFragment";

    @Inject
    CategoryListAdapter categoryListAdapter;

    @Inject
    SourceListAdapter sourceListAdapter;

    FragmentFollowingBinding binding;

    FollowingViewModel viewModel;

    ForYouViewModel forYouViewModel;
    private  OverflowClickListener overflowClickListener;
    private ArticleItemClickListener articleItemClickListener;

    @Inject
    TodayListAdapter todayListAdapter;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    List<Source> sourcesList;

    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    AddClickListener mAddClickListener;

    CheckClickListener checkClickListener;

    CategoryClickListener categoryClickListener;

    SourceItemBinding sourceItemBinding;

    View v;

    FirebaseAuth auth;


    public FollowingFragment() {
    }

    public static FollowingFragment newInstance() {
        return new FollowingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(FollowingViewModel.class);

        forYouViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ForYouViewModel.class);

        preferences = getContext().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);

        editor = preferences.edit();

        mAddClickListener = this;

        checkClickListener = this;

        categoryClickListener = this;

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false);

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

                                Intent intent = new Intent(getActivity(), LoginScreenActivity.class);
                                startActivity(intent);
                            }

                        })
                        .show();


            }

        });
        if(auth.getCurrentUser()!=null) {

            Glide.with(Objects.requireNonNull(getContext())).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).placeholder(R.drawable.ic_baseline_person_pin_24).dontAnimate().fitCenter().into(binding.profImage);
        }

        binding.noConnection.findViewById(R.id.retry).setOnClickListener(view1 -> {
            binding.noConnection.setVisibility(View.GONE);
            pullcategories();
            initSources(3);
        });


        overflowClickListener = this;
        articleItemClickListener = this;


        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.popularSourceRv.addItemDecoration(itemDecoration);
        binding.popularSourceRv.setLayoutManager(linearLayoutManager);
        binding.popularSourceRv.hasFixedSize();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setReverseLayout(false);
        binding.categoriesRv.setLayoutManager(gridLayoutManager);
        binding.categoriesRv.hasFixedSize();

        pullcategories();
        initSources(3);

        binding.linearLayout.setOnClickListener(this);







    }


    void pullcategories() {

        if (binding != null) {
            categoryListAdapter.setData(viewModel.getCategories());
            categoryListAdapter.setCategoryClickListener(categoryClickListener);
            binding.categoriesRv.setAdapter(categoryListAdapter);
        } else {
            Log.d(TAG, "pullcategories: Binding is null ");

        }

    }


    void initSources(int num) {

        viewModel.getSources(Constants.apiKey)
                .observe(getActivity(), sourceResponseNetworkResource -> {

                    if (sourceResponseNetworkResource != null) {


                        switch (sourceResponseNetworkResource.status) {

                            case LOADING: {

                                break;
                            }

                            case SUCCESS: {


                                if (sourceResponseNetworkResource.data != null) {


                                    sourceListAdapter.setOnCheckClickListener(checkClickListener);
                                    sourceListAdapter.setAddClickListener(mAddClickListener);
                                    // sourceListAdapter.setArticleItemClickListener(articleItemClickListener);

                                    if (num != 0) {
                                        sourceListAdapter.setCount(3);
                                    }
                                    sourcesList = sourceResponseNetworkResource.data.getSources();

                                    sourceListAdapter.setData(sourceResponseNetworkResource.data.getSources());
                                    binding.popularSourceRv.setAdapter(sourceListAdapter);

                                    binding.progressFollow.setVisibility(View.GONE);
                                    binding.constraintFollow.setVisibility(View.VISIBLE);
                                }
                                break;
                            }

                            case ERROR: {
                                binding.progressFollow.setVisibility(View.GONE);
                                binding.noConnection.setVisibility(View.VISIBLE);
                                binding.constraintFollow.setVisibility(View.GONE);
                                Toast.makeText(getContext(), sourceResponseNetworkResource.message + "", Toast.LENGTH_SHORT).show();
                                break;
                            }

                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {


        Intent intent = new Intent(getActivity(), MoreSourcesActivity.class);
        intent.putParcelableArrayListExtra("sources", list2ArrayList(sourcesList));

        getContext().startActivity(intent);
    }

    public static ArrayList<Source> list2ArrayList(List<Source> sourceList) {


        return new ArrayList<>(sourceList);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void AddClicked(SourceItemBinding bindings, int position, Source source) {

        Constants.showSnackBar(Objects.requireNonNull(getActivity()),"You're following "+source.getName(),true,source);


        editor.putString("sourceName "+position,source.getSource_id());

        getArticles_For_you(source.getSource_id());

        editor.apply();

        bindings.add.setVisibility(View.GONE);
        bindings.check.setVisibility(View.VISIBLE);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void CheckClicked(SourceItemBinding bindings, int position, Source source) {
        Constants.showSnackBar(getActivity(),"You stopped following " + source.getName(),false,source);

        editor.remove("sourceName "+position);
        editor.commit();
        bindings.add.setVisibility(View.VISIBLE);
        bindings.check.setVisibility(View.GONE);


        getArticles_For_you("");

    }

    @Override
    public void categoryClick(@NotNull Category category) {

        Log.d(TAG, category.getCat_name());
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }

    public void getArticles_For_you(String source_ids){

        View rootView  = getActivity().getWindow().getDecorView().findViewById(R.id.for_parent);

        FragmentForYouBinding for_you_binding = DataBindingUtil.getBinding(rootView);

        forYouViewModel.getTodayForYouArticles(source_ids,Constants.apiKey)
                .observe(Objects.requireNonNull(getActivity()), todayResponseNetworkResource -> {
                    if(todayResponseNetworkResource !=null){



                        switch(todayResponseNetworkResource.status)
                        {

                            case LOADING: {

                                break;
                            }

                            case SUCCESS: {


                                if(todayResponseNetworkResource.data!=null) {



//
//                                    if(auth.getCurrentUser()!=null){
//
//                                        todayListAdapter.setUser(true,auth);
//                                    }


                                    todayListAdapter.setOverflowClickListener(overflowClickListener);
                                    todayListAdapter.setArticleItemClickListener(articleItemClickListener);
                                    todayListAdapter.setData(todayResponseNetworkResource.data.getArticles());
                                    todayListAdapter.setForYou(1);
                                    for_you_binding.todayList.setAdapter(todayListAdapter);

                                    for_you_binding.progressForYou.setVisibility(View.GONE);
                                    for_you_binding.todayList.setVisibility(View.VISIBLE);
                                    //binding.staticSalute.setVisibility(View.VISIBLE);
                                }
                                break;
                            }

                            case ERROR: {
                                //binding.staticSalute.setVisibility(View.GONE);
                                for_you_binding.progressForYou.setVisibility(View.GONE);
                                for_you_binding.noConnection.setVisibility(View.VISIBLE);
                                for_you_binding.todayList.setVisibility(View.GONE);
                                break;
                            }

                        }

                    }
                });

    }

    @Override
    public void articleItemClicked(Article article) {

        Intent intent = new Intent(getActivity(), TheScoopDetailsActivity.class);
        Source source = new Source();
        source.setName(article.getSourceName());

        Bundle bundle = new Bundle();
        bundle.putParcelable("article",article);
        bundle.putParcelable("source",source);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void overflowClicked(Article article) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        BottomSheetFragment fragment = new BottomSheetFragment();

        Source source = new Source();
        source.setName(article.getSourceName());

        Bundle bundle = new Bundle();
        bundle.putParcelable("bottomSheet",article);
        bundle.putParcelable("source",source);

        fragment.setArguments(bundle);
        fragment.show(fragmentManager,fragment.getTag());
    }
}
