package com.mukhtarinc.thescoop.ui.activities

import android.R.attr
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.databinding.ActivityLoginScreenBinding


private const val RC_SIGN_IN = 1001
private const val TAG = "LoginScreenActivity"
class LoginScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding

    private lateinit var auth: FirebaseAuth

    lateinit var googleSignInClient: GoogleSignInClient

    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        preferences = getSharedPreferences("SKIP", Context.MODE_PRIVATE)

        editor = preferences.edit();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // for the requestIdToken, this is in the values.xml file that
                // is generated from your google-services.json
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Set the dimensions of the sign-in button.

        binding.signInButton.setSize(SignInButton.SIZE_WIDE)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()



        binding.signInButton.setOnClickListener{

            signIn()
        }

        binding.materialTextViewSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.SkipBT.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.SkipBT.setOnClickListener{

            editor.putBoolean("skipClick",true)
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


        binding.signInBT.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.textInputEditTextEmail.text.toString(), binding.textInputEditTextPass.text.toString()).addOnCompleteListener {


                if (it.isSuccessful) {

                    Log.d(TAG, "Email " + binding.textInputEditTextEmail.text.toString())

                    Toast.makeText(this, "Successfully logged In", Toast.LENGTH_LONG).show();
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                }


            }

        }


        binding.materialTextViewSignUp.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }


    }


    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }



    private fun firebaseAuthWithGoogle(token:String?){

        val credentials : AuthCredential = GoogleAuthProvider.getCredential(token,null)
        auth.signInWithCredential(credentials)
                .addOnCompleteListener{


                    if(it.isSuccessful){


                        Log.d(TAG, "firebaseAuthWithGoogle: Success")

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }else{

                        Log.d(TAG, "firebaseAuthWithGoogle: Failure")

                    }


                }

    }



}



