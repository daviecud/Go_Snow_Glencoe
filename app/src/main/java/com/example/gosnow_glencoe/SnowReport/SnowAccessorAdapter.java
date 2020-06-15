package com.example.gosnow_glencoe.SnowReport;

import android.text.format.DateUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SnowAccessorAdapter extends FragmentPagerAdapter {

    public SnowAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TodaysFragment todaysFragment = new TodaysFragment();
                return todaysFragment;

            case 1:
                TomorrowsFragment tomorrowsFragment = new TomorrowsFragment();
                return tomorrowsFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        //get todays date to set tab title
        String currentDate = DateFormat.getDateInstance().format(new Date());
        //get tomorrows date to set to tab
        String sourceDate = currentDate;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        Calendar calendarDate = Calendar.getInstance();
        try {
            calendarDate.setTime(dateFormat.parse(sourceDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarDate.add(Calendar.DATE, 1);
        sourceDate = dateFormat.format(calendarDate.getTime());

//        Date tomorrowDate = null;
//        try {
//            tomorrowDate = dateFormat.parse(sourceDate);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        tomorrowDate = DateUtils.addDays(tomorrowDate, 1)
//        calendarDate.setTime(new Date());
//        calendarDate.add(Calendar.DAY_OF_MONTH, 1);
//        String tomorrowsDate = calendarDate.getTime().toString();

        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");

        switch (position)
        {
            case 0:
                return currentDate;
            case 1:
                return sourceDate;
            default:
                return null;
        }
    }
}
