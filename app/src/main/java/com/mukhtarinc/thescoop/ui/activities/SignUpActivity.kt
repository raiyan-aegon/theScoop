package com.mukhtarinc.thescoop.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth;
    lateinit var binding : ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)


        binding.toolbar.setNavigationOnClickListener{

            onBackPressed()

        }


        auth = FirebaseAuth.getInstance();



        binding.signUpBT.setOnClickListener {

            auth.createUserWithEmailAndPassword(binding.textInputEditTextEmail.toString(),binding.textInputEditTextPass.toString()).addOnCompleteListener {

                if(it.isSuccessful){

                   // val user = auth.currentUser

                    Toast.makeText(this,"Successfully logged In",Toast.LENGTH_LONG).show();
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()


                } else {

                    Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }

        }


    }
}