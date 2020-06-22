package com.mukhtarinc.thescoop.utils;

import android.view.View;

import com.mukhtarinc.thescoop.databinding.SourceItemBinding;
import com.mukhtarinc.thescoop.model.Source;

/**
 * Created by Raiyan Mukhtar on 6/10/2020.
 */
public interface CheckboxClickListener {

    void checkboxClicked(SourceItemBinding binding,int positon, Source source);
}
