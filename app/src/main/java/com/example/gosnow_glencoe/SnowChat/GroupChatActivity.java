package com.example.gosnow_glencoe.SnowChat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gosnow_glencoe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton sendMessageButton;
    private EditText userMessageInput;
    private ScrollView scrollView;
    private TextView displayMessage;

    private FirebaseAuth auth;
    private DatabaseReference userRef, groupNameReference, groupMessageKeyReference;

    private String chosenGroupName, currentUserID, currentUserName, currentDate, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        chosenGroupName = getIntent().getExtras().get("groupName").toString();
        Toast.makeText(GroupChatActivity.this, chosenGroupName, Toast.LENGTH_SHORT).show();

        auth = FirebaseAuth.getInstance();
        currentUserID = auth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        groupNameReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(chosenGroupName);


        InitializeFields();

        GetUserInformation();

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMessageToDatabase();
                userMessageInput.setText("");

                scrollView.fullScroll(scrollView.FOCUS_DOWN);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        groupNameReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    DisplayGroupMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    DisplayGroupMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeFields() {

        toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(chosenGroupName);

        sendMessageButton = findViewById(R.id.send_group_chat_button);
        userMessageInput = findViewById(R.id.group_chat_send);
        displayMessage = findViewById(R.id.group_chat_text_display);
        scrollView = findViewById(R.id.chat_scroll_view);
    }

    private void GetUserInformation() {

        userRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentUserName = dataSnapshot.child("name").getValue().toString();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void SaveMessageToDatabase() {
        String messageKey = groupNameReference.push().getKey();
        String message = userMessageInput.getText().toString();

        if (TextUtils.isEmpty(message)) {

            Toast.makeText(GroupChatActivity.this, "Please enter a message....", Toast.LENGTH_SHORT).show();

        } else {
            //get and set current date
            Calendar calendarDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = currentDateFormat.format(calendarDate.getTime());

            //get and set current time
            Calendar calendarTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            currentTime = currentTimeFormat.format(calendarTime.getTime());

            HashMap<String, Object> groupKey = new HashMap<>();
            groupNameReference.updateChildren(groupKey);

            groupMessageKeyReference = groupNameReference.child(messageKey);

            HashMap<String, Object> messageMap = new HashMap<>();
            messageMap.put("name", currentUserName);
            messageMap.put("message", message);
            messageMap.put("date", currentDate);
            messageMap.put("time", currentTime);

            groupMessageKeyReference.updateChildren(messageMap);


        }

    }

    private void DisplayGroupMessages(DataSnapshot dataSnapshot) {

    Iterator iterator = dataSnapshot.getChildren().iterator();

    while (iterator.hasNext()) {
        String groupChatDate = (String) ((DataSnapshot)iterator.next()).getValue();
        String groupChatMessage = (String) ((DataSnapshot)iterator.next()).getValue();
        String groupChatName = (String) ((DataSnapshot)iterator.next()).getValue();
        String groupChatTime = (String) ((DataSnapshot)iterator.next()).getValue();

        displayMessage.append(groupChatName + ":\n" + groupChatMessage + "\n" + groupChatTime + "     " + groupChatDate + "\n\n\n");

        scrollView.fullScroll(scrollView.FOCUS_DOWN);
    }

    }

}
