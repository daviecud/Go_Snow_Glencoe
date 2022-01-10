package com.example.gosnow_glencoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.gosnow_glencoe.SnowChat.SnowChatActivity;
import com.example.gosnow_glencoe.SnowReport.SnowForecastActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class SportActivity extends AppCompatActivity {

    private AdView mAdView;
    private ImageView pistemapImage;
    private String fullscreenInd;
    //private BottomNavigationView bottomNavigationView;
    private NavigationBarView navBarView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow_sports);

        navBarView = findViewById(R.id.bottom_nav);
        navBarView.setOnItemSelectedListener(bottomNavBarMethod);

//        pistemapImage = findViewById(R.id.pistemap_image);
//        pistemapImage.setImageResource(R.drawable.pistemap2);
//
//        fullscreenInd = getIntent().getStringExtra("fullscreenIndicator");
//        if ("y".equals(fullscreenInd)) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().hide();
//
//            pistemapImage.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            pistemapImage.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            pistemapImage.setAdjustViewBounds(false);
//            pistemapImage.setScaleType(ImageView.ScaleType.FIT_XY);
//        }
//
//        pistemapImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SportActivity.this, SportActivity.class);
//
//                if ("y".equals(fullscreenInd)) {
//                    intent.putExtra("fullscreenIndicator", "");
//                } else {
//                    intent.putExtra("fullscreenIndicator", "y");
//                }
//                SportActivity.this.startActivity(intent);
//            }
//        });
    }

    private NavigationBarView.OnItemSelectedListener bottomNavBarMethod = item -> {


        switch (item.getItemId())
        {
            case R.id.bottom_nav_home:
                Intent intent = new Intent(SportActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_nav_snowreport:
                Intent sport_intent = new Intent(SportActivity.this, SnowForecastActivity.class);
                startActivity(sport_intent);
                break;
            case R.id.bottom_nav_snowchat:
                Intent chat_intent = new Intent(SportActivity.this, SnowChatActivity.class);
                startActivity(chat_intent);
                break;
            case R.id.bottom_nav_business:
                Intent bus_intent = new Intent(SportActivity.this, BusinessActivity.class);
                startActivity(bus_intent);
                break;
            case R.id.bottom_nav_resort:
                Intent resort_intent = new Intent(SportActivity.this, ResortActivity.class);
                startActivity(resort_intent);
        }
        return  true;
    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//
//        getMenuInflater().inflate(R.menu.snow_sports_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        if (item.getItemId() == R.id.menu_home_option) {
//            Intent intent = new Intent(SportActivity.this, MainActivity.class);
//            startActivity(intent);
//        }
//
//        if (item.getItemId() == R.id.menu_snow_report_option) {
//            Intent intent = new Intent(SportActivity.this, SnowReportActivity.class);
//            startActivity(intent);
//        }
//
//        if (item.getItemId() == R.id.menu_resort_option) {
//            Intent intent = new Intent(SportActivity.this, ResortActivity.class);
//            startActivity(intent);
//        }
//
//        if (item.getItemId() == R.id.menu_business_option) {
//            Intent intent = new Intent(SportActivity.this, BusinessActivity.class);
//            startActivity(intent);
//        }
//
//        if (item.getItemId() == R.id.menu_directions_option) {
//            Intent intent = new Intent(SportActivity.this, DirectionsActivity.class);
//            startActivity(intent);
//        }
//        return true;
//    }


}
