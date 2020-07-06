package com.mukhtarinc.thescoop.ui.fragments.shelf;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentShelfBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.activities.TheScoopDetailsActivity;
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.ShelfListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ShelfFragment extends DaggerFragment implements OverflowClickListener, ArticleItemClickListener {


    FragmentShelfBinding binding;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    ShelfListAdapter shelfListAdapter;

    ShelfViewModel viewModel;

    ArticleItemClickListener articleItemClickListener;

    OverflowClickListener overflowClickListener;

    private static final String TAG = "ShelfFragment";


    //TODO: DONT FORGET THE SHELF TEXT

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

        articleItemClickListener = this;
        overflowClickListener= this;


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);







    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getArticles();
        binding.offlineText.setVisibility(View.GONE);
        
    }

    public void getArticles(){
        
        viewModel.getArticles.observe(this, articles -> {
            

                shelfListAdapter.setData(articles);
                shelfListAdapter.setArticleClickListener(articleItemClickListener);
                shelfListAdapter.setOverflowListener(overflowClickListener);
                binding.shelfArticles.setAdapter(shelfListAdapter);
                
              

        });

       
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

}
