package com.example.gosnow_glencoe.SnowChat;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
import com.example.gosnow_glencoe.MainActivity;
import com.example.gosnow_glencoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Sign_UpActivity extends BaseActivity {


    private static final String TAG = "EmailPassword";

    private Toolbar toolbar;
    private EditText emailField;
    private EditText passwordField;
    private Button createAccBtn, signInBtn;
    private ProgressDialog loadingBar;

    private FirebaseAuth auth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");

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

                CreateAccount(email, password);
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

    private void CreateAccount(String email, String password) {
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
                                String phoneDeviceId = FirebaseInstanceId.getInstance().getToken();

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