package com.mukhtarinc.thescoop.ui.activities

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.databinding.ActivityArticleViewBinding
import com.mukhtarinc.thescoop.model.Article
import com.mukhtarinc.thescoop.model.Source
import com.mukhtarinc.thescoop.ui.fragments.BottomSheetFragment
import com.mukhtarinc.thescoop.utils.OverflowClickListener


lateinit var articleViewBinding: ActivityArticleViewBinding
lateinit var source: Source

class ArticleViewActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_view)


        articleViewBinding.toolbar.setNavigationOnClickListener {onBackPressed()}




        val bundle : Bundle? = intent.getBundleExtra("web_bundle")

        if(bundle!!.containsKey("url")){



            val webSettings = articleViewBinding.articleWeb.settings
            webSettings.javaScriptEnabled = true

            val webClientImpl = WebClientImpl(this, articleViewBinding.progressWeb)



            articleViewBinding.articleWeb.webViewClient = webClientImpl

            articleViewBinding.articleWeb.loadUrl(bundle.getString("url")!!)

            source = bundle.getParcelable("source_web")!!
            articleViewBinding.source =  source


        }

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if((keyCode == KeyEvent.KEYCODE_BACK) && articleViewBinding.articleWeb.canGoBack()){


            articleViewBinding.articleWeb.goBack()
            return  true
        }

        return super.onKeyDown(keyCode, event)
    }


}