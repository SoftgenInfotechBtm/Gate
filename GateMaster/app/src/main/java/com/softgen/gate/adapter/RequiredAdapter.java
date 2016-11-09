package com.softgen.gate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softgen.gate.gatedb.R;
import com.softgen.gate.model.Required;

import java.util.List;


/**
 * Created by 9Jeevan on 18-09-2016.
 */
public class RequiredAdapter extends RecyclerView.Adapter<RequiredAdapter.MyViewHolder> {
    private List<Required> requiredList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, amount, rating;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nm);
            amount = (TextView) view.findViewById(R.id.am);
            rating = (TextView) view.findViewById(R.id.rt);
        }
    }

    public RequiredAdapter(List<Required> offeredList) {
        this.requiredList = offeredList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offered_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Required required = requiredList.get(position);
        holder.name.setText(required.getName());
        holder.amount.setText(required.getAmount());
        holder.rating.setText(required.getRating());
    }

    @Override
    public int getItemCount() {
        return requiredList.size();
    }
}

