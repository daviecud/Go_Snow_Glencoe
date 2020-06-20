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

        //get todays date too set tab title
        String currentDate = DateFormat.getDateInstance().format(new Date());
        //get tomorrows date too set to tab
        String sourceDate = currentDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar calendarDate = Calendar.getInstance();
        try {
            calendarDate.setTime(dateFormat.parse(sourceDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarDate.add(Calendar.DATE, 1);
        sourceDate = dateFormat.format(calendarDate.getTime());

        //
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
