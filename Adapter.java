package com.example.kundan6singh.testproject.Filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kundan6singh.testproject.R;

import java.util.List;

/**
 * Created by Kundan6.Singh on 14-12-2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    List<Product> productList;
    Context context;

    public Adapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        MyViewHolder myViewHolderObj = new MyViewHolder(layoutView);

        return myViewHolderObj;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Product product = productList.get(position);
        final int pos = position;
        holder.tvName.setText(product.getTnaValue());
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }
}
