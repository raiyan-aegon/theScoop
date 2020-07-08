package com.mukhtarinc.thescoop.utils;

import com.mukhtarinc.thescoop.databinding.SourceItemBinding;
import com.mukhtarinc.thescoop.model.Source;

/**
 * Created by Raiyan Mukhtar on 6/10/2020.
 */
public interface CheckClickListener {

    void CheckClicked(SourceItemBinding binding, int position, Source source);
}
