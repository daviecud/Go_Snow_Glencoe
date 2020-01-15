package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusinessActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BusinessAdapter adapter;

    List<BusinessDetails> infoList;

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        infoList = new ArrayList<>();

        recyclerView = findViewById(R.id.localBus_RecycleView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Adding details to the ArrayList<> called infoList, it will create a new BusinessDetails entry

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
                "www.thegreenwellystop",
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

        Collections.shuffle(infoList);
        adapter = new BusinessAdapter(this, infoList);
        recyclerView.setAdapter(adapter);
    }
}
