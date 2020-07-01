package com.mukhtarinc.thescoop.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.databinding.TodayArticleItemBinding
import com.mukhtarinc.thescoop.model.Article
import com.mukhtarinc.thescoop.utils.ScoopDateUtils.Companion.newsTimeDifference


/**
 * Created by Raiyan Mukhtar on 6/30/2020.
 */
public class ShelfListAdapter (requestManager: RequestManager) : RecyclerView.Adapter<ShelfListAdapter.ShelfViewHolder>() {

    lateinit var  binding: TodayArticleItemBinding

    lateinit var todayResponses: List<Article>

    var requestManager: RequestManager = requestManager



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelfViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.today_article_item,parent,false)


        return ShelfViewHolder(binding.root)


    }



    override fun getItemCount(): Int {
       return todayResponses.size
    }

    override fun onBindViewHolder(holder: ShelfViewHolder, position: Int) {
        val article = todayResponses[position]
        binding.article = article
        requestManager.load(article.getUrlToImage()).placeholder(R.drawable.image_placeholder).centerCrop().into(binding.imageArticle)
        binding.time.text = newsTimeDifference(article.publishedAt)
    }



    fun setData(data: List<Article>){

        this.todayResponses = data

        notifyDataSetChanged()


    }


    class ShelfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){




    }

}