package com.example.gosnow_glencoe.SnowReport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gosnow_glencoe.AdActivity;
import com.example.gosnow_glencoe.BusinessActivity;
import com.example.gosnow_glencoe.DirectionsActivity;
import com.example.gosnow_glencoe.MainActivity;
import com.example.gosnow_glencoe.R;

import com.example.gosnow_glencoe.ResortActivity;
import com.example.gosnow_glencoe.SnowChat.SnowChatActivity;
import com.example.gosnow_glencoe.SportActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class SnowForecastActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SnowAccessorAdapter snowAccessorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow_forecast);

        toolbar = findViewById(R.id.snow_report_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Weather Conditions");

        viewPager = findViewById(R.id.snow_forecast_main_tabs_pager);
        snowAccessorAdapter = new SnowAccessorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(snowAccessorAdapter);

        tabLayout = findViewById(R.id.snow_forecast_main_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //inflate options overflow menu
        getMenuInflater().inflate(R.menu.snow_report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.bottom_nav_home) {
            Intent intent = new Intent(SnowForecastActivity.this, MainActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.bottom_nav_sport) {
            Intent intent = new Intent(SnowForecastActivity.this, SportActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.bottom_nav_snowchat) {
            Intent intent  =  new Intent(SnowForecastActivity.this, SnowChatActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.bottom_nav_business) {
            Intent intent = new Intent(SnowForecastActivity.this, BusinessActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.bottom_nav_resort) {
            Intent intent = new Intent(SnowForecastActivity.this, ResortActivity.class);
            startActivity(intent);
        }

        return true;
    }
}