package com.mukhtarinc.thescoop.ui;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.BottomSheetBinding;
import com.mukhtarinc.thescoop.model.Article;

/**
 * Created by Raiyan Mukhtar on 6/1/2020.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment{

    private static final String TAG = "BottomSheetFragment";
    BottomSheetBinding bottomSheetBinding;

    private Article article;

    public BottomSheetFragment(){


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){

           article = getArguments().getParcelable("bottomSheet");

        }
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
        String source = "Go to "+article.getGetSource().getName();

        bottomSheetBinding.setSource(source);

        bottomSheetBinding.shareSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();

            }
        });

        bottomSheetBinding.shelfSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Added to Shelf", Toast.LENGTH_SHORT).show();

            }
        });

        bottomSheetBinding.sourceSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Go to Source", Toast.LENGTH_SHORT).show();

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
