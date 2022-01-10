package com.example.gosnow_glencoe.SnowReport;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.gosnow_glencoe.HttpRequest;
import com.example.gosnow_glencoe.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Objects;


public class TodaysFragment extends Fragment {

    private View todaysFragmentView;
    private AdView adView, adview1;
    private AdRequest adRequest, adRequest2;


    public TodaysFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeFields();
        new GetWeatherJson().execute();
        new GetSnowInfoTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        todaysFragmentView = inflater.inflate(R.layout.fragment_todays, container, false);

        adView = (AdView) todaysFragmentView.findViewById(R.id.adview);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adview1 = (AdView) todaysFragmentView.findViewById(R.id.ad_below_top_report);
        adRequest2 = new AdRequest.Builder().build();
        adview1.loadAd(adRequest2);

        return todaysFragmentView;
    }

    private void initializeFields() {
//        TextView percentage_open = (TextView) todaysFragmentView.findViewById(R.id.runs_percentage_json);
//        new_snow.findViewById(R.id.new_snow_json);
//        frz_lvl.findViewById(R.id.freeze_lvl_json);
//        last_snow.findViewById(R.id.last_snow_json);
//        visibility.findViewById(R.id.visibility_json);
//        snow_falling.findViewById(R.id.snow_falling_json);
//        resort_conditions.findViewById(R.id.resort_condition_json);
//        //access
//        access_weather.findViewById(R.id.access_current_weather_json);
//
//        //top
//
    }

    /*Nested class
    * Retrieve Json details. Method doInBackground() will return http request using HttpCall.java.
    * onPostExecute() method uses try block to execute Json results.
    * Json results will be set to the appropriate TextView field
    * catch block will handle exceptions if they occur.
    * */
    private class GetWeatherJson extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return HttpCall.executeGet("https://api.weatherunlocked.com/api/resortforecast/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3");
        }

        @Override
        protected void onPostExecute(String jsonResult) {
            super.onPostExecute(jsonResult);

            TextView frz_lvl = (TextView) todaysFragmentView.findViewById(R.id.freeze_lvl_json);
            TextView visibility = (TextView) todaysFragmentView.findViewById(R.id.visibility_json);
            TextView snow_falling = (TextView) todaysFragmentView.findViewById(R.id.snow_falling_json);
            TextView rain_falling = (TextView) todaysFragmentView.findViewById(R.id.rain_falling_json);
            //Declare Access weather TextView
            TextView access_weather = (TextView) todaysFragmentView.findViewById(R.id.access_current_weather_json);
            TextView access_fresh= (TextView) todaysFragmentView.findViewById(R.id.fresh_snow_json);
            TextView access_temp = (TextView) todaysFragmentView.findViewById(R.id.temp_access_json);
            TextView access_feels_like = (TextView) todaysFragmentView.findViewById(R.id.feels_like_access_json);
            TextView access_wnd_dir = (TextView) todaysFragmentView.findViewById(R.id.wnd_direction_access_json);
            TextView access_wnd_spd = (TextView) todaysFragmentView.findViewById(R.id.wnd_speed_access_json);
            TextView access_wnd_gst = (TextView) todaysFragmentView.findViewById(R.id.wnd_gust_access_json);
            ImageView access_wthr_img = (ImageView) todaysFragmentView.findViewById(R.id.access_weather_image_json);
            //Declare top weather TextView
            TextView top_weather = (TextView) todaysFragmentView.findViewById(R.id.top_current_weather_json);
            TextView top_fresh = (TextView) todaysFragmentView.findViewById(R.id.top_fresh_snow_json);
            TextView top_temp = (TextView) todaysFragmentView.findViewById(R.id.top_temp_access_json);
            TextView top_feels_like = (TextView) todaysFragmentView.findViewById(R.id.feels_like_top_json);
            TextView top_wnd_dir = (TextView) todaysFragmentView.findViewById(R.id.wnd_direction_top_json);
            TextView top_wnd_spd = (TextView) todaysFragmentView.findViewById(R.id.wnd_speed_top_json);
            TextView top_wnd_gst = (TextView) todaysFragmentView.findViewById(R.id.wnd_gust_top_json);
            ImageView top_wthr_img = (ImageView) todaysFragmentView.findViewById(R.id.top_weather_image_json);

            try {
                JSONObject jsonObj = new JSONObject(jsonResult);
                JSONArray forecast = jsonObj.getJSONArray("forecast");
                JSONObject freeze = forecast.getJSONObject(0);
                JSONObject vis = forecast.getJSONObject(0);
                JSONObject snow_f = forecast.getJSONObject(0);
                JSONObject rain_f = forecast.getJSONObject(0);

                String frz = freeze.getString("frzglvl_ft") + "ft";
                String vizi = vis.getString("vis_mi") + "mile";
                String snowfalling = snow_f.getString("snow_mm") + "mm";
                String rainfalling = rain_f.getString("rain_mm") + "mm";

                frz_lvl.setText(frz);
                visibility.setText(vizi);
                snow_falling.setText(snowfalling);
                rain_falling.setText(rainfalling);

                if (forecast.length() > 0) {
                    for (int x = 0; x < forecast.length(); x++) {
                        JSONObject jsoObject = forecast.getJSONObject(x);

                        //TODO get weather symbol and code for base to set to image
                        //JSON for Base details
                        if (jsoObject.has("base")) {
                            JSONObject jsonBase = jsoObject.getJSONObject("base");
                            String weather = jsonBase.getString("wx_desc");

                            String frshSnow = jsonBase.getString("freshsnow_cm") + "cm";
                            String temperature = jsonBase.getString("temp_c") + "°C";
                            String feelLike = jsonBase.getString("feelslike_c") + "°C";
                            String wDir = jsonBase.getString("winddir_compass");
                            String wSpd = jsonBase.getString("windspd_mph") + "mph";
                            String wGst = jsonBase.getString("windgst_mph") + "mph";

                            if (weather.contains("rain") || weather.contains("drizzle")) {
                                access_wthr_img.setImageResource(R.drawable.rain);
                            } else if (weather.contains("Clear") || weather.contains("sunny")) {
                                access_wthr_img.setImageResource(R.drawable.sunny);
                            } else if (weather.contains("thunder")) {
                                access_wthr_img.setImageResource(R.drawable.thunder);
                            } else if (weather.contains("Mist")) {
                                access_wthr_img.setImageResource(R.drawable.mist);
                            } else if (weather.contains("Overcast")) {
                                access_wthr_img.setImageResource(R.drawable.overcast);
                            } else if (weather.contains("snow") || weather.contains(("Snow"))) {
                                access_wthr_img.setImageResource(R.drawable.blizzard);
                            }
                            else {
                                access_wthr_img.setImageResource(R.drawable.feels_like);
                            }

                            access_weather.setText(weather);
                            access_fresh.setText(frshSnow);
                            access_temp.setText(temperature);
                            access_feels_like.setText(feelLike);
                            access_wnd_dir.setText(wDir);
                            access_wnd_spd.setText(wSpd);
                            access_wnd_gst.setText(wGst);
                        }

                        //TODO get weather icon and weather code to set to image
                        //Json for Top Details
                        if (jsoObject.has("upper")) {
                            JSONObject jsonUpper = jsoObject.getJSONObject("upper");
                            String top_weath = jsonUpper.getString("wx_desc");
                            String top_frshSnow = jsonUpper.getString("freshsnow_cm") + "cm";
                            String top_temperature = jsonUpper.getString("temp_c") + "°C";
                            String top_feelsLike = jsonUpper.getString("feelslike_c") + "°C";
                            String top_winDir = jsonUpper.getString("winddir_compass");
                            String top_winSpd = jsonUpper.getString("windspd_mph") + "mph";
                            String top_winGst = jsonUpper.getString("windgst_mph") + "mph";

                            //if else statement that checks if weather description contains a certain weather condition
                            //if condition is true then sets specific image to weather image in top mountain weather section
                            if (top_weath.contains("rain") || top_weath.contains("drizzle")) {
                                top_wthr_img.setImageResource(R.drawable.rain);
                            } else if (top_weath.contains("Clear") || top_weath.contains("sunny")) {
                                top_wthr_img.setImageResource(R.drawable.sunny);
                            } else if (top_weath.contains("thunder")) {
                                top_wthr_img.setImageResource(R.drawable.thunder);
                            } else if (top_weath.contains("Mist")) {
                                top_wthr_img.setImageResource(R.drawable.mist);
                            } else if (top_weath.contains("Overcast")) {
                                top_wthr_img.setImageResource(R.drawable.overcast);
                            } else if (top_weath.contains("snow") || top_weath.contains(("Snow"))) {
                                top_wthr_img.setImageResource(R.drawable.blizzard);
                            } else {
                                top_wthr_img.setImageResource(R.drawable.feels_like);
                            }

                            top_weather.setText(top_weath);
                            top_fresh.setText(top_frshSnow);
                            top_temp.setText(top_temperature);
                            top_feels_like.setText(top_feelsLike);
                            top_wnd_dir.setText(top_winDir);
                            top_wnd_spd.setText(top_winSpd);
                            top_wnd_gst.setText(top_winGst);
                        }
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetSnowInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            return HttpRequest.executeGet("https://api.weatherunlocked.com/api/snowreport/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TextView access_depth = todaysFragmentView.findViewById(R.id.access_snow_depth_json);
            TextView top_depth = (TextView) todaysFragmentView.findViewById(R.id.top_snow_depth_json);
            TextView percentage_open = (TextView) todaysFragmentView.findViewById(R.id.runs_percentage_json);
            TextView new_snow = (TextView) todaysFragmentView.findViewById(R.id.new_snow_json);
            TextView last_snow = (TextView) todaysFragmentView.findViewById(R.id.last_snow_json);
            TextView resort_conditions = (TextView) todaysFragmentView.findViewById(R.id.resort_condition_json);

            //get json, get to string, set to txtfield
            try {
                JSONObject jObject = new JSONObject(s);

                String top = jObject.getString("uppersnow_cm") + "cm";
                String access = jObject.getString("lowersnow_cm") + "cm";
                String conditions = jObject.getString("conditions");
                String lstSnow = jObject.getString("lastsnow");
                String newSnow = jObject.getString("newsnow_cm")  + "cm";
                String pctOpen = jObject.getString("pctopen") + "%";

                top_depth.setText(top);
                access_depth.setText(access);
                resort_conditions.setText(conditions);
                last_snow.setText(lstSnow);
                new_snow.setText(newSnow);
                percentage_open.setText(pctOpen);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}





