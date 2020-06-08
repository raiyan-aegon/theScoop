package com.mukhtarinc.thescoop.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.MoreSourceBinding;
import com.mukhtarinc.thescoop.databinding.SourceItemBinding;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.utils.SourceListAdapter;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MoreSourcesActivity extends DaggerAppCompatActivity {

    MoreSourceBinding binding;

    ArrayList<Source>sources;

    @Inject
    SourceListAdapter sourceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.more_source);


       // TODO: Setup Snackbars when checked sources


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

        binding.moreSourcesRV.setAdapter(sourceListAdapter);

    }
}