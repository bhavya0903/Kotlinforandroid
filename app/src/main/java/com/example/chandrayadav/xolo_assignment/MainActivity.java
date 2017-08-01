package com.example.chandrayadav.xolo_assignment;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnCreateAccount;
    private TextView tvForgotPassword;
    private TextInputLayout inputLayoutPhoneNumber, inputLayoutPassword;
    private EditText inputPhone, inputPassword;
    private View vName, vPassword;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        init();

    }

    private void init() {
        initviews();
    }

    private void initviews() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnCreateAccount = (Button) findViewById(R.id.btn_createAccount);
        tvForgotPassword = (TextView) findViewById(R.id.tv_forgotPassword);
        inputLayoutPhoneNumber = (TextInputLayout) findViewById(R.id.input_phone_number);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        inputPhone = (EditText) findViewById(R.id.input_phone);
        inputPassword = (EditText) findViewById(R.id.input_password);

        vName = (View) findViewById(R.id.view_phone);
        vPassword = (View) findViewById(R.id.view_password);


        // get Instance  of Database Adapter
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        try {
            btnCreateAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tvForgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, ForgotActivity.class);
                    startActivity(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String phoneNumber = inputPhone.getText().toString();
                    String password = inputPassword.getText().toString();
                    //checks for 0 string
                    if (phoneNumber.trim().length() == 0) {
                        vName.setBackgroundColor(Color.DKGRAY);
                    }
                    try {
                        if (inputPassword.getText().toString().trim().length() == 0) {
                            inputPassword.setError("Password is not entered");
                            inputPassword.requestFocus();
                        } else {
                            sign(phoneNumber, password);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void sign(String number, String password) {

        // fetch the Password form database for respective user name
        String storedPassword = loginDataBaseAdapter.getSingleEntry(number);

        // check if the Stored password matches with  Password entered by user
        try {
            if (password.equals(storedPassword)) {

                //        Toast.makeText(MainActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                //dialog.dismiss();
                Intent it = new Intent(getApplicationContext(), AfterLoginActivity.class);
                startActivity(it);
            } else {
                Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
