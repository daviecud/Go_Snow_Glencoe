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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TodaysFragment extends Fragment {

    private TextView percentage_open, new_snow, frz_lvl, last_snow, visibility, snow_falling,
                    access_weather, access_depth, access_fresh, access_temp, access_feels_like,
                    access_wnd_dir, access_wnd_spd, access_wnd_gst, top_weather, top_depth, top_fresh,
                    top_temp, top_feels_like, top_wnd_dir, top_wnd_spd, top_wnd_gst, resort_conditions;
    private View todaysFragmentView;

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
    * Json results will be set to the appropriate TextView field declared above as a String.
    * catch block will handle exceptions if they occur.
    * */
    class GetWeatherJson extends AsyncTask<String, Void, String> {

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

            TextView access_weather = (TextView) todaysFragmentView.findViewById(R.id.access_current_weather_json);

            TextView access_fresh= (TextView) todaysFragmentView.findViewById(R.id.fresh_snow_json);
            TextView access_temp = (TextView) todaysFragmentView.findViewById(R.id.temp_access_json);
            TextView access_feels_like = (TextView) todaysFragmentView.findViewById(R.id.feels_like_access_json);
            TextView access_wnd_dir = (TextView) todaysFragmentView.findViewById(R.id.wnd_direction_access_json);
            TextView access_wnd_spd = (TextView) todaysFragmentView.findViewById(R.id.wnd_speed_access_json);
            TextView access_wnd_gst = (TextView) todaysFragmentView.findViewById(R.id.wnd_gust_access_json);

            TextView top_weather = (TextView) todaysFragmentView.findViewById(R.id.top_current_weather_json);
            TextView top_fresh = (TextView) todaysFragmentView.findViewById(R.id.top_fresh_snow_json);
            TextView top_temp = (TextView) todaysFragmentView.findViewById(R.id.top_temp_access_json);
            TextView top_feels_like = (TextView) todaysFragmentView.findViewById(R.id.feels_like_top_json);
            TextView top_wnd_dir = (TextView) todaysFragmentView.findViewById(R.id.wnd_direction_top_json);
            TextView top_wnd_spd = (TextView) todaysFragmentView.findViewById(R.id.wnd_speed_top_json);
            TextView top_wnd_gst = (TextView) todaysFragmentView.findViewById(R.id.wnd_gust_top_json);

            try {
                JSONObject jsonObj = new JSONObject(jsonResult);
                JSONArray forecast = jsonObj.getJSONArray("forecast");
                JSONObject freeze = forecast.getJSONObject(0);
                JSONObject vis = forecast.getJSONObject(0);
                JSONObject snow_f = forecast.getJSONObject(0);

                String frz = freeze.getString("frzglvl_ft") + "(ft)";
                String vizi = vis.getString("vis_mi") + "(mile)";
                String snowfalling = snow_f.getString("snow_mm") + "(mm)";

                frz_lvl.setText(frz);
                visibility.setText(vizi);
                snow_falling.setText(snowfalling);

                //JSON for Base details
                if (forecast.length() > 0) {
                    for (int x = 0; x < forecast.length(); x++) {
                        JSONObject jsoObject = forecast.getJSONObject(x);

                        if (jsoObject.has("base")) {
                            JSONObject jsonBase = jsoObject.getJSONObject("base");
                            String weather = jsonBase.getString("wx_desc");
                            String frshSnow = jsonBase.getString("freshsnow_cm") + "cm";
                            String temperature = jsonBase.getString("temp_c") + "°C";
                            String feelLike = jsonBase.getString("feelslike_c") + "°C";
                            String wDir = jsonBase.getString("winddir_compass");
                            String wSpd = jsonBase.getString("windspd_mph") + "mph";
                            String wGst = jsonBase.getString("windgst_mph") + "mph";

                            access_weather.setText(weather);
                            access_fresh.setText(frshSnow);
                            access_temp.setText(temperature);
                            access_feels_like.setText(feelLike);
                            access_wnd_dir.setText(wDir);
                            access_wnd_spd.setText(wSpd);
                            access_wnd_gst.setText(wGst);
                        }
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

            TextView access_depth = (TextView) todaysFragmentView.findViewById(R.id.access_snow_depth_json);
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





