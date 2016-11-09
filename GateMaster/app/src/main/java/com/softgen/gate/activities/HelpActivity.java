package com.softgen.gate.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softgen.gate.gatedb.R;


public class HelpActivity extends AppCompatActivity {
    TextView name, email, mobile, address, location;
    ImageView profile;
    private Context mcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile = (ImageView) findViewById(R.id.prfl);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        email.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        mobile = (TextView) findViewById(R.id.mobile);
        address = (TextView) findViewById(R.id.address);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(HelpActivity.this, EmailActivity.class);
                startActivity(emailIntent);

            }
        });
        location = (TextView) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HelpActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }
}


