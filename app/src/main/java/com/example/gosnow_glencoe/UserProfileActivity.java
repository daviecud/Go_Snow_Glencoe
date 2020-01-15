package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    private String receiverUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        receiverUserID = getIntent().getExtras().get("clicked_user_id").toString();

        Toast.makeText(this, "User ID: " + receiverUserID, Toast.LENGTH_SHORT).show();
    }
}
