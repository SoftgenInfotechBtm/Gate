package com.softgen.gate.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import com.softgen.gate.provider.SharedUtils;
import com.softgen.gate.utility.InstantAutoCompleteTextView;
import com.softgen.gate.utility.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softgen.gate.utility.Utils.loadJSONFromAsset;

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
    private InstantAutoCompleteTextView atvServicesOffered, atvServicesRequired;
    private String TAG = "", MODULE = "Edit Profile";
    private LabelledSpinner spinDuartion;
    private Utils utils;
    private String mServicesCharges;
    private Activity mActivity;
    private String jsonDate;
    private String validFrom;
    private String selectedDay;
    private ArrayList<String> lServicesOffered;
    private ArrayList<String> lServicesReceived;
    private LinearLayout llServicesOffered;
    private LinearLayout llServicesRequired;
    private ArrayList<String> serviceList;
    private CoordinatorLayout profileAct;
    List<String> getOffered;
    List<String> getReceived;
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

    private void initListeners() {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileAct = (CoordinatorLayout) findViewById(R.id.profile_co);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity = EditProfileActivity.this;
        utils = new Utils(mActivity);
        ButterKnife.bind(this);
        spinDuartion = (LabelledSpinner) findViewById(R.id.services_charges);
        spinDuartion.setItemsArray(R.array.cost_details);
        btSignUp.setText("Update");
        llServicesOffered = (LinearLayout) findViewById(R.id.ll_services_off);
        llServicesRequired = (LinearLayout) findViewById(R.id.ll_services_req);
        atvServicesOffered = (InstantAutoCompleteTextView) findViewById(R.id.services_offered);
        atvServicesRequired = (InstantAutoCompleteTextView) findViewById(R.id.services_required);
        lServicesOffered = new ArrayList<String>();
        lServicesReceived = new ArrayList<String>();
        spinDuartion.setOnItemChosenListener(this);
        db = new DBHelper(EditProfileActivity.this);
        //ids for edit texts
        etMobNo.setEnabled(false);
        etEmail.setEnabled(false);
        etStartTime.setOnClickListener(this);
        etEndTime.setOnClickListener(this);
        etPassword.setVisibility(View.GONE);
        etConfirmPassword.setVisibility(View.GONE);
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
        initListeners();
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty() && etEmail.getText().toString().isEmpty()
                        && etMobNo.getText().toString().isEmpty() && etCity.getText().toString().isEmpty()
                        && etState.getText().toString().isEmpty() && etArea.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Fields Cannot be empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    ProfileMaster master = new ProfileMaster();
                    master.setUserID(String.valueOf(SharedUtils.getUserID(mActivity)));
                    master.setUsername(etName.getText().toString());
                    master.setEmail(etEmail.getText().toString());
                    master.setMobile(etMobNo.getText().toString());
                    master.setCity(etCity.getText().toString());
                    master.setState(etState.getText().toString());
                    master.setArea(etArea.getText().toString());
                    master.setCost(etCost.getText().toString());
                    master.setDuration(mServicesCharges);
                    master.setStartTime(etStartTime.getText().toString());
                    master.setEndTime(etEndTime.getText().toString());
                    master.setCreatedAt(new Date());
                    master.setUpdatedAt(new Date());
                    db.updateLoginUser(master);
                    db.createOfferedList(master, lServicesOffered);
                    db.createRequiredList(master, lServicesReceived);
                    Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
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
            etCost.setText(getdetails.get(0).getCost());
            spinDuartion.setSelection(getIndex(getResources().getStringArray(R.array.cost_details), getdetails.get(0).getDuration()));
            etStartTime.setText(getdetails.get(0).getStartTime());
            etEndTime.setText(getdetails.get(0).getEndTime());
        }
        getOffered = db.getServiceOffered(SharedUtils.getUserID(this));
        getReceived = db.getServiceReceived(SharedUtils.getUserID(this));
        addReceivedViews(getReceived);
        addOfferedViews(getOffered);
    }

    private void addOfferedViews(List<String> offeredList){
        for (int i = 0; i < offeredList.size(); i++) {
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View to_add = inflater.inflate(R.layout.item_services, llServicesOffered, false);
        final TextView serviceList = (TextView) to_add.findViewById(R.id.tv_service);
        final ImageView cancel = (ImageView) to_add.findViewById(R.id.iv_close);
        serviceList.setText(offeredList.get(i).toString());
        cancel.setId(i);
        if (checkAlreadyExist(llServicesOffered, offeredList.get(i).toString())) {
            to_add.setId(i);
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
        });}
    }
    private void addReceivedViews(List<String> receivedList){
        for (int i = 0; i < receivedList.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View to_add = inflater.inflate(R.layout.item_services, llServicesRequired, false);
            final TextView serviceList = (TextView) to_add.findViewById(R.id.tv_service);
            final ImageView cancel = (ImageView) to_add.findViewById(R.id.iv_close);
            cancel.setId(i);
            serviceList.setText(receivedList.get(i).toString());
            if (checkAlreadyExist(llServicesRequired, receivedList.get(i).toString())) {
                to_add.setId(i);
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
