package com.example.gosnow_glencoe.SnowChat;

import com.example.gosnow_glencoe.SnowChat.ChatsFragment;
import com.example.gosnow_glencoe.SnowChat.ContactsFragment;
import com.example.gosnow_glencoe.SnowChat.GroupChatsFragment;
import com.example.gosnow_glencoe.SnowChat.RequestFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //Switch case statement for launching new fragment on users choice
    @NotNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new ChatsFragment();

            case 1:
                return new GroupChatsFragment();

            case 2:
                return new ContactsFragment();

            case 3:
                return new RequestFragment();

                default:
                    return null;
        }
    }

    //Assign number of tabs
    @Override
    public int getCount() {
        return 4;
    }

    //Set tab titles
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Chats";

            case 1:
                return "Groups";

            case 2:
                return "Contacts";

            case 3:
                return "Requests";

            default:
                return null;
        }
    }
}
