package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SnowReportActivity extends AppCompatActivity {

    TextView access_snow, top_snow, new_snow, last_snow, report_date, report_time, freezeLvl, rainfall, snowfall, visibility, perctOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow_report);

        access_snow = findViewById(R.id.accessDepthJson);
        top_snow = findViewById(R.id.topDepthJson);
        new_snow = findViewById(R.id.newSnowJson);
        report_date = findViewById(R.id.reportDateJson);
        report_time = findViewById(R.id.reportTimeJson);
        freezeLvl = findViewById(R.id.freezelvlJson);
        rainfall = findViewById(R.id.rainFallingJson);
        snowfall = findViewById(R.id.snowFallingJson);
        visibility = findViewById(R.id.visibiltyJson);
        perctOpen = findViewById(R.id.percentOpenJson);

        new getWeatherInfoTask().execute();
    }

     class getWeatherInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Show progress bar, Make the main design GONE */
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.snowContainer).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... args) {
            //TODO create a httpRequest class
            String url = HttpRequest.executeGet( "https://api.weatherunlocked.com/api/resortforecast/1398?" );
            return url;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray forecast = jsonObj.getJSONArray("forecast");
                JSONObject base = forecast.getJSONObject(0);


            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
