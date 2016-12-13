package com.softgen.gate.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.satsuware.usefulviews.LabelledSpinner;
import com.softgen.gate.database.DBHelper;
import com.softgen.gate.gatedb.R;
import com.softgen.gate.model.ProfileMaster;
import com.softgen.gate.utility.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity implements LabelledSpinner.OnItemChosenListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private Button button;
    private String TAG = "", MODULE = "Adding Profile";
    private Activity mActivity;
    private EditText inputName, inputEmail, inputmobileno, inputPassword, inputcity, inputstate, inputarea, inputoffered, inputrequired, inputconfirm, inputStart, inputEnd, inputCost;
    private Utils utils;
    private DBHelper db;
    private LabelledSpinner spinner1, spinner2, spinDuartion;
    private String mServicesOffered;
    private String mServicesReceived;
    private String mServicesCharges;
    private String jsonDate;
    private String validFrom;
    private String selectedDay;

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
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity = ProfileActivity.this;
        utils = new Utils(mActivity);
        //spinner for services offered
        spinner1 = (LabelledSpinner) findViewById(R.id.services_offered);
        spinner1.setItemsArray(R.array.services_offered);
        spinner1.setOnItemChosenListener(this);
        //spinner for services required
        spinner2 = (LabelledSpinner) findViewById(R.id.services_required);
        spinner2.setItemsArray(R.array.services_required);
        spinner2.setOnItemChosenListener(this);

        spinDuartion = (LabelledSpinner) findViewById(R.id.services_charges);
        spinDuartion.setItemsArray(R.array.cost_details);
        spinDuartion.setOnItemChosenListener(this);

        db = new DBHelper(ProfileActivity.this);

        //layouts for edit texts
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputEmail.setEnabled(false);
        inputEmail.setText(getIntent().getStringExtra("email"));
        inputmobileno = (EditText) findViewById(R.id.input_mob);
        inputmobileno.setEnabled(false);
        inputmobileno.setText(getIntent().getStringExtra("mobile"));

        //ids for edit texts
        inputName = (EditText) findViewById(R.id.input_name);
        inputcity = (EditText) findViewById(R.id.input_city);
        inputstate = (EditText) findViewById(R.id.input_state);
        inputarea = (EditText) findViewById(R.id.input_area);
        inputPassword = (EditText) findViewById(R.id.password);
        inputconfirm = (EditText) findViewById(R.id.confirm_password);
        inputStart = (EditText) findViewById(R.id.start_time);
        inputEnd = (EditText) findViewById(R.id.end_time);
        inputCost = (EditText) findViewById(R.id.cost);
        inputStart.setOnClickListener(this);
        inputEnd.setOnClickListener(this);
        inputStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickTime(0);
                }
            }
        });

        inputEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickTime(1);
                }
            }
        });
        button = (Button) findViewById(R.id.btn_sign_up);
        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                if (inputName.getText().toString().isEmpty() && inputEmail.getText().toString().isEmpty()
                        && inputmobileno.getText().toString().isEmpty() && inputcity.getText().toString().isEmpty()
                        && inputstate.getText().toString().isEmpty() && inputarea.getText().toString().isEmpty()
                        && inputPassword.getText().toString().isEmpty() && inputconfirm.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Fields Cannot be empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (inputPassword.getText().toString().length() < 8
                        || (inputconfirm.getText().toString().length() < 8)) {
                    Snackbar.make(view, "Password must be at-least 8 characters", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    if (!inputPassword.getText().toString().equals(inputconfirm.getText().toString())) {
                        Snackbar.make(view, "Passwords Doesn't Match", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else {
                        ProfileMaster master = new ProfileMaster();
                        master.setUsername(inputName.getText().toString());
                        master.setEmail(inputEmail.getText().toString());
                        master.setMobile(inputmobileno.getText().toString());
                        master.setCity(inputcity.getText().toString());
                        master.setState(inputstate.getText().toString());
                        master.setArea(inputarea.getText().toString());
                        master.setOffered(mServicesOffered);
                        master.setRequired(mServicesReceived);
                        master.setPassword(inputconfirm.getText().toString());
                        master.setCost(inputCost.getText().toString());
                        master.setDuration(mServicesCharges);
                        master.setStartTime(inputStart.getText().toString());
                        master.setEndTime(inputEnd.getText().toString());
                        master.setCreatedAt(new Date());
                        master.setUpdatedAt(new Date());
                        Log.e("values", master + "");
                        db.createProfileUser(master);
                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setCancelable(false);
        builder.setMessage("your registration process is not completed,Do u want to Exit ?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
//                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
//                startActivity(i);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
        switch (labelledSpinner.getId()) {
            case R.id.services_offered:
                mServicesOffered = adapterView.getItemAtPosition(position).toString();
                break;
            case R.id.services_required:
                mServicesReceived = adapterView.getItemAtPosition(position).toString();
                break;
            case R.id.services_charges:
                mServicesCharges = adapterView.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

    }

    private void pickTime(final int tv) {
        try {
            TAG = "pickTime";
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
                            String AM_PM = "";
                            Calendar datetime = Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            datetime.set(Calendar.MINUTE, minute);
                            int mAm_PM = datetime.get(Calendar.AM_PM);
                            int mHour = datetime.get(Calendar.HOUR);
                            if ( mAm_PM == Calendar.AM)
                                AM_PM = "AM";
                            else if (mAm_PM == Calendar.PM)
                                AM_PM = "PM";
                            String strHrsToShow = (mHour == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
                            if (strHrsToShow.length() == 1) strHrsToShow = "0" + strHrsToShow;
                            String minute1 = "" + datetime.get(Calendar.MINUTE);
                            if (minute1.length() == 1) minute1 = "0" + minute1;
                            String time = strHrsToShow + ":" + minute1 + " " + AM_PM;
                            setTime(tv, time);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTime(int tv, String time) {
        try {
            if ((tv == 0)) {
                inputStart.setText(time);
                inputEnd.setText("");
            } else if (tv == 1) {
                String t = inputStart.getText().toString();
                if (Utils.isGreaterThan24(t, time)) {
                    Utils.alertBox(mActivity, "Time should be less than 24 hous!");
                } else {
                    inputEnd.setText(time);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        try {
            monthOfYear++;
            jsonDate = "" + dayOfMonth + "/" + monthOfYear + "/" + year;
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, monthOfYear - 1);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal.set(Calendar.YEAR, year);
            String converted = utils.convertTime(jsonDate, "dd/MM/yyyy", "ddth MMM yyyy");
            Log.d("dateFormat", converted);
            String day = "" + dayOfMonth;
            String month = "" + monthOfYear;
            String yearStr = "" + year;
            if (day.length() == 1) day = "0" + day;
            if (month.length() == 1) month = "0" + month;
            validFrom = day + "-" + month + "-" + yearStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_time:
                pickTime(0);
                break;
            case R.id.end_time:
                pickTime(1);
                break;
        }
    }
}



