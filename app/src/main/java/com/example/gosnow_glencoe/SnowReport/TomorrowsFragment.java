package com.example.gosnow_glencoe.SnowReport;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gosnow_glencoe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TomorrowsFragment extends Fragment {

    private View tomorrowsFragmentView;

    public TomorrowsFragment() {
        // Required empty public constructor
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

            Calendar calendarDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            
            TextView frz_lvl = tomorrowsFragmentView.findViewById(R.id.freeze_lvl_json);
            TextView visibility = (TextView) tomorrowsFragmentView.findViewById(R.id.visibility_json);
            TextView snow_falling = (TextView) tomorrowsFragmentView.findViewById(R.id.snow_falling_json);

            TextView access_weather = (TextView) tomorrowsFragmentView.findViewById(R.id.access_current_weather_json);

            TextView access_fresh= (TextView) tomorrowsFragmentView.findViewById(R.id.fresh_snow_json);
            TextView access_temp = (TextView) tomorrowsFragmentView.findViewById(R.id.temp_access_json);
            TextView access_feels_like = (TextView) tomorrowsFragmentView.findViewById(R.id.feels_like_access_json);
            TextView access_wnd_dir = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_direction_access_json);
            TextView access_wnd_spd = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_speed_access_json);
            TextView access_wnd_gst = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_gust_access_json);

            TextView top_weather = (TextView) tomorrowsFragmentView.findViewById(R.id.top_current_weather_json);
            TextView top_fresh = (TextView) tomorrowsFragmentView.findViewById(R.id.top_fresh_snow_json);
            TextView top_temp = (TextView) tomorrowsFragmentView.findViewById(R.id.top_temp_access_json);
            TextView top_feels_like = (TextView) tomorrowsFragmentView.findViewById(R.id.feels_like_top_json);
            TextView top_wnd_dir = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_direction_top_json);
            TextView top_wnd_spd = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_speed_top_json);
            TextView top_wnd_gst = (TextView) tomorrowsFragmentView.findViewById(R.id.wnd_gust_top_json);

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

                        if (jsoObject.has("base") && jsonObj.has("timestamp")) {
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

}