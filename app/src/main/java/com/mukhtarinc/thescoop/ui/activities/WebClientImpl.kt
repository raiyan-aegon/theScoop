package com.mukhtarinc.thescoop.ui.activities

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.progressindicator.ProgressIndicator


/**
 * Created by Raiyan Mukhtar on 6/25/2020.
 */

const  val MAX_PROGRESS : Int = 100
open class WebClientImpl(private var progressBar: ProgressBar) : WebChromeClient(){


    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        progressBar.progress = newProgress

        if(newProgress <MAX_PROGRESS && progressBar.visibility == ProgressBar.GONE ){

            progressBar.visibility = ProgressBar.VISIBLE
        }
        if(newProgress == MAX_PROGRESS)
            progressBar.visibility = ProgressBar.GONE
    }

}