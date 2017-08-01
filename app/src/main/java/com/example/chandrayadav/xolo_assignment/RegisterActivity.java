package com.example.chandrayadav.xolo_assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private Button btnRegister;


    private TextInputLayout inputLayoutPhoneNumber,inputLayoutEmailId,inputLayoutName,inputLayoutPassword;
    private EditText inputPhone, inputEmail, inputName ,inputPassword;
    LoginDataBaseAdapter loginDataBaseAdapter;
    private boolean isDbEntry = false;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        // get Instance  of Database Adapter
        loginDataBaseAdapter= new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();


        initviews();
    }

    private void initviews() {
        btnRegister = (Button) findViewById(R.id.register_button);

        inputLayoutPhoneNumber = (TextInputLayout) findViewById(R.id.input_phone_number);
        inputLayoutEmailId = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        inputPhone = (EditText) findViewById(R.id.input_phone);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputName = (EditText) findViewById(R.id.input_name);
        inputPassword = (EditText) findViewById(R.id.input_password);

        tvLogin = (TextView) findViewById(R.id.tv_login) ;

        initListener();


        try {
            btnRegister.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    submitForm();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initListener() {

       // inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
       // inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
       // inputName.addTextChangedListener(new MyTextWatcher(inputName));
       // inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
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

        @Override
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
                case R.id.input_phone:
                    validatePhoneNumber();
                    break;
            }

        }


    }
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        if (!validatePhoneNumber()) {
            return;
        }

        loginDataBaseAdapter.insertEntry(inputPhone.getText().toString(), inputPassword.getText().toString());
        isDbEntry = true;

        try {
            if(isDbEntry)
            {
                tvLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Thank You!..Account Successfully created   ", Toast.LENGTH_SHORT).show();
    }


    private boolean validatePhoneNumber() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutPhoneNumber.setError(getString(R.string.err_msg_name));
            requestFocus(inputPhone);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
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
            inputLayoutEmailId .setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmailId.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
}
