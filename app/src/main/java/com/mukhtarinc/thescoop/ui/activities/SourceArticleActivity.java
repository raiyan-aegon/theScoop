package com.mukhtarinc.thescoop.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.ActivityCategoryBinding;
import com.mukhtarinc.thescoop.databinding.ActivitySourceArticleBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Category;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment;
import com.mukhtarinc.thescoop.ui.fragments.following.CategoryViewModel;
import com.mukhtarinc.thescoop.ui.fragments.following.SourceViewModel;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by Raiyan Mukhtar on 7/9/2020.
 */
public class SourceArticleActivity extends DaggerAppCompatActivity implements ArticleItemClickListener, OverflowClickListener {

    ActivitySourceArticleBinding binding;


    private static final String TAG = "SourceArticleActivity";
    SourceViewModel viewModel;

    Source source;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;


    @Inject
    TodayListAdapter todayListAdapter;


    ArticleItemClickListener articleItemClickListener;
    OverflowClickListener overflowClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_source_article);

        binding.toolbar.setNavigationOnClickListener(view -> {

            onBackPressed();

        });


        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(SourceViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        articleItemClickListener = this;
        overflowClickListener = this;

        binding.sourceArticles.setLayoutManager(layoutManager);
        binding.sourceArticles.hasFixedSize();


        if (getIntent() != null && getIntent().hasExtra("source")) {

            source = getIntent().getParcelableExtra("source");

            Log.d(TAG, "Source"+source.getSource_id());

            binding.setSource(source);

            getSourceArticles(source);
        }

    }

    public void getSourceArticles(Source source) {
        viewModel.getSourceArticles(source.getSource_id(), Constants.apiKey)
                .observe(this, todayResponseNetworkResource -> {
                    if (todayResponseNetworkResource != null) {


                        switch (todayResponseNetworkResource.status) {

                            case LOADING: {

                                break;
                            }

                            case SUCCESS: {


                                if (todayResponseNetworkResource.data != null) {


                                   todayListAdapter.setSource(source);
                                    todayListAdapter.setOverflowClickListener(overflowClickListener);
                                    todayListAdapter.setArticleItemClickListener(articleItemClickListener);
                                    todayListAdapter.setData(todayResponseNetworkResource.data.getArticles());
                                    // todayListAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.ALLOW);
                                    binding.sourceArticles.setAdapter(todayListAdapter);

                                    binding.progressSources.setVisibility(View.GONE);
                                    binding.sourceArticles.setVisibility(View.VISIBLE);
                                }
                                break;
                            }

                            case ERROR: {
                                binding.progressSources.setVisibility(View.GONE);
                                 binding.noConnection.setVisibility(View.VISIBLE);
                                binding.sourceArticles.setVisibility(View.GONE);
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
        bundle.putParcelable("article", article);
        bundle.putParcelable("source", article.getGetSource());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void overflowClicked(Article article) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        BottomSheetFragment fragment = new BottomSheetFragment();

        Source source = article.getGetSource();

        Bundle bundle = new Bundle();
        bundle.putParcelable("bottomSheet", article);
        Log.d(TAG, "overflowClicked: " + source.getName());

        bundle.putParcelable("source", source);

        fragment.setArguments(bundle);
        fragment.show(fragmentManager, fragment.getTag());

    }

}