package com.mukhtarinc.thescoop.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.databinding.SplashScreenBinding
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

lateinit var binding : SplashScreenBinding



class ScoopSplashActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    lateinit var preferences: SharedPreferences

    private val compositeDisposable: CompositeDisposable =  CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences("SKIP", Context.MODE_PRIVATE)


        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)


        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)


        auth = FirebaseAuth.getInstance()


        startMainActivity()

    }


    private fun startMainActivity(){

        compositeDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribe {


                    val isSkip = preferences.getBoolean("skipClick",false)

                    if(isSkip){
                        val intent = Intent(this,MainActivity::class.java)

                        startActivity(intent)
                        finish()

                    }else{
                        if(auth.currentUser==null){
                            val intent = Intent(this,LoginScreenActivity::class.java)

                            startActivity(intent)

                            finish()
                        }else{
                            val intent = Intent(this,MainActivity::class.java)

                            startActivity(intent)
                            finish()

                        }
                    }




                }
        )


    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }




}