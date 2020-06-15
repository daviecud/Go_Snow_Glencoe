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

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;

            case 1:
                GroupChatsFragment groupChatsFragment = new GroupChatsFragment();
                return groupChatsFragment;

            case 2:
                ContactsFragment contactsFragment = new ContactsFragment();
                return contactsFragment;

            case 3:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

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
