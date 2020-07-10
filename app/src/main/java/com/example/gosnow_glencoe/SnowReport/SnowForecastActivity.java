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
        getSupportActionBar().setTitle("Snow Report");

        viewPager = findViewById(R.id.snow_forecast_main_tabs_pager);
        snowAccessorAdapter = new SnowAccessorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(snowAccessorAdapter);

        tabLayout = findViewById(R.id.snow_forecast_main_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.snow_report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_home_option) {
            Intent intent = new Intent(SnowForecastActivity.this, MainActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_snow_sports_option) {
            Intent intent = new Intent(SnowForecastActivity.this, SportActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_snow_chat_option) {
            Intent intent  =  new Intent(SnowForecastActivity.this, SnowChatActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_resort_option) {
            Intent intent = new Intent(SnowForecastActivity.this, ResortActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_business_option) {
            Intent intent = new Intent(SnowForecastActivity.this, BusinessActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_directions_option) {
            Intent intent = new Intent(SnowForecastActivity.this, DirectionsActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_ads_option) {
            Intent intent = new Intent(SnowForecastActivity.this, AdActivity.class);
            startActivity(intent);
        }
        return true;
    }
}