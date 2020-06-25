package com.mukhtarinc.thescoop.ui.activities

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.progressindicator.ProgressIndicator


/**
 * Created by Raiyan Mukhtar on 6/25/2020.
 */


open class WebClientImpl(private var activity: Activity, private var progress:ProgressBar) : WebViewClient() {




    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return false
    }


    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {

        super.onPageFinished(view, url)
        progress.visibility = View.GONE

    }



}