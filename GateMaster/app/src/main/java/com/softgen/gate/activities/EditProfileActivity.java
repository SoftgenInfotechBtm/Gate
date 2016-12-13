package com.softgen.gate.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.softgen.gate.provider.SharedUtils;
import com.softgen.gate.utility.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity implements LabelledSpinner.OnItemChosenListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {
    @BindView(R.id.btn_sign_up)
    Button btSignUp;
    @BindView(R.id.input_name)
    EditText etName;
    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.input_mob)
    EditText etMobNo;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.input_city)
    EditText etCity;
    @BindView(R.id.input_state)
    EditText etState;
    @BindView(R.id.input_area)
    EditText etArea;
    @BindView(R.id.confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.start_time)
    EditText etStartTime;
    @BindView(R.id.end_time)
    EditText etEndTime;
    @BindView(R.id.cost)
    EditText etCost;
    private DBHelper db;
    private String TAG = "", MODULE = "Edit Profile";
    private LabelledSpinner spinner1, spinner2, spinDuartion;
    private String mServicesOffered;
    private String mServicesReceived;
    private Utils utils;
    private String mServicesCharges;
    private Activity mActivity;
    private String jsonDate;
    private String validFrom;
    private String selectedDay;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity = EditProfileActivity.this;
        utils = new Utils(mActivity);
        ButterKnife.bind(this);
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
        db = new DBHelper(EditProfileActivity.this);
        //ids for edit texts
        etMobNo.setEnabled(false);
        etEmail.setEnabled(false);
        etStartTime.setOnClickListener(this);
        etEndTime.setOnClickListener(this);
        setValues();
        etStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickTime(0);
                }
            }
        });

        etEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickTime(1);
                }
            }
        });
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty() && etEmail.getText().toString().isEmpty()
                        && etMobNo.getText().toString().isEmpty() && etCity.getText().toString().isEmpty()
                        && etState.getText().toString().isEmpty() && etArea.getText().toString().isEmpty()
                        && etPassword.getText().toString().isEmpty() && etConfirmPassword.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Fields Cannot be empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (etPassword.getText().toString().length() < 8
                        || (etConfirmPassword.getText().toString().length() < 8)) {
                    Snackbar.make(view, "Password must be at-least 8 characters", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                        Snackbar.make(view, "Passwords Doesn't Match", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else {
                        ProfileMaster master = new ProfileMaster();
                        master.setUserID(String.valueOf(SharedUtils.getUserID(mActivity)));
                        master.setUsername(etName.getText().toString());
                        master.setEmail(etEmail.getText().toString());
                        master.setMobile(etMobNo.getText().toString());
                        master.setCity(etCity.getText().toString());
                        master.setState(etState.getText().toString());
                        master.setArea(etArea.getText().toString());
                        master.setOffered(mServicesOffered);
                        master.setRequired(mServicesReceived);
                        master.setCost(etCost.getText().toString());
                        master.setDuration(mServicesCharges);
                        master.setStartTime(etStartTime.getText().toString());
                        master.setEndTime(etEndTime.getText().toString());
                        master.setPassword(etConfirmPassword.getText().toString());
                        master.setUpdatedAt(new Date());
                        db.updateLoginUser(master);
                        Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void setValues() {
        List<ProfileMaster> getdetails = db.getUserDetails(SharedUtils.getUserID(this));
        if (!getdetails.isEmpty()) {
            etName.setText(getdetails.get(0).getUsername());
            etEmail.setText(getdetails.get(0).getEmail());
            etMobNo.setText(getdetails.get(0).getMobile());
            etCity.setText(getdetails.get(0).getCity());
            etState.setText(getdetails.get(0).getState());
            etArea.setText(getdetails.get(0).getArea());
            spinner1.setSelection(getIndex(getResources().getStringArray(R.array.services_offered), getdetails.get(0).getOffered()));
            spinner2.setSelection(getIndex(getResources().getStringArray(R.array.services_required), getdetails.get(0).getRequired()));
            etCost.setText(getdetails.get(0).getCost());
            spinDuartion.setSelection(getIndex(getResources().getStringArray(R.array.cost_details), getdetails.get(0).getDuration()));
            etStartTime.setText(getdetails.get(0).getStartTime());
            etEndTime.setText(getdetails.get(0).getEndTime());
        }
    }

    private int getIndex(String[] spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.length; i++) {
            if (spinner[i].toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
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
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String AM_PM = "";
                            Calendar datetime = Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            datetime.set(Calendar.MINUTE, minute);
                            int mAm_PM = datetime.get(Calendar.AM_PM);
                            int mHour = datetime.get(Calendar.HOUR);
                            if (mAm_PM == Calendar.AM)
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
                etStartTime.setText(time);
                etEndTime.setText("");
            } else if (tv == 1) {
                String t = etStartTime.getText().toString();
                if (Utils.isGreaterThan24(t, time)) {
                    Utils.alertBox(mActivity, "Time should be less than 24 hous!");
                } else {
                    etEndTime.setText(time);
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
