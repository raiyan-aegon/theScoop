package com.mukhtarinc.thescoop.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.databinding.SplashScreenBinding
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

lateinit var binding : SplashScreenBinding



class ScoopSplashActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable =  CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)


        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)



        startMainActivity()

    }


    private fun startMainActivity(){

        compositeDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribe {

                    var intent = Intent(this,MainActivity::class.java)

                    startActivity(intent)

                }
        )


    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

}