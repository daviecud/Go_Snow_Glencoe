package com.example.gosnow_glencoe.SnowChat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gosnow_glencoe.BaseActivity;
import com.example.gosnow_glencoe.BusinessActivity;
import com.example.gosnow_glencoe.MainActivity;
import com.example.gosnow_glencoe.R;
import com.example.gosnow_glencoe.ResortActivity;
import com.example.gosnow_glencoe.SnowReport.SnowForecastActivity;
import com.example.gosnow_glencoe.SportActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;



public class Sign_UpActivity extends BaseActivity {

    private static final String TAG = "EmailPassword";

    private Toolbar toolbar;
    private NavigationBarView bottomBarNav;
    private EditText emailField;
    private EditText passwordField;
    private Button createAccBtn, signInBtn;
    private AlertDialog myAlert;
    private ProgressDialog loadingBar;

    private FirebaseAuth auth;
    private DatabaseReference rootRef;

    /*TODO create a check for email that includes an @ in email address, add second password field to confirm password matches
        remove sign up with phone, maybe add sign in with google option. Add stay signed in option
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        bottomBarNav = findViewById(R.id.signUp_bottom_nav);
        bottomBarNav.setOnItemSelectedListener(bottomNavMethod);

        loadingBar = new ProgressDialog(this);

        //Initialize fields declared above in a function
        initializeFields();

        //Initialize Authorisation
        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_UpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                createAccount(email, password);
            }
        });
    }

    private void initializeFields() {
        //Views
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        //Buttons
        createAccBtn = findViewById(R.id.create_acc_btn);
        signInBtn = findViewById(R.id.sign_in_btn);
    }

    //Navigation bar at bottom of screen
    private NavigationBarView.OnItemSelectedListener bottomNavMethod = item -> {

        if (item.getItemId() == R.id.signIn_bottom_nav_home) {
            Intent home_intent = new Intent(Sign_UpActivity.this, MainActivity.class);
            startActivity(home_intent);
        }
        else if (item.getItemId() == R.id.signIn_bottom_nav_snowreport) {
            Intent report_intent = new Intent(Sign_UpActivity.this, SnowForecastActivity.class);
            startActivity(report_intent);
        }
        else if (item.getItemId() == R.id.signIn_bottom_nav_sports) {
            Intent sports_intent = new Intent(Sign_UpActivity.this, SportActivity.class);
            startActivity(sports_intent);
        }
        else if (item.getItemId() == R.id.signIn_bottom_nav_resort) {
            Intent resort_intent = new Intent(Sign_UpActivity.this, ResortActivity.class);
            startActivity(resort_intent);
        }
        else if (item.getItemId() == R.id.signIn_bottom_nav_business) {
            Intent business_intent = new Intent(Sign_UpActivity.this, BusinessActivity.class);
            startActivity(business_intent);
        }

        return true;
    };

    protected void readMeButton() {

        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setTitle("Exit");
        myAlert.setMessage("Are you sure?");
        myAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        myAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        myAlert.show();
    }



    private void createAccount(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Sign_UpActivity.this, "Please enter an email address!",
                    Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(Sign_UpActivity.this, "Please enter a password!",
                    Toast.LENGTH_LONG).show();
        } else {

            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait we are creating an account for you. Thanks");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String currentUserID = auth.getCurrentUser().getUid();
                                String phoneDeviceId = FirebaseMessaging.getInstance().getToken().toString();

                                rootRef.child("Users").child(currentUserID).setValue("");
                                rootRef.child("Users").child(currentUserID).child("device_Id")
                                        .setValue(phoneDeviceId);

                                Intent intent = new Intent(Sign_UpActivity.this, SnowChatActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Sign_UpActivity.this, "Error occurred try again",
                                        Toast.LENGTH_LONG).show();
                            }
                            loadingBar.dismiss();
                        }

                    });
        }
    }

    //Menu creation and items listed in menu set
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_home_option) {
            Intent intent = new Intent(Sign_UpActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

   // private boolean validateForm() {
//        boolean valid = true;
//
//        String email = emailField.getText().toString();
//        //Check email field is not empty
//        if (TextUtils.isEmpty(email)) {
//            emailField.setError("Required");
//            valid = false;
//        } else {
//            emailField.setError(null);
//        }
//        //Check if email contains @ in the email address
//        if (!email.contains("@")) {
//            emailField.setError("@ required");
//            valid = false;
//        }
//
//        String password = passwordField.getText().toString();
//        //Check password field is not empty
//        if (TextUtils.isEmpty(password)) {
//            passwordField.setError("Required");
//            valid = false;
//        } else {
//            passwordField.setError(null);
//        }
//
//        if (password.length() < 6) {
//            Toast.makeText(getApplicationContext(),
//                    "Password too short, enter minimum 6 characters!!",
//                    Toast.LENGTH_SHORT).show();
//            valid = false;
//        }
//        return valid;
//    }

   // }
}