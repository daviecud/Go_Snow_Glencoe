package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        temperature = findViewById(R.id.tempJson);
        current_condition = findViewById(R.id.currentConditionJson);
        topDepth = findViewById(R.id.topDepthHeading);
        accessDepth = findViewById(R.id.accessDepthHeading);
        snow_report = findViewById(R.id.snowImage);
        snow_sports = findViewById(R.id.sportImage);
        snow_chat = findViewById(R.id.chatImage);
        resort_info = findViewById(R.id.resortImage);
        local_business = findViewById(R.id.localImage);
        google_directions = findViewById(R.id.directionsImage);

        getSnow();
        getWeatherConditions();
    }

    protected void getSnow() {
        String url = "https://api.weatherunlocked.com/api/snowreport/1398?";

        JsonObjectRequest snow = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String upper = response.getString("uppersnow_cm") + "cm";
                    String lower = response.getString("lowersnow_cm") + "cm";
                    String conditions = response.getString("conditions");

                    topDepth.setText(upper);
                    accessDepth.setText(lower);
                    current_condition.setText(conditions);

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

    protected void getWeatherConditions() {
        String url = "https://api.darksky.net/forecast//56.6325,-4.8279";

        JsonObjectRequest weather = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("currently");
                    String temp = String.valueOf(jsonResponse.getDouble("temperature"));
                    String summary = jsonResponse.getString("summary");

                    temperature.setText(temp);
                    current_condition.setText(summary);


                    double temp_int = Double.parseDouble(temp); //sets the string value from api to a double variable
                    double centi = (temp_int - 32) / 1.8000; //takes value from temp_int variable -32 then divides by 1.8000 to set centi with new value
                    centi = Math.round(centi); //Will take value from centi variable and use Math library round method to round to nearest whole number
                    int i = (int)centi; //will set i to an integer of value returned from centi variable
                    temperature.setText(String.valueOf(i));

                    //+ "°C"
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

        Intent intent = new Intent(this, SnowReportActivity.class);
        startActivity(intent);
    }

    public void goToSnowSports(View view) {
        displayToast(getString(R.string.snow_sports_clicked));

        Intent intent = new Intent(this, SportActivity.class);
        startActivity(intent);
    }

    public void goToSnowChat(View view) {
        displayToast(getString(R.string.snow_chat_clicked));

        Intent intent = new Intent(this, SnowChatActivity.class);
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

        Intent intent = new Intent(this, DirectionsActivity.class);
        startActivity(intent);
    }
}