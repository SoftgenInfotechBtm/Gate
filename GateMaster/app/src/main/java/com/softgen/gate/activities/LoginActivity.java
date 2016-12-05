package com.softgen.gate.activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.softgen.gate.database.DBHelper;
import com.softgen.gate.databaseHandler.DaoMaster;
import com.softgen.gate.gatedb.R;
import com.softgen.gate.provider.SharedUtils;

import io.fabric.sdk.android.Fabric;



public class LoginActivity extends AppCompatActivity {
    public Button btnSignUp;
    private  final String DB_NAME ="gate.db" ;
    public TextView tv, tvv;
    public TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    public EditText inputName, inputEmail, inputPassword;
    DBHelper db;
    private CheckBox rememberMe;
    private Context mActivity;

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        mActivity = LoginActivity.this;
        if (SharedUtils.isLoginDisabled(mActivity)) {
            finish();
            startActivity(new Intent(mActivity, HomeActivity.class));
        }
        setupDb();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        db = new DBHelper(mActivity);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name1);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        rememberMe = (CheckBox) findViewById(R.id.checkBox);
        inputName = (EditText) findViewById(R.id.input_name);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        String username = SharedUtils.getUserName(mActivity);
        String password = SharedUtils.getPassword(mActivity);
        if (username != null && password != null) {
            inputName.setText(username);
            inputPassword.setText(password);
            rememberMe.setChecked(true);
        }
        tv = (TextView) findViewById(R.id.tv1);
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(mActivity, ProfileActivity.class);
                startActivity(i);
            }
        });

        tvv = (TextView) findViewById(R.id.tv2);
        tvv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                Intent in = new Intent(mActivity, ForgotActivity.class);
                startActivity(in);
            }
        });

        btnSignUp = (Button) findViewById(R.id.login);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputName.getText().toString().isEmpty()
                        && inputPassword.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Fields Cannot be empty ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (inputPassword.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Password must be at-least 6 characters", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    boolean flags = db.getUserData(inputName.getText().toString(), inputPassword.getText().toString());
                    if (flags) {
                        if (rememberMe.isChecked()) {
                            SharedUtils.storeUserName(mActivity, inputName.getText().toString());
                            SharedUtils.storePasword(mActivity, inputPassword.getText().toString());
                        }
                        Intent i1 = new Intent(mActivity, HomeActivity.class);
                        startActivity(i1);
                    } else {
                        Snackbar.make(view, "Username or Password doesn't exist", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            }
        });
    }

    /**
     * Validating form
     */
    public boolean submitForm() {
        if (!validateName()) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean validateName() {
        String name = inputName.getText().toString().trim();
        if (name.isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        String psw = inputPassword.getText().toString().trim();
        if (psw.isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void setupDb(){
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(this, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
    }

    public void onBackPressed() {
        finish();
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
