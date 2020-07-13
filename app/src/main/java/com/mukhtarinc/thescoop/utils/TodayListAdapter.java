package com.mukhtarinc.thescoop.utils;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.TodayArticleItemBinding;
import com.mukhtarinc.thescoop.databinding.TopHeadlineBinding;
import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.model.Category;
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
    String search;
    Category category;
    Source source;

    boolean isUser =false;

    FirebaseAuth auth;

     OverflowClickListener overflowClickListener;
     ArticleItemClickListener articleItemClickListener;

   public  TodayListAdapter(RequestManager requestManager){

        this.requestManager = requestManager;

    }

    public void setUser(boolean user,FirebaseAuth auth) {
        isUser = user;
        this.auth = auth;
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

    public void setCategory(Category category){

       this.category = category;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setSource(Source source){
       this.source = source;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Article article = todayResponses.get(position);

       if(getItemViewType(position)==0) {

           if(holder instanceof TodayHeadlineViewHolder){


               TopHeadlineBinding topHeadlineBinding = DataBindingUtil.getBinding( ((TodayHeadlineViewHolder) holder).headlineView);



               if (topHeadlineBinding != null) {

                   topHeadlineBinding.setArticle(article);
                   requestManager.load(article.getUrlToImage()).placeholder(R.drawable.image_placeholder).centerCrop().into(topHeadlineBinding.largeImageArticle);

                   if(article.getPublishedAt().charAt(article.getPublishedAt().length()-1)=='Z' && !(article.getPublishedAt().length()>20)){

                       topHeadlineBinding.time.setText(ScoopDateUtils.Companion.newsTimeDifference(article.getPublishedAt()));
                       Log.d(TAG, article.getPublishedAt());

                   }else {
                       topHeadlineBinding.time.setText(R.string.some);
                   }

                   if(category!=null){
                       topHeadlineBinding.todayText.setText("Top "+category.getCat_name().toLowerCase()+" stories");
                   }

//                   if(search.length()>0){
//
//                       topHeadlineBinding.todayText.setText(search);
//                   }

                   if(source!=null){
                       topHeadlineBinding.todayText.setVisibility(View.GONE);
                       topHeadlineBinding.forYouText.setVisibility(View.GONE);
                   }

                   if (for_you == 1) {
                       if(isUser=true){

                           if(auth.getCurrentUser()!=null)

                               topHeadlineBinding.forYouText.setText("Hello "+auth.getCurrentUser().getDisplayName());
                           topHeadlineBinding.todayText.setVisibility(View.GONE);
                       }
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


                   if(article.getPublishedAt().charAt(article.getPublishedAt().length()-1)=='Z' && !(article.getPublishedAt().length()>20)){

                       binding.time.setText(ScoopDateUtils.Companion.newsTimeDifference(article.getPublishedAt()));
                       Log.d(TAG, article.getPublishedAt());

                   }else {
                       binding.time.setText(R.string.some);
                   }
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

            binding.topCard.setOnClickListener(view1 -> {
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
