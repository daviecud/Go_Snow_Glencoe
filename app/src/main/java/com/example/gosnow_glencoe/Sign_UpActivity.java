package com.example.gosnow_glencoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_UpActivity extends BaseActivity  {


    private static final String TAG = "EmailPassword";

    private TextView statusTextView;
    private TextView detailTextView;
    private EditText emailField;
    private EditText passwordField;
    private EditText usernameField;
    private Button createAccBtn, signInBtn;
    private ProgressDialog loadingBar;

    private FirebaseAuth auth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        loadingBar = new ProgressDialog(this);
        //Views
        statusTextView = findViewById(R.id.status);
        detailTextView = findViewById(R.id.detail);
        usernameField = findViewById(R.id.username);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        //Buttons

        createAccBtn = findViewById(R.id.create_acc_btn);
        signInBtn = findViewById(R.id.sign_in_btn);
//        findViewById(R.id.create_acc_btn).setOnClickListener(this);
//        findViewById(R.id.sign_in_btn).setOnClickListener(this);
//        findViewById(R.id.sign_out_btn).setOnClickListener(this);

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
                String username = usernameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                CreateAccount(username, email, password);
            }
        });

        //Initialize Authorisation
        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
    }

    private void CreateAccount(String username, String email, String password) {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(Sign_UpActivity.this, "Please enter a username!",
                    Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Sign_UpActivity.this, "Please enter an email address!",
                    Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(Sign_UpActivity.this, "Please enter a password!",
                    Toast.LENGTH_LONG).show();
        }
        else {

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
                                rootRef.child("Users").child(currentUserID).setValue("");
                                Intent intent = new Intent(Sign_UpActivity.this, SnowChatActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(Sign_UpActivity.this, "Error occurred try again",
                                        Toast.LENGTH_LONG).show();
                            }
                            loadingBar.dismiss();
                        }

                    });
        }
    }

//    //On Activity start check user
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = auth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//
//    private void updateUI(FirebaseUser user) {
//        //hideProgressDialog()
//        if (user != null) {
//            statusTextView.setText(getString(R.string.emailpassword_status_fmt, user.getEmail(), user.isEmailVerified()));
//            detailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            //findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
//            //findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
//            //findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
//
//            //findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());
//        } else {
//            statusTextView.setText(R.string.signed_out);
//            detailTextView.setText(null);
//
//            //findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
//            //findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
//            //findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        int i = view.getId();
//        if (i == R.id.create_acc_btn) {
//            createAccount(emailField.getText().toString(), passwordField.getText().toString());
//        } else if (i == R.id.sign_in_btn) {
//            signIn(emailField.getText().toString(), passwordField.getText().toString());
//        } else if (i == R.id.sign_out_btn) {
//            signOut();
//        } else if (i == R.id.verify_email_btn) {
//            sendEmailVerification();
//        }
//    }
//
//    private void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }
//
//        //showProgressDialog();
//
//        //Start create user with email/password
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //Sign in successful, update UI with users information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = auth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            //Sign in unsuccessful, display message to the user
//                            Log.d(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(Sign_UpActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                    }     //hideProgressDialog()
//                });
//    }
//
//    private void sendEmailVerification() {
//        //Disable emailVerification button
//        findViewById(R.id.verify_email_btn).setEnabled(false);
//
//        //Send verification email, if successful display message to user,
//        //else task unsuccessful else will send exception message to Log and to user
//        final FirebaseUser user = auth.getCurrentUser();
//        user.sendEmailVerification()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        //Re-enable emailVerification button
//                        findViewById(R.id.verify_email_btn).setEnabled(true);
//
//                        if (task.isSuccessful()) {
//                            Toast.makeText(Sign_UpActivity.this, "Verification email sent to " + user.getEmail(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.e(TAG, "sendEmailVerification", task.getException());
//                            Toast.makeText(Sign_UpActivity.this,
//                                    "Failed to send verification email.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    private void signIn(String email, String password) {
//        Log.d(TAG, "signIn:" + email);
//        if (!validateForm()) {
//            return;
//        }
//
////        showProgressDialog();
//
//        //Start sign in with email
//        auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //Sign in successful, update UI with users information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = auth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            //Sign in unsuccessful, display message to the user
//                            Log.d(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(Sign_UpActivity.this, "Authentication Failed",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        if (!task.isSuccessful()) {
//                            statusTextView.setText(R.string.auth_failed);
//                        }
//                        //hideProgressDialog();
//                    }
//                });
//    }
//
//
//    private void signOut() {
//        auth.signOut();
//    }
//
//    private boolean validateForm() {
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


}
