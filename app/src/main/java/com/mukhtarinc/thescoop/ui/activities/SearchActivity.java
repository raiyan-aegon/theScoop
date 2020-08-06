package com.mukhtarinc.thescoop.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.ActivitySearchBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
public class SearchActivity extends DaggerAppCompatActivity implements ArticleItemClickListener,OverflowClickListener {

    SearchViewModel viewModel;

    private static final String TAG = "SearchActivity";

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;


    @Inject
    TodayListAdapter todayListAdapter;



    ArticleItemClickListener articleItemClickListener;
    OverflowClickListener overflowClickListener;

    ActivitySearchBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(SearchViewModel.class);

        binding.backSearch.setOnClickListener(view -> {

            onBackPressed();

        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        articleItemClickListener = this;
        overflowClickListener = this;

        binding.articles.setLayoutManager(layoutManager);
        binding.articles.hasFixedSize();


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        binding.searchArticles.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        binding.searchArticles.setQueryHint("Search Latest News and Articles...");

        binding.searchArticles.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {

                    //load here
                    searchArticles(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //load

                if(newText.length()>0){
                    searchArticles(newText);
                }

                
                return false;
            }
        });

    }



        public void searchArticles(String keyword){

            viewModel.getSearchArticles(keyword, Constants.apiKey)
                    .observe(this, todayResponseNetworkResource -> {
                        if(todayResponseNetworkResource !=null){



                            switch(todayResponseNetworkResource.status)
                            {

                                case LOADING: {

                                    break;
                                }

                                case SUCCESS: {


                                    if(todayResponseNetworkResource.data!=null) {



                                       // todayListAdapter.setSearch(keyword);
                                        todayListAdapter.setOverflowClickListener(overflowClickListener);
                                        todayListAdapter.setArticleItemClickListener(articleItemClickListener);
                                        todayListAdapter.setData(todayResponseNetworkResource.data.getArticles());
                                        binding.articles.setAdapter(todayListAdapter);

                                        //binding.shimmerLayout.setVisibility(View.GONE);
                                        binding.articles.setVisibility(View.VISIBLE);
                                        //binding.staticSalute.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                }

                                case ERROR: {
                                    //binding.staticSalute.setVisibility(View.GONE);
                                    //binding.shimmerLayout.setVisibility(View.GONE);
                                    binding.noConnection.setVisibility(View.VISIBLE);
                                    binding.searchArticles.setVisibility(View.GONE);
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

    }
}