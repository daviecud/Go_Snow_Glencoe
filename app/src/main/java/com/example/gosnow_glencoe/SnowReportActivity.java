package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SnowReportActivity extends AppCompatActivity {

    TextView access_snow, top_snow, new_snow, last_snow, report_date, report_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow_report);

        access_snow = findViewById(R.id.accessDepthJson);
        top_snow = findViewById(R.id.topDepthJson);
        new_snow = findViewById(R.id.newSnowJson);
        report_date = findViewById(R.id.reportDateJson);
        report_time = findViewById(R.id.reportTimeJson);
    }


}
