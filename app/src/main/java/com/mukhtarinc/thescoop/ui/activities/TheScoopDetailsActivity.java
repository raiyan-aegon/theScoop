package com.mukhtarinc.thescoop.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.RequestManager;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.ActivityTheScoopDetailsBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment;
import com.mukhtarinc.thescoop.utils.TheScoopDateUtils;

import javax.crypto.spec.PSource;
import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class TheScoopDetailsActivity extends DaggerAppCompatActivity {
    ActivityTheScoopDetailsBinding binding;

    private static final String TAG = "TheScoopDetailsActivity";
    Article article = null;
    String source_name = null;
    Source source = null;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_the_scoop_details);

        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());


        if (getIntent().getExtras() != null) {

            Bundle bundle = getIntent().getExtras();
            article = bundle.getParcelable("article");
            source = bundle.getParcelable("source");

            if (article != null && source!=null) {


                requestManager.load(article.getUrlToImage()).placeholder(R.drawable.image_placeholder).into(binding.articleImage);
                binding.setArticle(article);
                binding.datePublished.setText(TheScoopDateUtils.newsTimeDifference(article.getPublishedAt()));


                binding.sourcesName.setText(source.getName());


                binding.readMore.setOnClickListener(view -> {

                    Intent i = new Intent(this, ArticleViewActivity.class);
                    Bundle web_bundle = new Bundle();
                    web_bundle.putParcelable("source_web",source);
                    web_bundle.putString("url",article.getUrl());

                    i.putExtra("web_bundle",web_bundle);

                    startActivity(i);

                });

            }


        binding.detailsOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fragmentManager =getSupportFragmentManager();

                BottomSheetFragment fragment = new BottomSheetFragment();


                Bundle bundle = new Bundle();
                bundle.putParcelable("bottomSheet",article);
                bundle.putParcelable("source",source);
                fragment.setArguments(bundle);
                fragment.show(fragmentManager,fragment.getTag());
            }
        });

    }

}
}