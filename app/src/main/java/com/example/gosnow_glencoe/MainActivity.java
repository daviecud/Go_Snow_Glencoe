package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gosnow_glencoe.SnowChat.SignInActivity;
import com.example.gosnow_glencoe.SnowReport.SnowForecastActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView snow_report, snow_sports, snow_chat, resort_info, local_business, google_directions;
    TextView topDepth, accessDepth, current_condition, temperature;

    //TODO set up AsyncTask for JSon requests, add degrees c sign to temp,
    // move weather to above temp, sort the alignment of depths response, fix option boxes colours and make all contents clickable,
    // set background to darker top gradient to ice bottom

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFields();
        new GetSnowDetailsTask().execute();
//        new GetWeatherInfoTask().execute();
        getWeatherConditions();
    }

    private void initializeFields() {
        temperature = findViewById(R.id.tempJson);
        // current_condition = findViewById(R.id.currentConditionJson);
        topDepth = findViewById(R.id.topDepthHeading);
        accessDepth = findViewById(R.id.accessDepthHeading);
        snow_report = findViewById(R.id.snowImage);
        snow_sports = findViewById(R.id.sportImage);
        snow_chat = findViewById(R.id.chatImage);
        resort_info = findViewById(R.id.resortImage);
        local_business = findViewById(R.id.localImage);
        google_directions = findViewById(R.id.directionsImage);
    }

    class GetSnowDetailsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String snowUrl = HttpRequest.executeGet("https://api.weatherunlocked.com/api/snowreport/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3");
            return snowUrl;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jObject = new JSONObject(s);
                String upper = jObject.getString("uppersnow_cm") + "cm";
                String lower = jObject.getString("lowersnow_cm") + "cm";

                topDepth.setText(upper);
                accessDepth.setText(lower);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    protected void getSnow() {
//        String url = "3";
//
//        JsonObjectRequest snow = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String upper = response.getString("uppersnow_cm") + "cm";
//                    String lower = response.getString("lowersnow_cm") + "cm";
//                    //String conditions = response.getString("conditions");
//
//                    topDepth.setText(upper);
//                    accessDepth.setText(lower);
//                    //current_condition.setText(conditions);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(snow);
//    }

//
//    class GetWeatherInfoTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String url = HttpRequest.executeGet("https://api.darksky.net/forecast/27a87fa552b2a741f881b3ee639b994a/56.6325,-4.8279");
//            return url;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                JSONObject jObject = new JSONObject(s);
//
//                String temp = jObject.get("temperature") + "°C";
////                String summary = jObject.getString("summary");
//                temperature.setText(temp);
//                //current_condition.setText(summary);
//
////                double temp_int = Double.parseDouble(temp); //parse the string value from api to a double variable
////                double centi = (temp_int - 32) / 1.8000; //takes value from temp_int variable -32 then divides by 1.8000 to set centi with new value
////                centi = Math.round(centi); //Will take value from centi variable and use Math library round method to round to nearest whole number
////                int i = (int)centi; //will set i to an integer of value returned from centi variable
////                String newTemp = String.valueOf(i);
////                temperature.setText(newTemp);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
////            String url = "https://api.darksky.net/forecast/27a87fa552b2a741f881b3ee639b994a/56.6325,-4.8279";
////
////            JsonObjectRequest maintemp = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
////
////                @Override
////                public void onResponse(JSONObject response) {
////                    try {
////                        String temp = response.get("temperature") + "°C";
//////                    String lower = response.getString("lowersnow_cm") + "cm";
////                        //String conditions = response.getString("conditions");
////
////                        temperature.setText(temp);
//////                    accessDepth.setText(lower);
////                        //current_condition.setText(conditions);
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
////
////            }, new Response.ErrorListener() {
////                @Override
////                public void onErrorResponse(VolleyError error) {
////
////                }
////            });
////            RequestQueue queue = Volley.newRequestQueue(this);
////            queue.add(maintemp);
//        }
//    }

    protected void getWeatherConditions() {

        String url = "https://api.darksky.net/forecast/27a87fa552b2a741f881b3ee639b994a/56.6325,-4.8279";

        JsonObjectRequest weather = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("currently");
                    String temp = String.valueOf(jsonResponse.getInt("temperature"));

                    int temp_int = Integer.parseInt(temp); //sets the string value from json to a double variable
                    int centi = (int) ((temp_int - 32) / 1.8000); //takes value from temp_int variable -32 then divides by 1.8000 to set centi with new value
                    centi = Math.round(centi); //Will take value from centi variable and use Math library round method to round to nearest whole number
                    int i = (int)centi; //will set i to an integer of value returned from centi variable
                    String newTemp = i + "°C";
                    temperature.setText(newTemp);
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
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    //Add toast/snackbar message as user clicks choice


    public void goToSnowReport(View view) {
        //Shows a message that the snow report was clicked
        displayToast(getString(R.string.snow_report_click));
        //Explicit Intent
        Intent intent = new Intent(this, SnowForecastActivity.class);
        startActivity(intent);
    }

    public void goToSnowSports(View view) {
        displayToast(getString(R.string.snow_sports_clicked));
        //Explicit Intent
        Intent intent = new Intent(this, SportActivity.class);
        startActivity(intent);
    }

    public void goToSnowChat(View view) {
        displayToast(getString(R.string.snow_chat_clicked));

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void goToResortInfo(View view) {
        displayToast(getString(R.string.resort_info_clicked));

        Intent intent = new Intent(this, ResortActivity.class);
        startActivity(intent);
    }

    public void goToBusinesses(View view) {
        displayToast(getString(R.string.local_business_clicked));

        Intent intent = new Intent(this, BusinessActivity.class);
        startActivity(intent);
    }

    public void goToDirections(View view) {
        displayToast(getString(R.string.directions_clicked));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:56.6325,-4.8279"));
        startActivity(intent);
    }
}