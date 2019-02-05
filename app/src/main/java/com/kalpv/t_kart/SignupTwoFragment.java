package com.kalpv.t_kart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kalpv.t_kart.Interface.OnFragmentChangeListener;
import com.kalpv.t_kart.Model.User;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.HashMap;
import java.util.UUID;


public class SignupTwoFragment extends Fragment implements View.OnClickListener {

    private EditText et_userFName, et_userLName;
    private String userId;
    private FirebaseFirestore db;
    private SignupActivity signupActivity;

    public SignupTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
        }
        View view = inflater.inflate(R.layout.fragment_signup_two, container, false);
        et_userFName = view.findViewById(R.id.et_userFName);
        et_userLName = view.findViewById(R.id.et_userLName);
        signupActivity = (SignupActivity) getActivity();
        if (signupActivity != null) {
            signupActivity.tv_skip.setVisibility(View.VISIBLE);
            signupActivity.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            signupActivity.tv_heading.setText(R.string.signup_title_second);
            signupActivity.tv_details.setText(R.string.description_second);
            signupActivity.btn_next.setOnClickListener(this);
        }
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:

                if (!et_userFName.getText().toString().isEmpty() && !et_userLName.getText().toString().isEmpty()) {

                    signupActivity.btn_next.setImageDrawable(null);
                    signupActivity.btn_next.startAnimation();

//                    HashMap<String, Object> userMap = new HashMap<>();
//                    userMap.put("Id",userId);
//                    userMap.put("FirstName", et_userFName.getText().toString());
//                    userMap.put("LastName", et_userLName.getText().toString());

                    User user = new User();
                    user.setFirstName(et_userFName.getText().toString());
                    user.setLastName(et_userLName.getText().toString());
                    user.setId(userId);

                    db.collection(Constant.USERS).document(userId).set(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Name updated", Toast.LENGTH_SHORT).show();
                                        SignupThreeFragment signupThreeFragment = new SignupThreeFragment();
                                        OnFragmentChangeListener fragmentChangeListener = (OnFragmentChangeListener) getActivity();
                                        if (fragmentChangeListener != null) {
                                            fragmentChangeListener.replaceFragment(signupThreeFragment);
                                        }
                                        signupActivity.btn_next.setImageDrawable(getResources().getDrawable(R.drawable.round_arrow_forward_white_24dp));

                                        signupActivity.btn_next.startMorphRevertAnimation();
                                    } else {
                                        Toast.makeText(signupActivity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        signupActivity.btn_next.setImageDrawable(getResources().getDrawable(R.drawable.round_arrow_forward_white_24dp));
                                        signupActivity.btn_next.startMorphRevertAnimation();
                                    }

                                }
                            });
                } else {

                }

                break;

        }
    }
}
