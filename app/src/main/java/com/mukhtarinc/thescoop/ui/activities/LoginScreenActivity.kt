package com.mukhtarinc.thescoop.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.data.network.NetworkResource
import com.mukhtarinc.thescoop.data.network.NetworkResource.NetworkResonseStatus
import com.mukhtarinc.thescoop.data.network.sources.SourceResponse
import com.mukhtarinc.thescoop.databinding.ActivityLoginScreenBinding
import com.mukhtarinc.thescoop.model.Source
import com.mukhtarinc.thescoop.ui.fragments.following.FollowingViewModel
import com.mukhtarinc.thescoop.utils.Constants
import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject


private const val RC_SIGN_IN = 1001
private const val TAG = "LoginScreenActivity"
class LoginScreenActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding

    private lateinit var auth: FirebaseAuth

    lateinit var googleSignInClient: GoogleSignInClient

    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var viewModel: FollowingViewModel? = null

    @Inject lateinit var viewModelProviderFactory: ViewModelProviderFactory

    var sourcesList: List<Source>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(FollowingViewModel::class.java)



        initSources();

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        preferences = getSharedPreferences("SKIP", Context.MODE_PRIVATE)

        editor = preferences.edit();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen)




        if(intent.hasExtra("inAlready")){

            binding.SkipBT.visibility = View.GONE
        }





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


        binding.SkipBT.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.SkipBT.setOnClickListener{

            editor.putBoolean("skipClick",true)
            editor.apply()


            val intent = Intent(this, MoreSourcesActivity::class.java)

            intent.putExtra("isSkip",true)
            intent.putParcelableArrayListExtra("sources", list2ArrayList(sourcesList))

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

                        val intent = Intent(this, MoreSourcesActivity::class.java)

                        intent.putExtra("isSkip",true)
                        intent.putParcelableArrayListExtra("sources", list2ArrayList(sourcesList))

                        startActivity(intent)

                        finish();

                    }else{

                        Log.d(TAG, "firebaseAuthWithGoogle: Failure")

                    }


                }

    }

    private fun initSources() {
        viewModel!!.getSources(Constants.apiKey)
                .observe(this, Observer { sourceResponseNetworkResource: NetworkResource<SourceResponse?>? ->
                    if (sourceResponseNetworkResource != null) {
                        when (sourceResponseNetworkResource.status) {
                            NetworkResonseStatus.LOADING -> {
                            }
                            NetworkResonseStatus.SUCCESS -> {
                                if (sourceResponseNetworkResource.data != null) {

                                    sourcesList = sourceResponseNetworkResource.data.sources

                                }
                            }
                            NetworkResonseStatus.ERROR -> {

                                Toast.makeText(this, sourceResponseNetworkResource.message + "", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }




private fun list2ArrayList(sourceList: List<Source>?): ArrayList<Source>? {
    return ArrayList(sourceList!!)
}

}



