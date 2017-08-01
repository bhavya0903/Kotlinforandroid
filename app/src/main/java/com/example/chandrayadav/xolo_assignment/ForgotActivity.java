package com.example.chandrayadav.xolo_assignment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class ForgotActivity extends AppCompatActivity {

    private Button btnResetPassword;
    private TextInputLayout inputLayoutPhoneNumber;
    private EditText inputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        init();

    }

    private void init() {
        initviews();
    }

    private void initviews() {
        btnResetPassword = (Button) findViewById(R.id.btn_resetPasswd);
        inputLayoutPhoneNumber = (TextInputLayout) findViewById(R.id.input_phone_number);
        inputPhone = (EditText) findViewById(R.id.input_phone);
//        btnRegister.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//            }
//        });
//
//    }
    }
}
