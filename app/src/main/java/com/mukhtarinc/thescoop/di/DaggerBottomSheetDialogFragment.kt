package com.mukhtarinc.thescoop.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Created by Raiyan Mukhtar on 7/1/2020.
 */


//Create this to inject into BottomSheetDialog

open class DaggerBottomSheetDialogFragment : BottomSheetDialogFragment(), HasSupportFragmentInjector{

    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

}