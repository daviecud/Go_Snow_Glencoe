package com.example.gosnow_glencoe.SnowChat;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gosnow_glencoe.R;
import com.example.gosnow_glencoe.SnowChat.GroupChatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GroupChatsFragment extends Fragment {

    private View groupFragmentView;
    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_groups = new ArrayList<>();
    private DatabaseReference rootRef;

    public GroupChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groupFragmentView = inflater.inflate(R.layout.fragment_group_chats, container, false);

        rootRef = FirebaseDatabase.getInstance().getReference().child("Groups");

        initializeFields();
        getAndDisplayGroups();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String chosenGroupName = adapterView.getItemAtPosition(position).toString(); //this will get the chosen group name and store it in the declared String

                Intent intent = new Intent(getContext(), GroupChatActivity.class);
                intent.putExtra("groupName" , chosenGroupName); //this will pass chosen group chat value to GroupChatActivity
                startActivity(intent);

            }
        });
        return groupFragmentView;
    }

    private void initializeFields() {
        list_view = groupFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list_of_groups);
        list_view.setAdapter(arrayAdapter);
    }

    //Method to display Group Chat options for user to select which group chat they would like to participate in
    //Using rootRef declared above to access the Firebase database child and attaching a new ValueEventListener to List available groups for user to view
    //onDataChange() will
    private void getAndDisplayGroups() {
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    set.add(((DataSnapshot)iterator.next()).getKey()); //this will get all entries(key) in Groups from firebaseDatabase
                }

                list_of_groups.clear();
                list_of_groups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
