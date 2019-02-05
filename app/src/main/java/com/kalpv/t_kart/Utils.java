package com.kalpv.t_kart;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

class Utils {

    static boolean isEmailPasswordValid(EditText email, EditText password) {

        String str_email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.getText().toString().isEmpty() && email.getText().toString().matches(str_email_pattern)
                && password.getText().toString().length() >= 6 && password.getText().toString().length() <= 14) {
            return true;
        } else {
            if (email.getText().toString().isEmpty() || !email.getText().toString().matches(str_email_pattern)) {
                email.setError("Invalid Email");
            }
            if (password.getText().toString().length() < 6 || password.getText().toString().length() > 14) {
                password.setError("Password length must be between 6 to 14 characters");
            }
            return false;
        }

    }

    static void passwordToggleVisibility(final EditText editText, final TextInputLayout textInputLayout) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    textInputLayout.setPasswordVisibilityToggleEnabled(false);
                }else {
                    textInputLayout.setPasswordVisibilityToggleEnabled(true);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().length() > 0)
                    textInputLayout.setPasswordVisibilityToggleEnabled(true);
                else
                    textInputLayout.setPasswordVisibilityToggleEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
