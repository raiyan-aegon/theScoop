package com.mukhtarinc.thescoop.utils;

import android.view.View;

import com.mukhtarinc.thescoop.databinding.SourceItemBinding;
import com.mukhtarinc.thescoop.model.Source;

/**
 * Created by Raiyan Mukhtar on 6/10/2020.
 */
public interface AddClickListener {

    void AddClicked(SourceItemBinding binding,int position, Source source);
}
