package com.mukhtarinc.thescoop.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.ActivityCategoryBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Category;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment;
import com.mukhtarinc.thescoop.ui.fragments.following.CategoryViewModel;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class CategoryActivity extends DaggerAppCompatActivity implements ArticleItemClickListener, OverflowClickListener {

    private static final String TAG = "CategoryActivity";
    CategoryViewModel viewModel;

    Category category;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;



    @Inject
    TodayListAdapter todayListAdapter;


    ActivityCategoryBinding binding;


    ArticleItemClickListener articleItemClickListener;
    OverflowClickListener overflowClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);

        binding.toolbar.setNavigationOnClickListener(view -> {

            onBackPressed();

        });


        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(CategoryViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        articleItemClickListener =this;
        overflowClickListener = this;

        binding.categoriesArticles.setLayoutManager(layoutManager);
        binding.categoriesArticles.hasFixedSize();




        if(getIntent()!=null && getIntent().hasExtra("category")){

            category = getIntent().getParcelableExtra("category");

            binding.setCategory(category);


            getCategoryArticles(category);
        }

    }

    public void getCategoryArticles(Category category){
        viewModel.getCategoryArticles(category.getCat_name(),Constants.apiKey)
                .observe(this, todayResponseNetworkResource -> {
                    if(todayResponseNetworkResource !=null){



                        switch(todayResponseNetworkResource.status)
                        {

                            case LOADING: {

                                break;
                            }

                            case SUCCESS: {


                                if(todayResponseNetworkResource.data!=null) {


                                    Log.d(TAG, "getCategoryArticles: ");


                                    if (todayListAdapter == null) {

                                        Log.d(TAG, "Adapter is null");
                                    }

                                    todayListAdapter.setCategory(category);
                                    todayListAdapter.setOverflowClickListener(overflowClickListener);
                                    todayListAdapter.setArticleItemClickListener(articleItemClickListener);
                                    todayListAdapter.setData(todayResponseNetworkResource.data.getArticles());
                                   // todayListAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.ALLOW);
                                    binding.categoriesArticles.setAdapter(todayListAdapter);

                                    binding.progressCategories.setVisibility(View.GONE);
                                    binding.categoriesArticles.setVisibility(View.VISIBLE);
                                }
                                break;
                            }

                            case ERROR: {
                                binding.progressCategories.setVisibility(View.GONE);
                               // binding.noConnection.setVisibility(View.VISIBLE);
                                binding.categoriesArticles.setVisibility(View.GONE);
                                break;
                            }

                        }

                    }
                });

    }

    @Override
    public void articleItemClicked(Article article) {
        Intent intent = new Intent(this, TheScoopDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("article",article);
        bundle.putParcelable("source",article.getGetSource());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void overflowClicked(Article article) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        BottomSheetFragment fragment = new BottomSheetFragment();

        Source source =article.getGetSource();

        Bundle bundle = new Bundle();
        bundle.putParcelable("bottomSheet",article);
        Log.d(TAG, "overflowClicked: "+source.getName());

        bundle.putParcelable("source",source);

        fragment.setArguments(bundle);
        fragment.show(fragmentManager,fragment.getTag());

    }
}