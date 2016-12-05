package com.softgen.gate.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softgen.gate.gatedb.R;


public class PersonelActivity extends AppCompatActivity {
    public Context mcon;
    ImageView imageView;
    TextView name, email, mobile, about;
    ImageButton call, msg;
    //    public Integer b2;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persondetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcon = PersonelActivity.this;
        imageView = (ImageView) findViewById(R.id.prfl);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        mobile = (TextView) findViewById(R.id.mobile);
        about = (TextView) findViewById(R.id.description);
        call = (ImageButton) findViewById(R.id.call_service);
        msg = (ImageButton) findViewById(R.id.message_service);
        imageView.setImageResource(getIntent().getIntExtra("img_id", 00));
        name.setText("Name:" + getIntent().getStringExtra("Name"));
        email.setText("Email:" + getIntent().getStringExtra("Email"));
        mobile.setText("Mobile:" + getIntent().getStringExtra("Mobile"));
        about.setText("About:" + getIntent().getStringExtra("About"));
        position = getIntent().getIntExtra("click_position", 0);
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+919738514840"));
                    if (ActivityCompat.checkSelfPermission(mcon, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {// TODO: Consider calling
////                            ActivityCompat#requestPermissions
////                         here to request the missing permissions, and then overriding to handle the case where the user grants the Manifest.permission. See the documentation
//                           public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
                        return;
                    }
                    mcon.startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(mcon, "Your call has failed...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msgIntent = new Intent(Intent.ACTION_VIEW);
                msgIntent.putExtra("sms_body", "default content");
                msgIntent.setType("vnd.android-dir/mms-sms");
                msgIntent.putExtra("address", new String("1234567890"));
                msgIntent.putExtra("sms_body", "Test ");
                mcon.startActivity(msgIntent);
            }

        });
        Details(position);
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

    public void Details(int position) {
        final String[] names = {"Test1", "Test2", "Test3", "Test4", "Test5", "Test6", "Test7","Test8"};
        final int[] pid = {R.drawable.carpenter1, R.drawable.carpenter1, R.drawable.carpenter1, R.drawable.carpenter1, R.drawable.carpenter1, R.drawable.carpenter1, R.drawable.carpenter1,R.drawable.carpenter1};
        final String[] mobileNum = {"1234567890", "1234567890", "1234567890", "1234567890", "1234567890", "1234567890", "1234567890","1234567890"};
        final String[] emails = {"Mahesh123@gmail.com", "tes1t@gmail.com", "test2@gmail.com", "test3@gmail.com", "test4@gmail.com", "test5@gmail.com", "test6@gmail.com","test7@gmail.com"};
        final String[] Descp = {"Test1", "Test2", "Test3", "Test4", "Test5", "Test6", "Test7","Test8"};
        name.setText(names[position]);
        email.setText(emails[position]);
        mobile.setText(mobileNum[position]);
        about.setText(Descp[position]);
        imageView.setImageResource(pid[position]);
    }
}
