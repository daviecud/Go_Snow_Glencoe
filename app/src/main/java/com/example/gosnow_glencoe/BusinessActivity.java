package com.example.gosnow_glencoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BusinessActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BusinessAdapter adapter;
    Toolbar toolbar;

    List<BusinessDetails> infoList;

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        toolbar = findViewById(R.id.business_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Local Business");

        //inflate
        recyclerView = findViewById(R.id.localBus_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetBusinessDetails();
        //Adding details to the ArrayList<> called infoList, it will create a new BusinessDetails entry

    }


    /*
    * Create ArrayList
    * Add entries using BusinessDetails class
    * Create new BusinessAdapter
    * Each time BusinessActivity opens shuffle entries
    * Set BusinessAdapter to Recyclerview
    * */
    public void GetBusinessDetails() {

        //Create ArrayList
        infoList = new ArrayList<>();
        //Add entries to ArrayList using BusinessDetails class
            infoList.add(new BusinessDetails(
                    1,
                    "Glencoe Mountain Resort",
                    "Glencoe, PH49 4HZ",
                    "01855 851226",
                    "www.glencoemoutain.co.uk",
                    "Winter/Summer Sports, Accommodation",
                    R.drawable.glencoe
            ));

            infoList.add(new BusinessDetails(
                    2,
                    "The Green Welly Stop",
                    "Tyndrum, FK20 8RY",
                    "01838 400271",
                    "www.thegreenwellystop.co.uk",
                    "Gifts - Food & Drink - Petrol - OutdoorWear",
                    R.drawable.greenwelly
            ));

            infoList.add(new BusinessDetails(
                    3,
                    "Kingshouse Hotel",
                    "Glencoe, PH49 4HY",
                    "01855 851 259",
                    "www.kingshousehotel.co.uk",
                    "Stay - Eat - Drink - Gather",
                    R.drawable.kingshouselogo
            ));

            infoList.add(new BusinessDetails(
                    4,
                    "Ice Factor",
                    "Leven Road, Kinlochleven, PH50 4SF",
                    "01855 831 100",
                    "www.ice-factor.co.uk",
                    "Rock/Ice Climbing - Indoor Activities - Food & Drink",
                    R.drawable.ice_factor_logo
            ));

            infoList.add(new BusinessDetails(
                    5,
                    "The Loch Leven Hotel",
                    "Old Ferry Road, Ballachulish, PH33 6SA",
                    "01855 821 236",
                    "www.lochlevenhotel.co.uk",
                    "Accommodation - Loch View Restaurant - Shop",
                    R.drawable.lochleven_logo
            ));

            //Shuffle entries in random order each time BusinessActivity starts
            Collections.shuffle(infoList);
            //Create new BusinessAdapter
            adapter = new BusinessAdapter(this, infoList);
            //set Adapter to recyclerview
            recyclerView.setAdapter(adapter);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.snow_sports_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_home_option) {
            Intent intent = new Intent(BusinessActivity.this, MainActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_snow_report_option) {
            Intent intent = new Intent(BusinessActivity.this, SnowReportActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_snow_sports_option) {
            Intent intent = new Intent(BusinessActivity.this, SportActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_resort_option) {
            Intent intent = new Intent(BusinessActivity.this, ResortActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_directions_option) {
            Intent intent = new Intent(BusinessActivity.this, DirectionsActivity.class);
            startActivity(intent);
        }

        return true;
    }

}
