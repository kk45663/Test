package com.example.kundan6singh.testproject.Filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kundan6singh.testproject.R;

import java.util.ArrayList;

/**
 * Created by Kundan6.Singh on 17-12-2018.
 */

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.MyFilterHolder> {
    ArrayList<FilterListModel> filterItemList;
    Context ctx;
    public FilterListAdapter(Context context, ArrayList<FilterListModel> filterItemList) {
       // this.listener = checkedInterface;
        this.ctx = context;
        this.filterItemList = filterItemList;
    }

    @Override
    public MyFilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_list_item, null);
        MyFilterHolder myViewHolderObj = new MyFilterHolder(layoutView);
        return myViewHolderObj;
    }

    @Override
    public void onBindViewHolder(MyFilterHolder holder, int position) {
        final FilterListModel filterListModel = filterItemList.get(position);
        holder.tvGroupName.setText(filterListModel.getName());
        holder.tick.setTag(position);
        boolean isChecked = filterListModel.isChecked();
        holder.tick.setChecked(filterItemList.get(position).isChecked());
        holder.tick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                filterItemList.get(getPosition).setChecked(buttonView.isChecked());
                ((FilterActivity)ctx).setClearAllSelection();
               // listener.setClearAllSelection();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterItemList.size();
    }

    public class MyFilterHolder extends RecyclerView.ViewHolder {
        public TextView tvGroupName;
        public CheckBox tick;

        public MyFilterHolder(View itemView) {
            super(itemView);
            tvGroupName = (TextView) itemView.findViewById(R.id.adaptertextview);
            tick = (CheckBox) itemView.findViewById(R.id.adaptercheckbox);
        }
    }
}
