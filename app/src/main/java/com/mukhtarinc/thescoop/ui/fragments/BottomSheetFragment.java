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
import androidx.lifecycle.ViewModelProviders;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.BottomSheetBinding;
import com.mukhtarinc.thescoop.databinding.FragmentShelfBinding;
import com.mukhtarinc.thescoop.di.DaggerBottomSheetDialogFragment;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.activities.SourceArticleActivity;
import com.mukhtarinc.thescoop.ui.fragments.shelf.ShelfViewModel;
import com.mukhtarinc.thescoop.utils.ShelfListAdapter;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by Raiyan Mukhtar on 6/1/2020.
 */
public class BottomSheetFragment extends DaggerBottomSheetDialogFragment {

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


    public BottomSheetFragment(){


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){

           article = getArguments().getParcelable("bottomSheet");

           source = getArguments().getParcelable("source");

           myShelf = getArguments().getString("shelf");
        }



        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ShelfViewModel.class);

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



        return bottomSheetBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBinding.setSource("Go to "+source.getName());

        bottomSheetBinding.shareSheet.setOnClickListener(view13 -> {
            Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        bottomSheetBinding.shelfSheet.setOnClickListener(view1 -> {

            if(bottomSheetBinding.shelfButton.getText().toString().equalsIgnoreCase(getString(R.string.remove))){

                viewModel.deleteArticle(article);

                getArticles();

                Toast.makeText(getActivity(),"Removed from Shelf",Toast.LENGTH_SHORT).show();


            }else {


                article.setSourceName(source.getName());
                viewModel.insert(article);
                Toast.makeText(getActivity(), "Added to Shelf", Toast.LENGTH_SHORT).show();
                getArticles();
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




    public void getArticles(){

        Log.d(TAG, "getArticles");
        viewModel.getArticles.observe(this, articles -> {


            shelfListAdapter.setData(articles);
            View rootView  = Objects.requireNonNull(getActivity()).getWindow().getDecorView().findViewById(R.id.parent_container);
            LayoutInflater inflater = getLayoutInflater();


            FragmentShelfBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shelf, (ViewGroup) rootView.getParent(),false);

            if (binding.shelfArticles!=null) {
                binding.shelfArticles.setAdapter(shelfListAdapter);
            }else {
                Log.d(TAG, "Binding is null");
            }

        });


    }
}
