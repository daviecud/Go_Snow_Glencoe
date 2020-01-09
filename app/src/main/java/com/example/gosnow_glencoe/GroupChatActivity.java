package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton sendMessageButton;
    private EditText userMessageInput;
    private ScrollView scrollView;
    private TextView displayMessage;

    private String chosenGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        chosenGroupName = getIntent().getExtras().get("groupName").toString();
        Toast.makeText(GroupChatActivity.this, chosenGroupName, Toast.LENGTH_SHORT).show();

        InitializeFields();
    }

    private void InitializeFields() {

        toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Group Name");

        sendMessageButton = findViewById(R.id.send_group_chat_button);
        userMessageInput = findViewById(R.id.group_chat_send);
        displayMessage = findViewById(R.id.group_chat_text_display);
        scrollView = findViewById(R.id.chat_scroll_view);
    }


}
