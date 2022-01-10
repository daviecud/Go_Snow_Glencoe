package com.example.gosnow_glencoe.SnowChat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gosnow_glencoe.BaseActivity;
import com.example.gosnow_glencoe.BusinessActivity;
import com.example.gosnow_glencoe.MainActivity;
import com.example.gosnow_glencoe.R;
import com.example.gosnow_glencoe.ResortActivity;
import com.example.gosnow_glencoe.SnowReport.SnowForecastActivity;
import com.example.gosnow_glencoe.SportActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;


public class SignInActivity extends BaseActivity {

    private Toolbar toolbar;
    private NavigationBarView navigationBarView;
    private EditText input_username, input_email, input_password;
    private TextView newAccountLink, forgotPasswordLink;
    private Button signInButton, phoneLoginButton;
    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private ProgressDialog loadingBar;
    private static final String TAG = "EmailPassword";
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        loadingBar = new ProgressDialog(this);

        InitializeFields();

//        toolbar = findViewById(R.id.chat_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Sign In");

        navigationBarView = findViewById(R.id.signIn_bottom_nav);
        navigationBarView.setOnItemSelectedListener(navBarViewMethod);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allowUserToSignIn();
            }
        });

        newAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreateAccount();
            }
        });
    }

    private void InitializeFields() {

        signInButton = findViewById(R.id.sign_in_btn);
        //forgotPasswordLink = findViewById(R.id.forget_password_link);
        newAccountLink = findViewById(R.id.new_acc_link);
        input_email = findViewById(R.id.email);
        input_password = findViewById(R.id.password);
//        input_username = findViewById(R.id.username);
        loadingBar = new ProgressDialog(this);
    }

    //On Activity start check user
    @Override
    public void onStart() {
        super.onStart();

        if (currentUser != null) {
            SendUserToSnowChatActivity();
        }
    }

    private void allowUserToSignIn() {

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email..", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password..", Toast.LENGTH_SHORT).show();
        }
        else {

            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait....");
            loadingBar.show();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String currentUserID = auth.getCurrentUser().getUid();
                                String phoneDeviceTokenID = FirebaseMessaging.getInstance().getToken().toString();
//                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SignInActivity.this, new OnSuccessListener<InstanceIdResult>() {
//                                    @Override
//                                    public void onSuccess(InstanceIdResult instanceIdResult) {
//                                        String token = instanceIdResult.getToken();
//                                        Log.e("Token", token);
//                                    }
//                                });

                                userRef.child(currentUserID).child("device_Id")
                                        .setValue(phoneDeviceTokenID)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    SendUserToSnowChatActivity();
                                                    loadingBar.dismiss();
                                                }
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(SignInActivity.this, "Error: ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void SendUserToSnowChatActivity() {
        Log.d(TAG, "signInWithEmail:success");
        Intent intent = new Intent(SignInActivity.this, SnowChatActivity.class);
        startActivity(intent);
    }

    private void goToCreateAccount(){
        Intent intent = new Intent(SignInActivity.this, Sign_UpActivity.class);
        startActivity(intent);
    }

    private NavigationBarView.OnItemSelectedListener navBarViewMethod = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (item.getItemId() == R.id.signIn_bottom_nav_home) {
                Intent home_intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(home_intent);
            }
            else if (item.getItemId() == R.id.signIn_bottom_nav_snowreport) {
                Intent report_intent = new Intent(SignInActivity.this, SnowForecastActivity.class);
                startActivity(report_intent);
            }
            else if (item.getItemId() == R.id.signIn_bottom_nav_sports) {
                Intent sports_intent = new Intent(SignInActivity.this, SportActivity.class);
                startActivity(sports_intent);
            }
            else if (item.getItemId() == R.id.signIn_bottom_nav_resort) {
                Intent resort_intent = new Intent(SignInActivity.this, ResortActivity.class);
                startActivity(resort_intent);
            }
            else if (item.getItemId() == R.id.signIn_bottom_nav_business) {
                Intent business_intent = new Intent(SignInActivity.this, BusinessActivity.class);
                startActivity(business_intent);
            }

            return true;
        }
    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.chat_sign_in_menu, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        if (item.getItemId() == R.id.menu_home_option) {
//            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//            startActivity(intent);
//        }
//
//        if (item.getItemId() == R.id.menu_signout_option) {
//            auth.signOut();
//            Intent intent = new Intent(SignInActivity.this, SignInActivity.class);
//            startActivity(intent);
//        }
//        return true;
//    }

//    private void SignIn(String email, String password) {
//        Log.d(TAG, "signIn:" + email);
//        if (!validateForm()) {
//            return;
//        }
////        showProgressDialog();
//
//        //Start sign in with email
//        auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //Sign in successfully will take user to SnowChatActivity, and display welcome message
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = auth.getCurrentUser();
//                            Intent intent = new Intent(SignInActivity.this, SnowChatActivity.class);
//                            startActivity(intent);
//                            Toast.makeText(SignInActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //Sign in unsuccessful, display message to the user
//                            Log.d(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(SignInActivity.this, "Authentication Failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                        //hideProgressDialog();
//                    });
//    }



//    @Override
//    public void onClick(View view) {
//        int i = view.getId();
//        if (i == R.id.sign_in_btn) {
//            SignIn(input_email.getText().toString(), input_password.getText().toString());
//        } else if (i == R.id.sign_out_btn) {
//            signOut();
//        } else if (i == R.id.register_btn) {
//            goToCreateAccount();
//        } else if (i == R.id.reset_pwd_btn) {
//
//        }
//    }
//
//    private void signOut() {
//        auth.signOut();
//    }

    private boolean validateForm() {
        boolean valid = true;

        String email = input_email.getText().toString();
        //Check email field is not empty
        if (TextUtils.isEmpty(email)) {
            input_email.setError("Required");
            valid = false;
        } else {
            input_email.setError(null);
        }
        //Check if email contains @ in the email address
        if (!email.contains("@")) {
            input_email.setError("@ required");
            valid = false;
        }

        String password = input_password.getText().toString();
        //Check password field is not empty
        if (TextUtils.isEmpty(password)) {
            input_password.setError("Required");
            valid = false;
        } else {
            input_password.setError(null);
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(),
                    "Password too short, enter minimum 6 characters!!",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    public void readMeButton(View view) {
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
}
