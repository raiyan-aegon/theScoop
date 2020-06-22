package com.mukhtarinc.thescoop.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.databinding.FragmentFollowingBinding;
import com.mukhtarinc.thescoop.databinding.SourceItemBinding;
import com.mukhtarinc.thescoop.model.Source;

import java.util.List;

/**
 * Created by Raiyan Mukhtar on 6/7/2020.
 */
public class SourceListAdapter extends RecyclerView.Adapter<SourceListAdapter.SourceViewHolder> {

    private static final String TAG = "SourceListAdapter";

    List<Source> sources;
    int num_of_sources;

    RequestManager requestManager;

    SharedPreferences preferences;

    SharedPreferences.Editor editor;
    Application application;

    SourceItemBinding binding;

    CheckboxClickListener checkboxClickListener;


    public SourceListAdapter(Application application, RequestManager requestManager){

        this.requestManager = requestManager;
        this.application = application;

    }


    public void setOnCheckClickListener(CheckboxClickListener checkboxClickListener){


        this.checkboxClickListener = checkboxClickListener;
    }

    @NonNull
    @Override
    public SourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.source_item,parent,false);


        preferences = parent.getContext().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);




        return new SourceViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull SourceViewHolder holder, int position) {

        SourceItemBinding binding = DataBindingUtil.getBinding(holder.itemView);







        Source source = sources.get(position);

        binding.sourcesName.setText(source.getName());


        String sourceName = preferences.getString("sourceName "+position,null);

            if(sourceName!=null && sourceName.equals(source.getSource_id())){

                binding.checkSource.setChecked(true);

            }


        if(position==0){
            //binding.sourceImage.setImageResource(R.drawable.bloomberg);
            requestManager.load(R.drawable.bloomberg).placeholder(R.drawable.image_placeholder).centerCrop().into(binding.sourceImage);




        }else if(position==1){
            //binding.sourceImage.setImageResource(R.drawable.bbc_sport_logo);

            requestManager.load(R.drawable.bbc_sport_logo).placeholder(R.drawable.image_placeholder).centerCrop().into(binding.sourceImage);

        }else if(position==2){
           // binding.sourceImage.setImageResource(R.drawable.espn);
            requestManager.load(R.drawable.espn).placeholder(R.drawable.image_placeholder).centerCrop().into(binding.sourceImage);

        }

        else{
            requestManager.load(R.drawable.ew).placeholder(R.drawable.image_placeholder).centerCrop().into(binding.sourceImage);

            // binding.sourceImage.setImageResource(R.drawable.ew);
        }

    }

    public void setData(List<Source> data){
        sources = data;
        notifyDataSetChanged();
    }

    public  void setCount(int num_of_sources){
        this.num_of_sources =num_of_sources;

    }

    @Override
    public int getItemCount() {
        if(num_of_sources!=0){

            return num_of_sources;
        }
        return sources.size();
    }



    public SourceItemBinding getSourceView(){

        return  binding;
    }

    public class SourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        SourceItemBinding binding;

        public SourceViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.getBinding(itemView);

            binding.checkSource.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            checkboxClickListener.checkboxClicked(binding,getAdapterPosition(),sources.get(getAdapterPosition()));

        }
    }
}
