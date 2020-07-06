package com.mukhtarinc.thescoop.utils;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.TodayArticleItemBinding;
import com.mukhtarinc.thescoop.databinding.TopHeadlineBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Source;

import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * Created by Raiyan Mukhtar on 5/28/2020.
 */
public class TodayListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "TodayListAdapter";
    List<Article> todayResponses;

    RequestManager requestManager;
    int for_you;

     OverflowClickListener overflowClickListener;
     ArticleItemClickListener articleItemClickListener;

   public  TodayListAdapter(RequestManager requestManager){

        this.requestManager = requestManager;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        if(viewType==0){

            TopHeadlineBinding topHeadlineBinding = DataBindingUtil.inflate(inflater,R.layout.top_headline,parent,false);
            return new TodayHeadlineViewHolder(topHeadlineBinding.getRoot());

        }

            TodayArticleItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.today_article_item, parent, false);

            return new TodayViewHolder(binding.getRoot());

    }


    public void setOverflowClickListener(OverflowClickListener listener){

       this.overflowClickListener = listener;

    }

    public void setArticleItemClickListener(ArticleItemClickListener articleItemClickListener){

       this.articleItemClickListener =articleItemClickListener;
    }

    public void setForYou(int forYou){

        this.for_you = forYou;


    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Article article = todayResponses.get(position);

       if(getItemViewType(position)==0) {

           if(holder instanceof TodayHeadlineViewHolder){


               TopHeadlineBinding topHeadlineBinding = DataBindingUtil.getBinding( ((TodayHeadlineViewHolder) holder).headlineView);



               if (topHeadlineBinding != null) {
                   topHeadlineBinding.setArticle(article);
                   requestManager.load(article.getUrlToImage()).placeholder(R.drawable.image_placeholder).centerCrop().into(topHeadlineBinding.largeImageArticle);
                   topHeadlineBinding.time.setText(ScoopDateUtils.Companion.newsTimeDifference(article.getPublishedAt()));


                   if (for_you == 1) {
                       Log.d(TAG, "For you "+for_you);
                       if (topHeadlineBinding.todayText != null && topHeadlineBinding.forYouText != null) {
                           topHeadlineBinding.todayText.setVisibility(View.GONE);
                           topHeadlineBinding.forYouText.setVisibility(View.VISIBLE);
                       }else {
                           Log.d(TAG, "onBindViewHolder: NULL TEXT");
                       }
                   }

               }
           }

       }else {


           if(holder instanceof TodayViewHolder){
               TodayArticleItemBinding binding = DataBindingUtil.getBinding(((TodayViewHolder)holder).itemView);


               if (binding != null) {
                   binding.setArticle(article);
                   requestManager.load(article.getUrlToImage()).placeholder(R.drawable.image_placeholder).centerCrop().into(binding.imageArticle);


                   if(article.getPublishedAt()==null){
                       binding.time.setText("");
                       Log.d(TAG, article.getTitle()+" , "+"Time : Null");
                   }
                   binding.time.setText(ScoopDateUtils.Companion.newsTimeDifference(article.getPublishedAt()));
               }

           }
        }

   }


    @Override
    public int getItemViewType(int position) {

       return position;
    }

    @Override
    public int getItemCount() {
        return todayResponses.size();
    }

    public void setData(List<Article> data){

        todayResponses= data;
        notifyDataSetChanged();
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


       TodayArticleItemBinding binding;

        public TodayViewHolder(@NonNull View view) {
            super(view);
            binding = DataBindingUtil.getBinding(view);

            binding.overflowMenu.setOnClickListener(this);
            binding.getRoot().setOnClickListener(view1 -> {
                Article article = todayResponses.get(getAdapterPosition());
                articleItemClickListener.articleItemClicked(article);
            });
        }

        @Override
        public void onClick(View view) {
            Article article = todayResponses.get(getAdapterPosition());

            overflowClickListener.overflowClicked(article);


        }
    }


    public class TodayHeadlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


       TopHeadlineBinding binding;
       View headlineView;

        public TodayHeadlineViewHolder(@NonNull View view) {

            super(view);
            headlineView =view;
            binding = DataBindingUtil.getBinding(view);
            binding.overflowMenu.setOnClickListener(this);

            binding.getRoot().setOnClickListener(view1 -> {
                Article article = todayResponses.get(getAdapterPosition());
                articleItemClickListener.articleItemClicked(article);
            });


        }


        @Override
        public void onClick(View view) {
            Article article = todayResponses.get(getAdapterPosition());
            overflowClickListener.overflowClicked(article);

        }
    }







}
