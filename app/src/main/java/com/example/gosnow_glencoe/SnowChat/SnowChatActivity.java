package com.example.gosnow_glencoe.SnowChat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.gosnow_glencoe.MainActivity;
import com.example.gosnow_glencoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class SnowChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAccessorAdapter tabsAccessorAdapter;

    private DatabaseReference rootRef;
    private FirebaseAuth auth;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow_chat);

        toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Snow Chat");

        viewPager = findViewById(R.id.main_tabs_pager);
        tabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAccessorAdapter);

        tabLayout = findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);

        rootRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            sendUSerToLoginActivity();
        } else {
            confirmUserExistence();
            updateUserStatus("online");

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (currentUserID != null) {
            updateUserStatus("offline");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUserID != null) {
            updateUserStatus("offline");
        }
    }

    private void confirmUserExistence() {
        String currentUsersID = auth.getCurrentUser().getUid();
        rootRef.child("Users").child(currentUsersID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //checks for username
                if ((dataSnapshot.child("name").exists())) {
                    Toast.makeText(SnowChatActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendUserToSettingsActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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
            Intent intent = new Intent(SnowChatActivity.this, MainActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_create_group_option) {
            getNewGroup();
        }

        if (item.getItemId() == R.id.menu_signout_option) {
            updateUserStatus("offline");
            auth.signOut();
            Intent intent = new Intent(SnowChatActivity.this, SignInActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_setting_option) {
            sendUserToSettingsActivity();
        }

        if (item.getItemId() == R.id.menu_find_friends_option) {
            sendUserToFindFriendsActivity();
        }
        return true;
    }

    private void getNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SnowChatActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Group Name: ");

        final EditText groupNameField = new EditText(SnowChatActivity.this);
        groupNameField.setHint("e.g. Snow Conditions");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupName = groupNameField.getText().toString();

                if (TextUtils.isEmpty(groupName)) {
                    Toast.makeText(SnowChatActivity.this, "Please enter a Group Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    createNewGroup(groupName);
                }
            }
        });

    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    });

    builder.show();
    }

    private void createNewGroup(final String groupName) {
        rootRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SnowChatActivity.this, groupName + " is created successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendUserToSettingsActivity(){
        Intent intent = new Intent(SnowChatActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void sendUserToFindFriendsActivity() {
        Intent intent = new Intent(SnowChatActivity.this, FindFriendsActivity.class);
        startActivity(intent);
    }

    private void sendUSerToLoginActivity() {
        Intent intent = new Intent(SnowChatActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    private void updateUserStatus(String currentState) {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> userOnlineStateMap = new HashMap<>();
        userOnlineStateMap.put("time", saveCurrentTime);
        userOnlineStateMap.put("date", saveCurrentDate);
        userOnlineStateMap.put("state", currentState);

        currentUserID = auth.getCurrentUser().getUid();

        rootRef.child("Users").child(currentUserID).child("userStatus")
                .updateChildren(userOnlineStateMap);
    }


}
