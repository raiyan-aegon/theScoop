package com.mukhtarinc.thescoop.di;

import android.view.View;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */

@Retention(RetentionPolicy.RUNTIME)
@MapKey
@Documented
@Target(ElementType.METHOD)
public @interface ViewModelKey {

    Class<? extends ViewModel> value();
}
