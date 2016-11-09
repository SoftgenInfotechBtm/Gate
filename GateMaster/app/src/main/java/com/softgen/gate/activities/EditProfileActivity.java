package com.softgen.gate.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.softgen.gate.database.DBHelper;
import com.softgen.gate.gatedb.R;
import com.softgen.gate.model.ProfileMaster;

import java.util.Date;

public class EditProfileActivity extends AppCompatActivity {
    public Button button;
    public TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutmobileno, inputLayoutcity, inputLayoutstate, inputLayoutarea, inputLayoutoffered, inputLayoutrequired, inputLayoutPassword, inputLayoutconfirmpassword;
    public EditText inputName, inputEmail, inputmobileno, inputPassword, inputcity, inputstate, inputarea, inputoffered, inputrequired, inputconfirm;

    public DBHelper db;
    public Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //spinner for services offered
        spinner1 = (Spinner) findViewById(R.id.services_offered1);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.services_offered, android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter);

        //spinner for services required
        spinner2 = (Spinner) findViewById(R.id.services_required1);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.services_required, android.R.layout.simple_spinner_item);
        spinner2.setAdapter(adapter1);

        db = new DBHelper(EditProfileActivity.this);

        //layouts for edit texts
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name1);
        inputEmail = (EditText) findViewById(R.id.input_email1);
//        inputEmail.setEnabled(false);
//        inputEmail.setText(getIntent().getStringExtra("email"));
//        Intent i = getIntent();
//        i.getStringExtra("email");
        inputmobileno = (EditText) findViewById(R.id.input_mob1);
//        inputmobileno.setEnabled(false);
//        inputmobileno.setText(getIntent().getStringExtra("mobile"));
//        Intent i2 = getIntent();
//        i2.getStringExtra("mobile");
        inputLayoutcity = (TextInputLayout) findViewById(R.id.input_layout_city1);
        inputLayoutstate = (TextInputLayout) findViewById(R.id.input_layout_state1);
        inputLayoutarea = (TextInputLayout) findViewById(R.id.input_layout_area1);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password1);
        inputLayoutconfirmpassword = (TextInputLayout) findViewById(R.id.input_layout_confirmPassword1);

        //ids for edit texts
        inputName = (EditText) findViewById(R.id.input_name1);
        inputEmail = (EditText) findViewById(R.id.input_email1);
        inputmobileno = (EditText) findViewById(R.id.input_mob1);
        inputcity = (EditText) findViewById(R.id.input_city1);
        inputstate = (EditText) findViewById(R.id.input_state1);
        inputarea = (EditText) findViewById(R.id.input_area1);
        inputPassword = (EditText) findViewById(R.id.password1);
        inputconfirm = (EditText) findViewById(R.id.confirm_password1);

        button = (Button) findViewById(R.id.btn_save);
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
                    Snackbar.make(view, "Password must be at-least 6 characters", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
                        master.setOffered(spinner1.getSelectedItem().toString());
                        master.setRequired(spinner2.getSelectedItem().toString());
                        master.setPassword(inputconfirm.getText().toString());
//                        master.setConfirmpassword(inputconfirm.getText().toString());
                        master.setCreatedAt(new Date());
                        master.setUpdatedAt(new Date());
                        Log.e("values", master + "");
                        db.editProfileUser(master);
                        Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        Snackbar.make(view, "Profile Saved Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            }
        });
    }

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
}
