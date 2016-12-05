package com.example.mahesha.flames;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NamesActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText mBoysName;
    private TextInputEditText mGirlsName;
    private Button mCalculate;
    private TextView mResult;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBoysName = (TextInputEditText) findViewById(R.id.boys_name);
        mGirlsName = (TextInputEditText) findViewById(R.id.girls_name);
        mCalculate = (Button) findViewById(R.id.calculate);
        mResult = (TextView) findViewById(R.id.header_result);
        mImageView = (ImageView) findViewById(R.id.result_image);
        final String[] boysName = {""};
        final String[] girlsName = {""};
        mBoysName.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onAfterTextChanged(Editable s) {
                boysName[0] = String.valueOf(mBoysName.getText().toString());
                if (boysName[0] == null || boysName[0].isEmpty() || girlsName[0] == null || girlsName[0].isEmpty()) {
                    mResult.setText(getString(R.string.result_label));
                }
            }
        });
        mGirlsName.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onAfterTextChanged(Editable s) {
                girlsName[0] = String.valueOf(mGirlsName.getText().toString());
                if (boysName[0] == null || boysName[0].isEmpty() || girlsName[0] == null || girlsName[0].isEmpty()) {
                    mResult.setText(getString(R.string.result_label));
                }
            }
        });
        mCalculate.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_names, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calculate:
                char result = originalLogic();
                String relation = "";

//                switch (result) {
//                    case "Friends":
//                        /***Info: If FRIEND*/
//                        relation = getResources().getString(R.string.result,
//                                getString(R.string.friend));
//                        break;
//                    case "Lovers":
//                        /***Info: If LOVE*/
//                        relation = getResources().getString(R.string.result,
//                                getString(R.string.love));
//                        break;
//                    case "Acquaintance":
//                        /***Info: If ACQUAINTANCE*/
//                        relation = getResources().getString(R.string.result,
//                                getString(R.string.attraction));
//                        break;
//                    case "Married":
//                        /***Info: If MARRIAGE*/
//                        relation = getResources().getString(R.string.result,
//                                getString(R.string.marriage));
//                        break;
//                    case "Enemies":
//                        /***Info: If ENEMY*/
//                        relation = getResources().getString(R.string.result,
//                                getString(R.string.enemy));
//                        break;
//                    case "Sweethearts":
//                        /***Info: If SWEETHEARTS*/
//                        relation = getResources().getString(R.string.result,
//                                getString(R.string.sister));
//                        break;
//                }
                switch (result) {
                    case 'f':
                        /***Info: If FRIEND*/
                        relation = getResources().getString(R.string.result,
                                getString(R.string.friend));
                        break;
                    case 'l':
                        /***Info: If LOVE*/
                        relation = getResources().getString(R.string.result,
                                getString(R.string.love));
                        break;
                    case 'a':
                        /***Info: If ACQUAINTANCE*/
                        relation = getResources().getString(R.string.result,
                                getString(R.string.attraction));
                        break;
                    case 'm':
                        /***Info: If MARRIAGE*/
                        relation = getResources().getString(R.string.result,
                                getString(R.string.marriage));
                        break;
                    case 'e':
                        /***Info: If ENEMY*/
                        relation = getResources().getString(R.string.result,
                                getString(R.string.enemy));
                        break;
                    case 's':
                        /***Info: If SWEETHEARTS*/
                        relation = getResources().getString(R.string.result,
                                getString(R.string.sister));
                        break;
                    default:
                        System.out.println("FLAME Test works only for different names");
                        break;
                }
                mResult.setText(relation);
                break;
        }
    }

    private String calculateFlames() {

        String boyName = String.valueOf(mBoysName.getText());
        String girlName = String.valueOf(mGirlsName.getText());
        String[] _fName, _sName;
        String[][] _flames = {{"F", "Friends"}, {"L", "Lovers"}, {"A", "Acquaintance"}, {"M", "Married"},
                {"E", "Enemies"}, {"S", "Sweethearts"}};

        String _fn, _sn;

        _fn = boyName.toUpperCase().replace(" ", "");
        _sn = girlName.toUpperCase().replace(" ", "");

		/* setting array size */
        _fName = new String[_fn.length()];
        _sName = new String[_sn.length()];

		/* filling-up array */
        for (int i = 0; i < _fn.length(); i++) {
            _fName[i] = _fn.substring(i, i + 1);
        }
        for (int i = 0; i < _sn.length(); i++) {
            _sName[i] = _sn.substring(i, i + 1);
        }
        /* comparing from F.L.A.M.E.S letters */
        int _resA = 0, _resB = 0;
        for (int i = 0; i < _fName.length; i++) {
            for (int j = 0; j < _flames.length; j++) {
                if (_fName[i].equals(_flames[j][0])) {
                    _resA++;
                }
            }
        }
        int _x = (_fn.length() - _resA);
        for (int i = 0; i < _sName.length; i++) {
            for (int j = 0; j < _flames.length; j++) {
                if (_sName[i].equals(_flames[j][0])) {
                    _resB++;
                }
            }
        }
        /* iterating on F.L.A.M.E.S */
        int _y = (_sn.length() - _resB), _flamesRes = 0;
        for (int i = 0; i < (_y + _x); i++) {
            if (_flamesRes == 5) {
                _flamesRes = 0;
            } else {
                _flamesRes++;
            }
        }
        /* Displaying the results */
        System.out.print("Result: " + (_flames[_flamesRes][1]));
        return "" + _flames[_flamesRes][1];
    }

    private char originalLogic() {
        String boyName = String.valueOf(mBoysName.getText());
        String girlName = String.valueOf(mGirlsName.getText());
        for (int i = 0; i < boyName.length(); i++) {
            for (int j = 0; j < girlName.length(); j++) {
                if (boyName.charAt(i) == girlName.charAt(j)) {
                    boyName = boyName.replaceFirst(String.valueOf(boyName.charAt(i)), "#");
                    girlName = girlName.replaceFirst(String.valueOf(girlName.charAt(j)), "#");
                }
            }
        }
        String result = boyName + girlName;
        result = result.replaceAll("#", "");
        int resultLength = result.length();
        System.out.println("Result Length is: " + resultLength);
        String baseInput = "flames";
        System.out.println("Base input length is: " + baseInput.length());
        StringBuffer s3 = new StringBuffer("flames");
        String s4 = new String();

        for (int i = 0; i < 5; i++) {
            int n = -1, l = 0, p = 0;
            for (int j = 1; j <= resultLength; j++) {
                n++;
                l++;
                if (n > s3.length() - 1) {
                    char e = s3.charAt(p);
                    if (l == resultLength) {
                        s3.deleteCharAt(p);
                        s4 = s3.substring(p, s3.length());
                        s3.delete(p, s3.length());
                        s3.insert(0, s4);
                        break;
                    } else {
                        p++;
                        if (p == s3.length()) {
                            p = 0;
                        }
                    }
                } else {
                    char e = s3.charAt(n);
                    if (l == resultLength) {
                        s3.deleteCharAt(n);

                        s4 = s3.substring(n, s3.length());
                        s3.delete(n, s3.length());
                        s3.insert(0, s4);
                        break;
                    }
                }
            }
        }
        char r = s3.charAt(0);
        return r;
    }
}
