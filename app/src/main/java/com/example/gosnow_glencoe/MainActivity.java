package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView topMount, midMount, baseMount, current_condition, last_snowfall, new_snow_cm, wndspd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topMount = findViewById(R.id.topMountJson);
        midMount = findViewById(R.id.midMountJson);
        baseMount = findViewById(R.id.baseMountJson);
        current_condition = findViewById(R.id.currentWeatherText);
        new_snow_cm = findViewById(R.id.newSnowJson);
        last_snowfall = findViewById(R.id.lastSnowJson);
        wndspd = findViewById(R.id.windSpeedJson);

        getSnow();
        getResortForecastInfo();
    }

    protected void getSnow() {
        String url = "https://api.weatherunlocked.com/api/snowreport/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3";
//Try another snow report API

        JsonObjectRequest snow = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    String upper = response.getString("uppersnow_cm");
                    String lower = response.getString("lowersnow_cm");
                    String new_snow = response.getString("newsnow_cm");
                    String last_snow = response.getString("lastsnow");
                    String conditions = response.getString("conditions");
                    String name = response.getString("resortname");

                    topMount.setText(upper);
                    baseMount.setText(lower);
                    current_condition.setText(conditions);
                    new_snow_cm.setText(new_snow);
                    last_snowfall.setText(last_snow);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(snow);
    }

    protected void getResortForecastInfo(){
        //String url = "https://api.darksky.net/forecast/27a87fa552b2a741f881b3ee639b994a/56.6325,-4.8279";
        String url = "https://api.weatherunlocked.com/api/resortforecast/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3";

        JsonObjectRequest weather = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String windSpeed = response.getString("resort.forecast.timeframe.base.windspd_mph");

                    wndspd.setText(windSpeed);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(weather);
    };

}



