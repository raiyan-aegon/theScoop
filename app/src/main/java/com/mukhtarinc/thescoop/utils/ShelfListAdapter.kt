package com.mukhtarinc.thescoop.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.databinding.ShelfArticleItemBinding
import com.mukhtarinc.thescoop.model.Article
import com.mukhtarinc.thescoop.utils.ScoopDateUtils.Companion.newsTimeDifference


/**
 * Created by Raiyan Mukhtar on 6/30/2020.
 */


private const val TAG = "ShelfListAdapter"
public class ShelfListAdapter (requestManager: RequestManager) : RecyclerView.Adapter<ShelfListAdapter.ShelfViewHolder>() {

    lateinit var  binding: ShelfArticleItemBinding

    lateinit var todayResponses: List<Article>
    private lateinit var articleItemClickListener: ArticleItemClickListener

    private lateinit var overflowClickListener: OverflowClickListener

    var requestManager: RequestManager = requestManager





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelfViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.shelf_article_item,parent,false)


        return ShelfViewHolder(binding.root)


    }




    fun setArticleClickListener(articleItemClickListener: ArticleItemClickListener){

        this.articleItemClickListener = articleItemClickListener
    }


    fun setOverflowListener(overflowClickListener: OverflowClickListener){

        this.overflowClickListener = overflowClickListener
    }

    override fun getItemCount(): Int {
       return todayResponses.size
    }

    override fun onBindViewHolder(holder: ShelfViewHolder, position: Int) {
        val article = todayResponses[position]
        binding.article = article
        Log.d(TAG, "onBindViewHolder: Source "+article.sourceName)
        requestManager.load(article.urlToImage).placeholder(R.drawable.image_placeholder).centerCrop().into(binding.imageArticle)


        if (article.publishedAt[article.publishedAt.length - 1] == 'Z' && article.publishedAt.length <= 20) {
            binding.time.text = newsTimeDifference(article.publishedAt)
        } else {
            binding.time.setText(R.string.some)
        }

        binding.root.setOnClickListener {

            articleItemClickListener.articleItemClicked(article)
        }

        binding.overflowMenu.setOnClickListener {

            overflowClickListener.overflowClicked(article)

        }


    }



    fun setData(data: List<Article>){

        this.todayResponses = data

        notifyDataSetChanged()


    }



    class ShelfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {




    }

}