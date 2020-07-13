package com.mukhtarinc.thescoop.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.databinding.ActivityLoginScreenBinding

private const val TAG = "LoginScreenActivity"
class LoginScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginScreenBinding

    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen)




        auth = FirebaseAuth.getInstance()




        binding.signInBT.setOnClickListener{
            auth.signInWithEmailAndPassword(binding.textInputEditTextEmail.text.toString(), binding.textInputEditTextPass.text.toString()).addOnCompleteListener {


                if(it.isSuccessful){

                    Log.d(TAG, "Email "+ binding.textInputEditTextEmail.text.toString())

                    Toast.makeText(this,"Successfully logged In",Toast.LENGTH_LONG).show();
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
                }


            }

        }

        binding.signUpBT.setOnClickListener{




        }





    }



}