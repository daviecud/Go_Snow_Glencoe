package com.example.gosnow_glencoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.gosnow_glencoe.SnowChat.SnowChatActivity;
import com.example.gosnow_glencoe.SnowReport.SnowForecastActivity;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class ResortActivity extends AppCompatActivity {

    Toolbar toolbar;
    private NavigationBarView navBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort);
//TODO fix navBarView being null
        navBarView = findViewById(R.id.resort_bottom_nav);
        navBarView.setOnItemSelectedListener(navBarMethod);
    }

    private NavigationBarView.OnItemSelectedListener navBarMethod= item -> {

        switch (item.getItemId()) {
            case R.id.resort_bottom_nav_home:
                Intent home_intent = new Intent(ResortActivity.this, MainActivity.class);
                startActivity(home_intent);
                break;
            case R.id.resort_bottom_nav_snowreport:
                Intent report_intent = new Intent(ResortActivity.this, SnowForecastActivity.class);
                startActivity(report_intent);
                break;
            case R.id.resort_bottom_nav_snowchat:
                Intent chat_intent = new Intent(ResortActivity.this, SnowChatActivity.class);
                startActivity(chat_intent);
                break;
            case R.id.resort_bottom_nav_business:
                Intent bus_intent = new Intent(ResortActivity.this, BusinessActivity.class);
                startActivity(bus_intent);
                break;
            case R.id.resort_bottom_nav_sports:
                Intent sports_intent = new Intent(ResortActivity.this, SportActivity.class);
                startActivity(sports_intent);
                break;
        }
        return true;
    };
}
