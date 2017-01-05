package com.softgen.gate.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment implements LabelledSpinner.OnItemChosenListener, DatePickerDialog.OnDateSetListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btSignUp;
    EditText etName;
    EditText etEmail;
    EditText etMobNo;
    EditText etPassword;
    EditText etCity;
    EditText etState;
    EditText etArea;
    EditText etConfirmPassword;
    EditText etStartTime;
    EditText etEndTime;
    EditText etCost;
    private DBHelper db;
    private String TAG = "", MODULE = "Edit Profile";
    private LabelledSpinner spinDuartion;
    private String mServicesOffered;
    private String mServicesReceived;
    private Utils utils;
    private String mServicesCharges;
    private Activity mActivity;
    private String jsonDate;
    private String validFrom;
    private String selectedDay;
    private OnFragmentInteractionListener mListener;
    private FragmentManager manager;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this.getActivity());
        manager = getFragmentManager();
        spinDuartion = (LabelledSpinner) view.findViewById(R.id.services_charges);
        spinDuartion.setItemsArray(R.array.cost_details);
        spinDuartion.setOnItemChosenListener(this);
        btSignUp = (Button) view.findViewById(R.id.btn_sign_up);
        etName = (EditText) view.findViewById(R.id.input_name);
        etEmail = (EditText) view.findViewById(R.id.input_email);
        etMobNo = (EditText) view.findViewById(R.id.input_mob);
        etPassword = (EditText) view.findViewById(R.id.password);
        etCity = (EditText) view.findViewById(R.id.input_city);
        etState = (EditText) view.findViewById(R.id.input_state);
        etArea = (EditText) view.findViewById(R.id.input_area);
        etConfirmPassword = (EditText) view.findViewById(R.id.confirm_password);
        etStartTime = (EditText) view.findViewById(R.id.start_time);
        etEndTime = (EditText) view.findViewById(R.id.end_time);
        etCost = (EditText) view.findViewById(R.id.cost);
        db = new DBHelper(this.getActivity());
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
                        master.setUpdatedAt(new Date());
                        db.updateLoginUser(master);
                        replaceSettings();
                    }
                }
            }
        });
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
    public void replaceSettings() {
        SettingsFragment fragmentA = new SettingsFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragmentA, "fragA");
        transaction.commit();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setValues() {
        List<ProfileMaster> getdetails = db.getUserDetails(SharedUtils.getUserID(this.getActivity()));
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
