package com.kalpv.t_kart;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kalpv.t_kart.Interface.OnFragmentChangeListener;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupThreeFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private EditText et_datePicker;
    private String gender;
    private SignupActivity signupActivity;
    private FirebaseFirestore db;
    private String userId;

    public SignupThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_three, container, false);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
        }
        db = FirebaseFirestore.getInstance();
        signupActivity = (SignupActivity) getActivity();
        if (signupActivity != null) {
            signupActivity.tv_skip.setVisibility(View.VISIBLE);
            signupActivity.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            signupActivity.tv_heading.setText(R.string.signup_title_third);
            signupActivity.tv_details.setText(R.string.description_third);
            signupActivity.btn_next.setOnClickListener(this);
        }


        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        et_datePicker = view.findViewById(R.id.et_datePicker);
        et_datePicker.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton genderRadioButton = group.findViewById(checkedId);
                gender = genderRadioButton.getText().toString();

            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:

//                String gender = radioGroup.getCheckedRadioButtonId() == R.id.rb_male ?"Male":"Female";

                if(gender.matches("")){
                    Toast.makeText(getContext(), "Please specify gender", Toast.LENGTH_SHORT).show();
                }else {
                    signupActivity.btn_next.setImageDrawable(null);
                    signupActivity.btn_next.startAnimation();

                    db.collection(Constant.USERS).document(userId)
                            .update(Constant.UserGender,gender).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                signupActivity.btn_next.doneLoadingAnimation(Color.parseColor("#f32845"), BitmapFactory.decodeResource(getResources(),R.drawable.baseline_done_white_24dp));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        signupActivity.finish();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);

                                    }
                                },Constant.DELAY_ONESEC);

                            }else {
                                signupActivity.btn_next.setImageDrawable(getResources().getDrawable(R.drawable.round_arrow_forward_white_24dp));
                                signupActivity.btn_next.startMorphRevertAnimation();
                                Toast.makeText(signupActivity, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

                break;

            case R.id.et_datePicker:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;

        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        et_datePicker.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }
}
