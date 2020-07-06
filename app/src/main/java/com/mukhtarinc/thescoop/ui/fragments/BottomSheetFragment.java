package com.mukhtarinc.thescoop.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.BottomSheetBinding;
import com.mukhtarinc.thescoop.di.DaggerBottomSheetDialogFragment;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.fragments.shelf.ShelfViewModel;
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatDialogFragment;
import dagger.android.support.DaggerDialogFragment;
import dagger.android.support.DaggerFragment;

/**
 * Created by Raiyan Mukhtar on 6/1/2020.
 */
public class BottomSheetFragment extends DaggerBottomSheetDialogFragment {

    private static final String TAG = "BottomSheetFragment";
    BottomSheetBinding bottomSheetBinding;

     Article article;
     Source source;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    ShelfViewModel viewModel;


    public BottomSheetFragment(){


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){

           article = getArguments().getParcelable("bottomSheet");

           source = getArguments().getParcelable("source");
        }



        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ShelfViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet,container,false);


        return bottomSheetBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBinding.setSource("Go to "+source.getName());

        bottomSheetBinding.shareSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();

            }
        });

        bottomSheetBinding.shelfSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                article.setSourceName(source.getName());
                viewModel.insert(article);
                Toast.makeText(getActivity(), "Added to Shelf", Toast.LENGTH_SHORT).show();
                setCancelable(true);
            }
        });

        bottomSheetBinding.sourceSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Go to Source", Toast.LENGTH_SHORT).show();
                setCancelable(true);

            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
