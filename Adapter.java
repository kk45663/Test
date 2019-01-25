package com.example.kundan6singh.testproject.Filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kundan6singh.testproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kundan6.Singh on 14-12-2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {
    List<Product> productList;
    Context context;
    private List<Product> mFilteredList;
    public Adapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.mFilteredList=productList;
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
      //  holder.tvName.setText(product.getTnaValue());
        holder.tvName.setText(mFilteredList.get(position).getTnaValue());
    }

    @Override
    public int getItemCount() {
        return this.mFilteredList.size();
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

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = productList;
                } else {

                    ArrayList<Product> filteredList = new ArrayList<>();

                    for (Product androidVersion : productList) {

                        if (androidVersion.getTnaValue().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Product>) filterResults.values;
              FilterActivity filterActivityObj=new FilterActivity();
              //filterActivityObj.reFreashAdapter(mFilteredList);
                notifyDataSetChanged();
            }
        };
    }

}
