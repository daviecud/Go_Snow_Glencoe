package com.example.gosnow_glencoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private EditText input_username, input_email, input_password;
    private FirebaseAuth auth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        //Views
        input_username = findViewById(R.id.username);
        input_email = findViewById(R.id.email);
        input_password = findViewById(R.id.password);

        //Buttons
        findViewById(R.id.sign_in_btn).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);
        findViewById(R.id.reset_pwd_btn).setOnClickListener(this);
        findViewById(R.id.verify_email_btn).setOnClickListener(this);

    }

    //On Activity start check user
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
//        showProgressDialog();

        //Start sign in with email
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign in successfully will take user to SnowChatActivity, and display welcome message
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(SignInActivity.this, SnowChatActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignInActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        } else {
                            //Sign in unsuccessful, display message to the user
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                        //hideProgressDialog();
                    });
    }

    private void goToCreateAccount(){
        Intent intent = new Intent(this, Sign_UpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.sign_in_btn) {
            signIn(input_email.getText().toString(), input_password.getText().toString());
        } else if (i == R.id.sign_out_btn) {
            signOut();
        } else if (i == R.id.register_btn) {
            goToCreateAccount();
        } else if (i == R.id.reset_pwd_btn) {

        }
    }

    private void signOut() {
        auth.signOut();
    }

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
}
