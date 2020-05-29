package com.mukhtarinc.thescoop.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.TodayArticleItemBinding;
import com.mukhtarinc.thescoop.databinding.TopHeadlineBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.network.today.TodayResponse;
import com.mukhtarinc.thescoop.ui.today.TodayViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Raiyan Mukhtar on 5/28/2020.
 */
public class TodayListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Article> todayResponses;

    RequestManager requestManager;

    private static int PUBLIC_KEY=0;
    private static int PUBLIC_OTHERS=1;

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

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Article article = todayResponses.get(position);

       if(getItemViewType(position)==0) {

           if(holder instanceof TodayHeadlineViewHolder){


               TopHeadlineBinding topHeadlineBinding = DataBindingUtil.getBinding( ((TodayHeadlineViewHolder) holder).headlineView);



               if (topHeadlineBinding != null) {
                   topHeadlineBinding.setArticle(article);
                   requestManager.load(article.getUrlToImage()).placeholder(R.drawable.image_placeholder).centerCrop().into(topHeadlineBinding.largeImageArticle);
                   topHeadlineBinding.time.setText(TheScoopDateUtils.newsTimeDifference(article.getPublishedAt()));


               }


           }


       }else {


           if(holder instanceof TodayViewHolder){
               TodayArticleItemBinding binding = DataBindingUtil.getBinding(((TodayViewHolder)holder).itemView);


               if (binding != null) {
                   binding.setArticle(article);
                   requestManager.load(article.getUrlToImage()).placeholder(R.drawable.image_placeholder).centerCrop().into(binding.imageArticle);
                   binding.time.setText(TheScoopDateUtils.newsTimeDifference(article.getPublishedAt()));

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

    public class TodayViewHolder extends RecyclerView.ViewHolder{



        public TodayViewHolder(@NonNull View view) {
            super(view);
        }
    }


    public class TodayHeadlineViewHolder extends RecyclerView.ViewHolder{


       View headlineView;

        public TodayHeadlineViewHolder(@NonNull View view) {
            super(view);
            headlineView =view;
        }
    }





}
