package com.example.gosnow_glencoe.SnowReport;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gosnow_glencoe.HttpRequest;
import com.example.gosnow_glencoe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//TODO create new adMob on google and implement into code with adID and ADUNIT
public class TomorrowsFragment extends Fragment {

    private View tomorrowsFragmentView;

    public TomorrowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GetWeatherJson().execute();
        new GetSnowInfoTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tomorrowsFragmentView =  inflater.inflate(R.layout.fragment_tomorrows, container, false);

        return tomorrowsFragmentView;


    }

    class GetWeatherJson extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return HttpCall.executeGet("https://api.weatherunlocked.com/api/resortforecast/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3");
        }

        @Override
        protected void onPostExecute(String jsonResult) {
            super.onPostExecute(jsonResult);

            TextView frz_lvl = tomorrowsFragmentView.findViewById(R.id.freeze_lvl_json);
            TextView visibility = (TextView) tomorrowsFragmentView.findViewById(R.id.visibility_json);
            TextView snow_falling = (TextView) tomorrowsFragmentView.findViewById(R.id.snow_falling_json);
            TextView tomorrows_access_weather = (TextView) tomorrowsFragmentView.findViewById(R.id.access_tomorrow_weather_json);
            TextView tomorrows_access_fresh = (TextView) tomorrowsFragmentView.findViewById(R.id.fresh_tomorrow_snow_json);
            TextView tomorrows_access_temp = (TextView) tomorrowsFragmentView.findViewById(R.id.temp_tomorrow_access_json);
            TextView tomorrows_access_feels_like = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrow_feels_like_access_json);
            TextView tomorrows_access_wnd_dir = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrow_wnd_direction_access_json);
            TextView tomorrows_access_wnd_spd = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrow_wnd_speed_access_json);
            TextView tomorrows_access_wnd_gst = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrow_wnd_gust_access_json);

            TextView tomorrows_top_weather = (TextView) tomorrowsFragmentView.findViewById(R.id.top_current_weather_json);
            TextView tomorrows_top_freshSnow = (TextView) tomorrowsFragmentView.findViewById(R.id.top_fresh_snow_json);
            TextView tomorrows_top_temp = (TextView) tomorrowsFragmentView.findViewById(R.id.top_temp_access_json);
            TextView tomorrows_top_feels_like = (TextView) tomorrowsFragmentView.findViewById(R.id.feels_like_top_json);
            TextView tomorrows_top_wnd_dir = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_direction_top_json);
            TextView tomorrows_top_wnd_spd = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_speed_top_json);
            TextView tomorrows_top_wnd_gst = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_gust_top_json);

            try {
                JSONObject jsonObj = new JSONObject(jsonResult);
                JSONArray forecast = jsonObj.getJSONArray("forecast");
                JSONObject freeze = forecast.getJSONObject(10);
                JSONObject vis = forecast.getJSONObject(10);
                JSONObject snow_f = forecast.getJSONObject(10);

                String frz = freeze.getString("frzglvl_ft") + "(ft)";
                String vizi = vis.getString("vis_mi") + "(mile)";
                String snowfalling = snow_f.getString("snow_mm") + "(mm)";

                frz_lvl.setText(frz);
                visibility.setText(vizi);
                snow_falling.setText(snowfalling);

                JSONObject baseJSON = forecast.getJSONObject(10);
                JSONObject accessJSON = baseJSON.getJSONObject("base");

                    String weather = accessJSON.getString("wx_desc");
                    String frshSnow = accessJSON.getString("freshsnow_cm") + "cm";
                    String temperature = accessJSON.getString("temp_c") + "°C";
                    String feelLike = accessJSON.getString("feelslike_c") + "°C";
                    String wDir = accessJSON.getString("winddir_compass");
                    String wSpd = accessJSON.getString("windspd_mph") + "mph";
                    String wGst = accessJSON.getString("windgst_mph") + "mph";

                    tomorrows_access_weather.setText(weather);
                    tomorrows_access_fresh.setText(frshSnow);
                    tomorrows_access_temp.setText(temperature);
                    tomorrows_access_feels_like.setText(feelLike);
                    tomorrows_access_wnd_dir.setText(wDir);
                    tomorrows_access_wnd_spd.setText(wSpd);
                    tomorrows_access_wnd_gst.setText(wGst);

                    JSONObject topJSON = forecast.getJSONObject(10);
                    JSONObject topMountJSON = topJSON.getJSONObject("upper");

                    String top_weather = topMountJSON.getString("wx_desc");
                    String top_freshSnow = topMountJSON.getString("freshsnow_cm") + "cm";
                    String top_temperature = topMountJSON.getString("temp_c") + "°C";
                    String top_feelsLike = topMountJSON.getString("feelslike_c") + "°C";
                    String top_windDirection = topMountJSON.getString("winddir_compass");
                    String top_windSpeed = topMountJSON.getString("windspd_mph") + "mph";
                    String top_windGust = topMountJSON.getString("windgst_mph") + "mph";

                    tomorrows_top_weather.setText(top_weather);
                    tomorrows_top_freshSnow.setText(top_freshSnow);
                    tomorrows_top_temp.setText(top_temperature);
                    tomorrows_top_feels_like.setText(top_feelsLike);
                    tomorrows_top_wnd_dir.setText(top_windDirection);
                    tomorrows_top_wnd_spd.setText(top_windSpeed);
                    tomorrows_top_wnd_gst.setText(top_windGust);

            } catch (JSONException e) {
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
            return HttpRequest.executeGet("https://api.weatherunlocked.com/api/snowreport/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TextView access_depth = (TextView) tomorrowsFragmentView.findViewById(R.id.access_tomorrow_snow_depth_json);
            TextView top_depth = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrows_top_snow_depth_json);
            TextView percentage_open = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrow_runs_percentage_json);
            TextView new_snow = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrow_new_snow_json);
            TextView last_snow = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrow_last_snow_json);
            TextView resort_conditions = (TextView) tomorrowsFragmentView.findViewById(R.id.tomorrow_resort_condition_json);

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

