package com.kalpv.t_kart;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kalpv.t_kart.Interface.OnFragmentChangeListener;
import com.kofigyan.stateprogressbar.StateProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupOneFragment extends Fragment implements View.OnClickListener {

    private EditText et_userEmail, et_userPassword, et_userConfirmPassword;
    private SignupActivity signupActivity;
    private FirebaseAuth mAuth;

    public SignupOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_one, container, false);
        mAuth = FirebaseAuth.getInstance();
        et_userEmail = view.findViewById(R.id.et_userEmail);
        et_userPassword = view.findViewById(R.id.et_userPassword);
        et_userConfirmPassword = view.findViewById(R.id.et_userConfirmPassword);
        TextInputLayout password_til = view.findViewById(R.id.password_til);
        final TextInputLayout confirmPassword_til = view.findViewById(R.id.confirmPassword_til);

        Utils.passwordToggleVisibility(et_userPassword, password_til);
        Utils.passwordToggleVisibility(et_userConfirmPassword, confirmPassword_til);

        signupActivity = (SignupActivity) getActivity();
        if (signupActivity != null) {
            signupActivity.tv_skip.setVisibility(View.GONE);
            signupActivity.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            signupActivity.tv_details.setText(R.string.description_first);
            signupActivity.btn_next.setOnClickListener(this);
        }

        et_userConfirmPassword.addTextChangedListener(new TextWatcher() {
            Drawable drawable = confirmPassword_til.getPasswordVisibilityToggleDrawable();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && et_userPassword.getText().toString().contentEquals(s)) {
                    confirmPassword_til.setPasswordVisibilityToggleDrawable(R.drawable.ic_done_success_24dp);
                } else {
                    confirmPassword_til.setPasswordVisibilityToggleDrawable(drawable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:

                if (et_userPassword.getText().toString().matches(et_userConfirmPassword.getText().toString())) {
                    if (Utils.isEmailPasswordValid(et_userEmail, et_userPassword)) {
                        signupActivity.btn_next.setImageDrawable(null);
                        signupActivity.btn_next.startAnimation();

//                        SignupTwoFragment signupTwoFragment = new SignupTwoFragment();
//                        OnFragmentChangeListener onFragmentChangeListener = (OnFragmentChangeListener) getActivity();
//                        if (onFragmentChangeListener != null) {
//                            onFragmentChangeListener.replaceFragment(signupTwoFragment);
//                        }



                        mAuth.createUserWithEmailAndPassword(et_userEmail.getText().toString(), et_userPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(signupActivity, "Sign up successful!", Toast.LENGTH_SHORT).show();
                                                    SignupTwoFragment signupTwoFragment = new SignupTwoFragment();
                                                    OnFragmentChangeListener onFragmentChangeListener = (OnFragmentChangeListener) getActivity();
                                                    if (onFragmentChangeListener != null) {
                                                        onFragmentChangeListener.replaceFragment(signupTwoFragment);
                                                    }
                                                    signupActivity.btn_next.setImageDrawable(getResources().getDrawable(R.drawable.round_arrow_forward_white_24dp));

                                                    signupActivity.btn_next.startMorphRevertAnimation();
                                                }
                                            }, Constant.DELAY_ONESEC);

                                        } else {
                                            Toast.makeText(signupActivity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            signupActivity.btn_next.setImageDrawable(getResources().getDrawable(R.drawable.round_arrow_forward_white_24dp));
                                            signupActivity.btn_next.startMorphRevertAnimation();
                                        }
                                    }
                                });


                    }
                } else {
                    Toast.makeText(getActivity(), "Passwords didn't match. Try again.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
