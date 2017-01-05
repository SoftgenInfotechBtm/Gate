package com.softgen.gate.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.softgen.gate.asynctask.SendEmailTask;
import com.softgen.gate.database.DBHelper;
import com.softgen.gate.gatedb.R;
import com.softgen.gate.model.OTPMaster;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, SendEmailTask.Callback {
    private static final String EmailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

    private String mobileNo;
    private Button otp, register;
    private TextInputLayout inputLayoutEnterOtp;
    private TextView resend;
    private Activity mActivity;
    private EditText inputEnterEmail;
    private EditText inputEnterMobileNo;
    private EditText inputEnterOtp;
    private DBHelper db;
    private CoordinatorLayout coordinatorLayout;
    private CheckBox checkTermsConditions;
    private String emailId;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InitUI();
    }

    private void InitUI() {
        mActivity = RegisterActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Register");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DBHelper(mActivity);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        inputEnterEmail = (EditText) findViewById(R.id.input_email);
        inputEnterMobileNo = (EditText) findViewById(R.id.input_mob1);
        inputLayoutEnterOtp = (TextInputLayout) findViewById(R.id.input_layout_otp);
        inputEnterOtp = (EditText) findViewById(R.id.input_otp);
        otp = (Button) findViewById(R.id.otp);
        checkTermsConditions = (CheckBox) findViewById(R.id.checkTc);
        register = (Button) findViewById(R.id.reg);
        resend = (TextView) findViewById(R.id.resnd);
        InitListeners();
    }
    private void InitListeners() {
        otp.setOnClickListener(this);
        register.setOnClickListener(this);
        resend.setOnClickListener(this);
        setValues();
    }

    private void setValues() {
        emailId = String.valueOf(inputEnterEmail.getText());
        mobileNo = String.valueOf(inputEnterMobileNo.getText());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.otp:
                emailId = String.valueOf(inputEnterEmail.getText());
                mobileNo = String.valueOf(inputEnterMobileNo.getText());
                String vUser = db.getValidUser(emailId, mobileNo);
                if (emailId.isEmpty() || mobileNo.isEmpty()) {
                    Snackbar.make(view, "Please Enter Valid Email/MobileNo", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (!(emailId.matches(EmailPattern))) {
                    Snackbar.make(view, "Please Enter Valid Email", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (vUser.equals(emailId) || vUser.equals(mobileNo)) {
                    Snackbar.make(view, "User already exists", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    new SendEmailTask(this, mActivity, emailId).execute();
//                    new sendmailgmail().execute();
                }
                break;
            case R.id.reg:
                if (inputEnterEmail.getText().toString().isEmpty()
                        && inputEnterMobileNo.getText().toString().isEmpty()
                        && inputEnterOtp.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Fields Cannot be empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (inputEnterOtp.getText().toString().length() < 6
                        || (inputEnterOtp.getText().toString().length() < 6)) {
                    Snackbar.make(view, "Should enter 6 digit otp which received in mail", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                if (!checkTermsConditions.isChecked()) {
                    Snackbar.make(view, "Please Agree Terms & Conditions", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    String sOtp = String.valueOf(inputEnterOtp.getText());
                    if (!sOtp.isEmpty() && sOtp != null) {
                        String sentOTP = db.getMyOTP(emailId, mobileNo);
                        if (sentOTP != null && !sentOTP.equals(sOtp)) {
                            Snackbar.make(view, "Please Enter Received OTP", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        } else {
                            finish();
                            Intent i3 = new Intent(RegisterActivity.this, ProfileActivity.class);
                            i3.putExtra("email", emailId);
                            i3.putExtra("mobile", mobileNo);
                            startActivity(i3);
                        }
                    } else {
                        Snackbar.make(view, "Please Enter OTP", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
                break;
            case R.id.resnd:

                break;
        }
    }

    @Override
    public void onEmailSentResult(SendEmailTask task, String result) {
        if (result != null)
            storeDetails(result);
        else
            Snackbar.make(coordinatorLayout, "Email Not Sent Please Try again Later", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }

    private void storeDetails(String mOtp) {
        register.setVisibility(View.VISIBLE);
        checkTermsConditions.setVisibility(View.VISIBLE);
        inputLayoutEnterOtp.setVisibility(View.VISIBLE);
        resend.setVisibility(View.VISIBLE);
        otp.setVisibility(View.GONE);
        Log.e(">>>>>>>>>> mOtp", mOtp + "coming here");
        OTPMaster master1 = new OTPMaster();
        master1.setOtp(mOtp);
        master1.setEmail(emailId);
        master1.setMobile(mobileNo);
        master1.setCreatedAt(new Date());
        master1.setUpdatedAt(new Date());
        Log.e("values", master1 + "");
        db.createOTPUser(master1);
        Log.e("OTPMaster object", master1.toString());
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Registration Process not completed,Do you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RegisterActivity.this.finish();
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
}
