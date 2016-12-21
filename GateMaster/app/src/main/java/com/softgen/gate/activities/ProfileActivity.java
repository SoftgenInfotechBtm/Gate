package com.softgen.gate.activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.satsuware.usefulviews.LabelledSpinner;
import com.softgen.gate.database.DBHelper;
import com.softgen.gate.gatedb.R;
import com.softgen.gate.model.ProfileMaster;
import com.softgen.gate.utility.InstantAutoCompleteTextView;
import com.softgen.gate.utility.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.softgen.gate.utility.Utils.loadJSONFromAsset;

public class ProfileActivity extends AppCompatActivity implements LabelledSpinner.OnItemChosenListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private Button button;
    private String TAG = "", MODULE = "Adding Profile";
    private Activity mActivity;
    private TextInputLayout tilServicesOffered, tilServicesRequired;
    private InstantAutoCompleteTextView atvServicesOffered, atvServicesRequired;
    private EditText etName, etEmail, etMobileNo, etPassword, etCity, etState, etArea, etConfirm, etStart, etEnd, etCost;
    private Utils utils;
    private DBHelper db;
    private LabelledSpinner spinDuartion;
    private String mServicesOffered;
    private String mServicesReceived;
    private ArrayList<String> lServicesOffered;
    private ArrayList<String> lServicesReceived;
    private String mServicesCharges;
    private String jsonDate;
    private String validFrom;
    private String selectedDay;
    private LinearLayout llServicesOffered;
    private LinearLayout llServicesRequired;
    private ArrayList<String> serviceList;
    private CoordinatorLayout profileAct;

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
        initUI();
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DBHelper(ProfileActivity.this);
        profileAct = (CoordinatorLayout) findViewById(R.id.profile_co);
        mActivity = ProfileActivity.this;
        utils = new Utils(mActivity);
        tilServicesOffered = (TextInputLayout) findViewById(R.id.til_ser_offered);
        tilServicesRequired = (TextInputLayout) findViewById(R.id.til_ser_required);
        atvServicesOffered = (InstantAutoCompleteTextView) findViewById(R.id.services_offered);
        atvServicesRequired = (InstantAutoCompleteTextView) findViewById(R.id.services_required);
        llServicesOffered = (LinearLayout) findViewById(R.id.ll_services_off);
        llServicesRequired = (LinearLayout) findViewById(R.id.ll_services_req);
        etEmail = (EditText) findViewById(R.id.input_email);
        etMobileNo = (EditText) findViewById(R.id.input_mob);
        etName = (EditText) findViewById(R.id.input_name);
        etCity = (EditText) findViewById(R.id.input_city);
        etState = (EditText) findViewById(R.id.input_state);
        etArea = (EditText) findViewById(R.id.input_area);
        etPassword = (EditText) findViewById(R.id.password);
        etConfirm = (EditText) findViewById(R.id.confirm_password);
        etStart = (EditText) findViewById(R.id.start_time);
        etEnd = (EditText) findViewById(R.id.end_time);
        etCost = (EditText) findViewById(R.id.cost);
        button = (Button) findViewById(R.id.btn_sign_up);
        spinDuartion = (LabelledSpinner) findViewById(R.id.services_charges);
        spinDuartion.setItemsArray(R.array.cost_details);
        lServicesOffered = new ArrayList<String>();
        lServicesReceived = new ArrayList<String>();
        if (getIntent().getStringExtra("email") != null)
            etEmail.setText(getIntent().getStringExtra("email"));
        else
            etEmail.setText("testmahesh547@gmail.com");
        if (getIntent().getStringExtra("mobile") != null)
            etMobileNo.setText(getIntent().getStringExtra("mobile"));
        else
            etMobileNo.setText("9738514840");
        etEmail.setEnabled(false);
        etMobileNo.setEnabled(false);
        initListeners();
    }

    private void initListeners() {
        spinDuartion.setOnItemChosenListener(this);
        etStart.setOnClickListener(this);
        etEnd.setOnClickListener(this);
        button.setOnClickListener(this);
        atvServicesOffered.setThreshold(0);
        atvServicesRequired.setThreshold(0);
        serviceList = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(getApplicationContext()));
            JSONArray jsonArray = obj.getJSONArray("services");
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    serviceList.add(jsonArray.get(i).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapterDoctor = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, serviceList);
        atvServicesOffered.setAdapter(adapterDoctor);
        atvServicesRequired.setAdapter(adapterDoctor);
        etStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickTime(0);
                }
            }
        });

        etEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickTime(1);
                }
            }
        });
        atvServicesOffered.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View to_add = inflater.inflate(R.layout.item_services, llServicesOffered, false);
                final TextView serviceList = (TextView) to_add.findViewById(R.id.tv_service);
                final ImageView cancel = (ImageView) to_add.findViewById(R.id.iv_close);
                serviceList.setText(parent.getItemAtPosition(position).toString());
                cancel.setId(position);
                if (checkAlreadyExist(llServicesOffered, parent.getItemAtPosition(position).toString())) {
                    to_add.setId(position);
                    lServicesOffered.add(serviceList.getText().toString());
                    llServicesOffered.addView(to_add);
                    atvServicesOffered.setText("");
                } else {
                    Snackbar.make(profileAct, "Already Added", Snackbar.LENGTH_SHORT).show();
                    atvServicesOffered.setText("");
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int childCount = llServicesOffered.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            if (llServicesOffered.getChildAt(i).getId() == cancel.getId()) {
                                String serviceName = serviceList.getText().toString();
                                lServicesOffered.remove(serviceName);
                                llServicesOffered.removeViewAt(i);
                                break;
                            }
                        }
                    }
                });
            }
        });
        atvServicesRequired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final int pos = position;
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View to_add = inflater.inflate(R.layout.item_services, llServicesRequired, false);
                final TextView serviceList = (TextView) to_add.findViewById(R.id.tv_service);
                final ImageView cancel = (ImageView) to_add.findViewById(R.id.iv_close);
                cancel.setId(position);
                serviceList.setText(parent.getItemAtPosition(position).toString());
                if (checkAlreadyExist(llServicesRequired, parent.getItemAtPosition(position).toString())) {
                    to_add.setId(position);
                    lServicesReceived.add(serviceList.getText().toString());
                    llServicesRequired.addView(to_add);
                    atvServicesRequired.setText("");
                } else {
                    Snackbar.make(profileAct, "Already Added", Snackbar.LENGTH_SHORT).show();
                    atvServicesRequired.setText("");
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int childCount = llServicesRequired.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            if (llServicesRequired.getChildAt(i).getId() == cancel.getId()) {
                                String serviceName = serviceList.getText().toString();
                                lServicesReceived.remove(serviceName);
                                llServicesRequired.removeViewAt(i);
                                break;
                            }
                        }
                    }
                });
            }
        });
    }

    private boolean checkAlreadyExist(LinearLayout llServicesOffered, String name) {
        int childCount = llServicesOffered.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RelativeLayout childView = (RelativeLayout) llServicesOffered.getChildAt(i);
            TextView serviceData = (TextView) childView.findViewById(R.id.tv_service);
            if (serviceData.getText().toString().equals(name)) {
                return false;
            }
        }
        return true;
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
                etStart.setText(time);
                etEnd.setText("");
            } else if (tv == 1) {
                String t = etStart.getText().toString();
                if (Utils.isGreaterThan24(t, time)) {
                    Utils.alertBox(mActivity, "Time should be less than 24 hous!");
                } else {
                    etEnd.setText(time);
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
            case R.id.btn_sign_up:
                if (validateCredentials())
                    insertToDatabase();
                break;
        }
    }

    private void insertToDatabase() {
        ProfileMaster master = new ProfileMaster();
        master.setUsername(etName.getText().toString());
        master.setEmail(etEmail.getText().toString());
        master.setMobile(etMobileNo.getText().toString());
        master.setCity(etCity.getText().toString());
        master.setState(etState.getText().toString());
        master.setArea(etArea.getText().toString());
        master.setOffered(mServicesOffered);
        master.setRequired(mServicesReceived);
        master.setPassword(etConfirm.getText().toString());
        master.setCost(etCost.getText().toString());
        master.setDuration(mServicesCharges);
        master.setStartTime(etStart.getText().toString());
        master.setEndTime(etEnd.getText().toString());
        master.setCreatedAt(new Date());
        master.setUpdatedAt(new Date());
        Log.e("values", master + "");
        db.createProfileUser(master);
        db.createOfferedList(master, lServicesOffered);
        db.createRequiredList(master, lServicesReceived);
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateCredentials() {
        if (etName.getText().toString().isEmpty() && etEmail.getText().toString().isEmpty()
                && etMobileNo.getText().toString().isEmpty() && etCity.getText().toString().isEmpty()
                && etState.getText().toString().isEmpty() && etArea.getText().toString().isEmpty()
                && etPassword.getText().toString().isEmpty() && etConfirm.getText().toString().isEmpty()) {
            Snackbar.make(profileAct, "Fields Cannot be empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else if (etPassword.getText().toString().length() < 8
                || (etConfirm.getText().toString().length() < 8)) {
            Snackbar.make(profileAct, "Password must be at-least 8 characters", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else {
            if (!etPassword.getText().toString().equals(etConfirm.getText().toString())) {
                Snackbar.make(profileAct, "Passwords Doesn't Match", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            } else {
                return true;
            }
        }
        return false;
    }
}



