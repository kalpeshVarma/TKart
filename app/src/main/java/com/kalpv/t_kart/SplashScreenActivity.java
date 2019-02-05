package com.kalpv.t_kart;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener {

    //TODO: Use butterknife library

    private FirebaseAuth mAuth;
    ImageView iv_logo;
    TextView tv_newUser;
    TextInputLayout til_password;
    CircularProgressButton btn_login;
    EditText et_userEmail, et_userPassword;
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet1, constraintSet2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(SplashScreenActivity.this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_splash_screen_enlarged);
            iv_logo = findViewById(R.id.iv_logo);
            tv_newUser = findViewById(R.id.tv_newUser);
            btn_login = findViewById(R.id.btn_login);
            et_userEmail = findViewById(R.id.et_email);
            et_userPassword = findViewById(R.id.et_password);
            constraintLayout = findViewById(R.id.constraintL);
            til_password = findViewById(R.id.til_password);

            btn_login.setOnClickListener(this);
            constraintSet1 = new ConstraintSet();
            constraintSet1.clone(constraintLayout);
            constraintSet2 = new ConstraintSet();
            constraintSet2.clone(SplashScreenActivity.this, R.layout.activity_splash_screen);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    ConstraintSet constraintSet = constraintSet2;
                    constraintSet.applyTo(constraintLayout);
                }
            }, Constant.DELAY_TWOSEC);

            Utils.passwordToggleVisibility(et_userPassword, til_password);

            tv_newUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SplashScreenActivity.this, SignupActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                }
            });


        }
    }

    @Override
    protected void onResume() {
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Toast.makeText(this, "onResume called", Toast.LENGTH_SHORT).show();
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                if (Utils.isEmailPasswordValid(et_userEmail, et_userPassword)) {
                    btn_login.startAnimation();
                    mAuth.signInWithEmailAndPassword(et_userEmail.getText().toString(), et_userPassword.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        btn_login.doneLoadingAnimation(Color.parseColor("#f32845"),
                                                BitmapFactory.decodeResource(getResources(), R.drawable.baseline_done_white_24dp));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                                                Toast.makeText(SplashScreenActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                            }
                                        }, 1000);


                                    } else {
                                        btn_login.startMorphRevertAnimation();
                                        Toast.makeText(SplashScreenActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
        }
    }

//    private void showError() {
//        if (!isEmailValid)
//            et_userEmail.setError("Invalid Email");
//
//        if (!isPasswordValid)
//            et_userPassword.setError("Password length must be between 4-8 characters");
//
//
//    }

}
