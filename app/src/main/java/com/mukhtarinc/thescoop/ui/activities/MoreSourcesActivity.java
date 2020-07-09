package com.mukhtarinc.thescoop.ui.activities;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.MoreSourceBinding;
import com.mukhtarinc.thescoop.databinding.SourceItemBinding;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.utils.AddClickListener;
import com.mukhtarinc.thescoop.utils.CheckClickListener;
import com.mukhtarinc.thescoop.utils.Constants;
import com.mukhtarinc.thescoop.utils.SourceListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MoreSourcesActivity extends DaggerAppCompatActivity implements AddClickListener,CheckClickListener {

    private static final String TAG = "MoreSourcesActivity";
    MoreSourceBinding binding;

    ArrayList<Source>sources;

    @Inject
    SourceListAdapter sourceListAdapter;

    AddClickListener mAddClickListener;

    CheckClickListener mCheckClickListener;
    
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.more_source);

        mAddClickListener = this;
        mCheckClickListener = this;
        preferences = getSharedPreferences(Constants.SHARED_PREFS,MODE_PRIVATE);
        editor = preferences.edit();

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();

        if(intent.hasExtra("sources")){


            sources = intent.getParcelableArrayListExtra("sources");



        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        binding.moreSourcesRV.addItemDecoration(itemDecoration);
        binding.moreSourcesRV.setLayoutManager(linearLayoutManager);
        binding.moreSourcesRV.hasFixedSize();

        sourceListAdapter.setData(sources);
        sourceListAdapter.setOnCheckClickListener(mCheckClickListener);
        sourceListAdapter.setAddClickListener(mAddClickListener);

        binding.moreSourcesRV.setAdapter(sourceListAdapter);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void AddClicked(SourceItemBinding bindings, int position, Source source) {
       // Constants.showSnackBar(this,"You're following "+source.getName(),true);
        Snackbar.make(bindings.getRoot(),"You're following "+source.getName(),Snackbar.LENGTH_LONG)
                .setActionTextColor(getColor(R.color.colorAccent))
                .setAction("Read Now", view ->{
                    Intent intent = new Intent(this,SourceArticleActivity.class);
                    intent.putExtra("source",source);
                    startActivity(intent);

                })
                .show();

        editor.putString("sourceName "+position,source.getSource_id());
        Log.d(TAG, "Source_ID"+source.getSource_id());

        editor.apply();

        bindings.add.setVisibility(View.GONE);
        bindings.check.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void CheckClicked(SourceItemBinding bindings, int position, Source source) {
       // Constants.showSnackBar(bin,"You stopped following " + source.getName(),false);

        Snackbar.make(bindings.getRoot(),"You stopped following " + source.getName(),Snackbar.LENGTH_LONG).show();

        editor.remove("sourceName "+position);
        editor.commit();
        bindings.add.setVisibility(View.VISIBLE);
        bindings.check.setVisibility(View.GONE);
    }
}