package com.softgen.gate.activities;

import android.content.Context;
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

import com.softgen.gate.adapter.RequiredAdapter;
import com.softgen.gate.gatedb.R;
import com.softgen.gate.model.Required;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 9Jeevan on 18-09-2016.
 */
public class RequiredActivity extends AppCompatActivity {
    private List<Required> requiredList = new ArrayList<>();
    private RecyclerView rV;
    private RequiredAdapter rAdapter;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rV = (RecyclerView) findViewById(R.id.recycler_view1);

        rAdapter = new RequiredAdapter(requiredList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rV.setLayoutManager(mLayoutManager);
        rV.setItemAnimator(new DefaultItemAnimator());
        rV.setAdapter(rAdapter);

        rV.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rV, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                Required required = requiredList.get(position);
                Toast.makeText(getApplicationContext(), required.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareRequiredData();
    }

    private void prepareRequiredData() {
        Required required;
        required = new Required("Electrician", "450/-", "3.5", R.drawable.carpenter1);
        requiredList.add(required);

        required = new Required("Carpenter", "450/-", "3.0", R.drawable.carpenter1);
        requiredList.add(required);

        required = new Required("Painter", "450/-", "3.5", R.drawable.carpenter1);
        requiredList.add(required);

        required = new Required("Plumber", "450/-", "3.0", R.drawable.carpenter1);
        requiredList.add(required);

        required = new Required("Interior", "450/-", "4.5", R.drawable.carpenter1);
        requiredList.add(required);

        required = new Required("Mechanic", "450/-", "3.8", R.drawable.carpenter1);
        requiredList.add(required);

        required = new Required("Painter", "450/-", "3.5", R.drawable.carpenter1);
        requiredList.add(required);

        required = new Required("Carpenter", "450/-", "3.0", R.drawable.carpenter1);
        requiredList.add(required);

        rAdapter.notifyDataSetChanged();

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private OfferedActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView rV, final OfferedActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = rV.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, rV.getChildPosition(child));
                    }
                }
            });
        }

        public RecyclerTouchListener(Context applicationContext, RecyclerView rV, ClickListener clickListener) {
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

