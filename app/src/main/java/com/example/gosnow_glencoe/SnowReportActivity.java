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

    TextView access_snow, top_snow, new_snow, last_snow, report_date, report_time, freezeLvl, rainfall, snowfall,
            visibility, perctOpen, conditions, weatherDesc, freshSnow, temp, feelsLike, windDir, windSpeed, windGust;

    /*TODO get and set JSon results for upper slopes, fix layouts of details and add images to details,
       add a cartoon mountain as background, figure out how to use scrollview for activity,
       add scroll to left to get next days weather forecast, add snowfall to activity if weather conditions has snow */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow_report);


        InitializeFields();

        new GetWeatherInfoTask().execute();
        new GetSnowInfoTask().execute();
    }

    private void InitializeFields() {

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
        last_snow = findViewById(R.id.lastSnowJson);
        conditions = findViewById(R.id.resortConditionsJson);

        //Base Details Views
        weatherDesc = findViewById(R.id.weatherJson);
        freshSnow = findViewById(R.id.fshSnowJson);
        temp = findViewById(R.id.tempJson);
        feelsLike = findViewById(R.id.feelLikeJson);
        windDir = findViewById(R.id.wndDirJson);
        windSpeed = findViewById(R.id.wndSpdJson);
        windGust = findViewById(R.id.wndGstJson);

    }

    class GetWeatherInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Show progress bar, Make the main design GONE */
//            findViewById(R.id.loader).setVisibility(View.VISIBLE);
//            findViewById(R.id.snowContainer).setVisibility(View.GONE);
//            findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... args) {

            return url;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray forecast = jsonObj.getJSONArray("forecast");
                JSONObject freeze = forecast.getJSONObject(0);
                JSONObject vis = forecast.getJSONObject(0);
                JSONObject rpt_date = forecast.getJSONObject(0);
                JSONObject rpt_time = forecast.getJSONObject(0);
                JSONObject rain_f = forecast.getJSONObject(0);
                JSONObject snow_f = forecast.getJSONObject(0);


                String frz = freeze.getString("frzglvl_ft") + "(ft)";
                String vizi = vis.getString("vis_mi") + "(mile)";
                String date = rpt_date.getString("date");
                String time = rpt_time.getString("time");
                String rainfalling = rain_f.getString("rain_mm") + "(mm)";
                String snowfalling = snow_f.getString("snow_mm") + "(mm)";


                freezeLvl.setText(frz);
                visibility.setText(vizi);
                report_date.setText(date);
                report_time.setText(time);
                rainfall.setText(rainfalling);
                snowfall.setText(snowfalling);

                //JSON for Base details
                if (forecast.length() > 0) {
                    for (int x = 0; x < forecast.length(); x++) {
                        JSONObject jsoObject = forecast.getJSONObject(x);

                        if (jsoObject.has("base")) {
                            JSONObject jsonBase = jsoObject.getJSONObject("base");
                            String weath = jsonBase.getString("wx_desc");
                            String frshSnow = jsonBase.getString("freshsnow_cm") + "cm";
                            String temperature = jsonBase.getString("temp_c") + "°C";
                            String feelLike = jsonBase.getString("feelslike_c") + "°C";
                            String wDir = jsonBase.getString("winddir_compass");
                            String wSpd = jsonBase.getString("windspd_mph") + "mph";
                            String wGst = jsonBase.getString("windgst_mph") + "mph";

                            weatherDesc.setText(weath);
                            freshSnow.setText(frshSnow);
                            temp.setText(temperature);
                            feelsLike.setText(feelLike);
                            windDir.setText(wDir);
                            windSpeed.setText(wSpd);
                            windGust.setText(wGst);
                        }
                        if (jsoObject.has("upper")) {
                            JSONObject jsonUpper = jsoObject.getJSONObject("upper");
                        }
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetSnowInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            return snowUrl;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //get json, get to string, set to txtfield
            try {
                JSONObject jObject = new JSONObject(s);


                String top = jObject.getString("uppersnow_cm") + "cm";
                String access = jObject.getString("lowersnow_cm") + "cm";
                String conds = jObject.getString("conditions");
                String lstSnow = jObject.getString("lastsnow");
                String newSnow = jObject.getString("newsnow_cm")  + "cm";
                String pctOpen = jObject.getString("pctopen") + "%";

                top_snow.setText(top);
                access_snow.setText(access);
                conditions.setText(conds);
                last_snow.setText(lstSnow);
                new_snow.setText(newSnow);
                perctOpen.setText(pctOpen);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
