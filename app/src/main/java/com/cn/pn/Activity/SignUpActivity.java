package com.cn.pn.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.pn.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    static final String TAG = "SignUpActivity";
    Database db;

    @Bind(R.id.btn_signup)Button createAccount;
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.input_confirmpassword)
    EditText _confirmpasswordText;
    @Bind(R.id.link_login)
    TextView _loginLink;
    @Bind(R.id.input_mobile)
    EditText _mobileText;
    @Bind(R.id.input_vehicle)
    EditText _vehicleText;
    @Bind(R.id.input_lincense)
    EditText _lincenseText;
    @Bind(R.id.input_dob)
    EditText _dobText;
    @Bind(R.id.input_address)
    EditText _addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = new Database(SignUpActivity.this);
        ButterKnife.bind(this);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                localdataValidation();

            }


        });

    }

    private void localdataValidation() {
        Log.d(TAG,"SignUp");

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmpassword = _confirmpasswordText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String vehicle = _vehicleText.getText().toString();
        String license = _lincenseText.getText().toString();
        String dob = _dobText.getText().toString();
        String address = _addressText.getText().toString();

        if (!validate()) {
            onSignupFailed();
            return;
        } else {
            db.insertnumber(name, email, password, mobile, vehicle, license, dob, address);
        }

        createAccount.setEnabled(false);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Intent in = new Intent(getApplication(), LoginActivity.class);
                        startActivity(in);

                    }
                }, 3000);

    }

    private boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmpassword = _confirmpasswordText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String vehicle = _vehicleText.getText().toString();
        String license = _lincenseText.getText().toString();
        String dob = _dobText.getText().toString();
        String address = _addressText.getText().toString();


        if (password.equals(confirmpassword)) {

        } else {
            _confirmpasswordText.setError("Please enter same password");
            valid = false;
        }

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "sign failed", Toast.LENGTH_LONG).show();

        createAccount.setEnabled(true);
    }

}
