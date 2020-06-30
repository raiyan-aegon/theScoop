package com.mukhtarinc.thescoop.ui.activities

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_view)


        articleViewBinding.toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }




        val bundle : Bundle? = intent.getBundleExtra("web_bundle")

        if(bundle!!.containsKey("url")){


            articleViewBinding.articleWeb.settings.javaScriptEnabled = true
            articleViewBinding.articleWeb.settings.loadWithOverviewMode = true
            articleViewBinding.articleWeb.settings.useWideViewPort = true
            articleViewBinding.articleWeb.settings.domStorageEnabled = true
            articleViewBinding.articleWeb.settings.setSupportZoom(true)
            articleViewBinding.articleWeb.settings.builtInZoomControls = true
            articleViewBinding.articleWeb.settings.displayZoomControls =false

            articleViewBinding.articleWeb.webViewClient = object : WebViewClient (){


                override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                    handler?.proceed()

                }

            }

            articleViewBinding.articleWeb.webChromeClient = WebClientImpl(articleViewBinding.progressWeb)


            articleViewBinding.articleWeb.loadUrl(bundle.getString("url")!!)

            source = bundle.getParcelable("source_web")!!
            articleViewBinding.source =  source


        }

    }


    override fun onDestroy() {
        super.onDestroy()
        articleViewBinding.articleWeb.stopLoading()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if((keyCode == KeyEvent.KEYCODE_BACK) && articleViewBinding.articleWeb.canGoBack()){


            articleViewBinding.articleWeb.goBack()
            return  true
        }

        return super.onKeyDown(keyCode, event)
    }


}