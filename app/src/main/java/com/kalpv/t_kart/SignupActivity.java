package com.kalpv.t_kart;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import br.com.simplepass.loadingbutton.customViews.CircularProgressImageButton;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kalpv.t_kart.Interface.OnFragmentChangeListener;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class SignupActivity extends AppCompatActivity implements OnFragmentChangeListener {

    StateProgressBar stateProgressBar;
    TextView tv_details, tv_heading, tv_skip;
    CircularProgressImageButton btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Signup");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        SignupOneFragment signupOneFragment = new SignupOneFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, signupOneFragment);
        fragmentTransaction.commit();

        stateProgressBar = findViewById(R.id.spb_progress);
        tv_details = findViewById(R.id.tv_details);
        tv_heading = findViewById(R.id.tv_heading);
        btn_next = findViewById(R.id.btn_next);
        tv_skip = findViewById(R.id.tv_skip);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.enter, R.anim.exit);
        } else {
            new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                    .setPositiveButton("Skip", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setTitle("Skip profile update?")
                    .setMessage("Are you sure you want to skip updating your information? You can update it later in the profile section.").show();

        }


    }


    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right, R.anim.enter, R.anim.exit);
        fragmentTransaction.replace(R.id.container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}
