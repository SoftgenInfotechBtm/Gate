package com.softgen.gate.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.softgen.gate.adapter.OfferedAdapter;
import com.softgen.gate.gatedb.R;
import com.softgen.gate.model.Offered;

import java.util.ArrayList;
import java.util.List;

public class OfferedActivity extends AppCompatActivity {
    private List<Offered> offeredList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OfferedAdapter oAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offered);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        oAdapter = new OfferedAdapter(offeredList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(oAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Offered offered = offeredList.get(position);
                Intent i = new Intent(OfferedActivity.this, PersonelActivity.class);
                i.putExtra("click_position", position);
                startActivity(i);
                Toast.makeText(getApplicationContext(), offered.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareOfferedData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return false;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareOfferedData() {
        Offered offered;
        offered = new Offered("Electrician", "450/-", "3.5", R.drawable.carpenter1);
        offeredList.add(offered);

        offered = new Offered("Carpenter", "450/-", "3.0", R.drawable.carpenter1);
        offeredList.add(offered);

        offered = new Offered("Painter", "450/-", "3.5", R.drawable.carpenter1);
        offeredList.add(offered);

        offered = new Offered("Plumber", "450/-", "3.0", R.drawable.carpenter1);
        offeredList.add(offered);

        offered = new Offered("Interior", "450/-", "4.5", R.drawable.carpenter1);
        offeredList.add(offered);

        offered = new Offered("Mechanic", "450/-", "3.8", R.drawable.carpenter1);
        offeredList.add(offered);

        offered = new Offered("Painter", "450/-", "3.5", R.drawable.carpenter1);
        offeredList.add(offered);

        offered = new Offered("Carpenter", "450/-", "3.0", R.drawable.carpenter1);
        offeredList.add(offered);

        oAdapter.notifyDataSetChanged();

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private OfferedActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final OfferedActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
