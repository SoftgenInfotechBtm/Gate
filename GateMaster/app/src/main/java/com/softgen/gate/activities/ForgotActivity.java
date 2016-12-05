package com.softgen.gate.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softgen.gate.gatedb.R;


public class ForgotActivity extends AppCompatActivity implements View.OnClickListener{
    public Button confirm, getotp;
    public TextInputLayout inputLayoutEmail_mob;
    public EditText inputEmail_mob;
    String emaill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpsw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Forgot Password");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputLayoutEmail_mob = (TextInputLayout) findViewById(R.id.input_layout_email_mob);
        inputEmail_mob = (EditText) findViewById(R.id.input_email_mob);

        getotp = (Button) findViewById(R.id.get_otp);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emaill = inputEmail_mob.getText().toString();
                if (!emaill.isEmpty() && emaill != null) {
                    emaill = inputEmail_mob.getText().toString();
                } else {
                    Snackbar.make(view, "Please Enter Valid Email/MobileNo", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        confirm = (Button) findViewById(R.id.cnfm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(i2);
            }
        });
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ForgotActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {

    }
}