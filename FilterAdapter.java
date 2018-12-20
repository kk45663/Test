package com.example.kundan6singh.testproject.Filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kundan6singh.testproject.R;

import java.util.ArrayList;

/**
 * Created by Kundan6.Singh on 18-12-2018.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    ArrayList<FilterProduct> alAfterFilterList;
    Context context;

    public FilterAdapter(Context context, ArrayList<FilterProduct> filterList) {
        this.alAfterFilterList = filterList;
        this.context = context;
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        FilterViewHolder myViewHolderObj = new FilterViewHolder(layoutView);
        return myViewHolderObj;
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        final FilterProduct product = alAfterFilterList.get(position);
        holder.tvName.setText(product.getImageNameDataValue());
    }

    @Override
    public int getItemCount() {
        return this.alAfterFilterList.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivImage;

        public FilterViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }
}

