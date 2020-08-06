package com.mukhtarinc.thescoop.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.BottomSheetBinding;
import com.mukhtarinc.thescoop.databinding.FragmentShelfBinding;
import com.mukhtarinc.thescoop.di.DaggerBottomSheetDialogFragment;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.activities.LoginScreenActivity;
import com.mukhtarinc.thescoop.ui.activities.SourceArticleActivity;
import com.mukhtarinc.thescoop.ui.activities.TheScoopDetailsActivity;
import com.mukhtarinc.thescoop.ui.fragments.shelf.ShelfViewModel;
import com.mukhtarinc.thescoop.utils.ArticleItemClickListener;
import com.mukhtarinc.thescoop.utils.OverflowClickListener;
import com.mukhtarinc.thescoop.utils.ShelfListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by Raiyan Mukhtar on 6/1/2020.
 */
public class BottomSheetFragment extends DaggerBottomSheetDialogFragment implements OverflowClickListener, ArticleItemClickListener{

    private static final String TAG = "BottomSheetFragment";
    BottomSheetBinding bottomSheetBinding;

     Article article;
     Source source;

    @Inject
    ShelfListAdapter shelfListAdapter;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    ShelfViewModel viewModel;

    String myShelf;

    ArticleItemClickListener articleItemClickListener;

    OverflowClickListener overflowClickListener;

    FirebaseAuth auth;





    public BottomSheetFragment(){


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleItemClickListener=this;
        overflowClickListener=this;
        if(getArguments()!=null){

           article = getArguments().getParcelable("bottomSheet");

           source = getArguments().getParcelable("source");

           myShelf = getArguments().getString("shelf");
        }



        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ShelfViewModel.class);

        auth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet,container,false);

        if(myShelf!=null){
            if(myShelf.equalsIgnoreCase("shelf")){


                bottomSheetBinding.shelfButton.setText(R.string.remove);

            }
        }


        overflowClickListener = this;
        articleItemClickListener = this;

        return bottomSheetBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBinding.setSource("Go to "+source.getName());

        bottomSheetBinding.shareSheet.setOnClickListener(view13 -> {

            if(auth.getCurrentUser()!=null){

                Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();

                Intent sendIntent  =  new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");

                sendIntent.putExtra(Intent.EXTRA_TEXT,article.getUrl());

                startActivity(Intent.createChooser(sendIntent,"Share"));



            }else {

                Snackbar.make(getActivity().findViewById(android.R.id.content),"No offline access",Snackbar.LENGTH_LONG)
                        .setAction("Sign In" , view2 -> {

                            Intent intent = new Intent(view2.getContext(), LoginScreenActivity.class);
                            view2.getContext().startActivity(intent);

                        }).setActionTextColor(getActivity().getResources().getColor(R.color.colorAccent))
                        .show();



            }

            dismiss();
        });

        bottomSheetBinding.shelfSheet.setOnClickListener(view1 -> {

            if(bottomSheetBinding.shelfButton.getText().toString().equalsIgnoreCase(getString(R.string.remove))){

                viewModel.deleteArticle(article);

                View rootView  = getActivity().getWindow().getDecorView().findViewById(R.id.shelf_parent);

                FragmentShelfBinding binding = DataBindingUtil.getBinding(rootView);

                getArticles(binding);



                Toast.makeText(getActivity(),"Removed from Shelf",Toast.LENGTH_SHORT).show();


            }else {

                if(auth.getCurrentUser()!=null){

                    article.setSourceName(source.getName());
                    viewModel.insert(article);
                    Toast.makeText(getActivity(), "Added to Shelf", Toast.LENGTH_SHORT).show();
                }else {

                 //   View rootView  = getActivity().getWindow().getDecorView().findViewById(R.id.parent_container);

//                    if(rootView==null){
//
//                        Log.d(TAG, "onViewCreated: Root NULL");
//                    }

                    Snackbar.make(getActivity().findViewById(android.R.id.content),"No offline access",Snackbar.LENGTH_LONG)
                            .setAction("Sign In" , view2 -> {

                                Intent intent = new Intent(view2.getContext(), LoginScreenActivity.class);
                                view2.getContext().startActivity(intent);

                            }).setActionTextColor(getActivity().getResources().getColor(R.color.colorAccent))
                            .show();
                }


            }
            dismiss();
        });

        bottomSheetBinding.sourceSheet.setOnClickListener(view12 -> {

            if(source.getSource_id()!=null){
                Intent intent = new Intent(getContext(), SourceArticleActivity.class);
                intent.putExtra("source",source);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(), "Sorry no articles", Toast.LENGTH_SHORT).show();
            }


            dismiss();
        });
    }

    public void getArticles(FragmentShelfBinding binding){

        viewModel.getArticles.observe(getActivity(), articles -> {


            shelfListAdapter.setOverflowListener(overflowClickListener);
            shelfListAdapter.setArticleClickListener(articleItemClickListener);
            shelfListAdapter.setData(articles);


            if(shelfListAdapter.getItemCount()==0){

                Log.d(TAG, "No articles");
                binding.shelfArticles.setVisibility(View.GONE);
            }else {
                Log.d(TAG, "Articles");
                binding.offlineText.setVisibility(View.GONE);


            }
            binding.shelfArticles.setAdapter(shelfListAdapter);

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
        bundle.putString("shelf","shelf");

        fragment.setArguments(bundle);
        fragment.show(fragmentManager,fragment.getTag());
    }
}
