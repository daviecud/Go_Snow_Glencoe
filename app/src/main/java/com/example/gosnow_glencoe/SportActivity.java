package com.example.gosnow_glencoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SportActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        toolbar = findViewById(R.id.snow_sport_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Snow Sports");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.snow_sports_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_home_option) {
            Intent intent = new Intent(SportActivity.this, MainActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_snow_report_option) {
            Intent intent = new Intent(SportActivity.this, SnowReportActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_resort_option) {
            Intent intent = new Intent(SportActivity.this, ResortActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_business_option) {
            Intent intent = new Intent(SportActivity.this, BusinessActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_directions_option) {
            Intent intent = new Intent(SportActivity.this, DirectionsActivity.class);
            startActivity(intent);
        }


        return true;
    }

}
