package com.softgen.gate.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.softgen.gate.gatedb.R;
import com.softgen.gate.model.Offered;

import java.util.List;


/**
 * Created by 9Jeevan on 16-09-2016.
 */
public class OfferedAdapter extends RecyclerView.Adapter<OfferedAdapter.MyViewHolder> {
    private List<Offered> offeredList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, amount, rating;
        public ImageButton b1, b2;
        public RatingBar rBar1;

        public MyViewHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nm);
            amount = (TextView) view.findViewById(R.id.am);
            rating = (TextView) view.findViewById(R.id.rt);
//            b1 = (ImageButton) view.findViewById(R.id.img1);
//            b2 = (ImageButton) view.findViewById(R.id.img2);
            rBar1 = (RatingBar)view.findViewById(R.id.star);
        }
    }

    private void startActivity(Intent callIntent) {
    }

    private void startActivity1(Intent msgIntent) {
    }

    public OfferedAdapter(List<Offered> offeredList) {
        this.offeredList = offeredList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offered_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Offered offered = offeredList.get(position);
        holder.name.setText(offered.getName());
        holder.amount.setText(offered.getAmount());
        holder.rating.setText(offered.getRating());

//        holder.b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("9738514840"));
//                startActivity(callIntent);
//
//            }
//        });
//
//        holder.b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent msgIntent = new Intent(Intent.ACTION_VIEW);
//                msgIntent.putExtra("sms_body", "default content");
//                msgIntent.setType("vnd.android-dir/mms-sms");
//                msgIntent.putExtra("address", new String("9738514840"));
//                msgIntent.putExtra("sms_body", "Test ");
//                startActivity1(msgIntent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return offeredList.size();
    }
}
