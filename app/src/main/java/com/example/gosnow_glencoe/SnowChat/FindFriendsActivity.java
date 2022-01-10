package com.example.gosnow_glencoe.SnowChat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gosnow_glencoe.R;
import com.firebase.ui.database.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView friendsRecyclerView;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        friendsRecyclerView = findViewById(R.id.find_friends_recyclerview);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.find_friends_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Contacts");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(usersRef, Contacts.class)
                .build();

        FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder> adapter = new FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder>(options) {
            //retrieving database users using Contacts class
            @Override
            protected void onBindViewHolder(@NonNull FindFriendsViewHolder holder, final int position, @NonNull Contacts model) {
                holder.userName.setText(model.getName());
                holder.userStatus.setText(model.getStatus());
                Picasso.get().load(model.getProfileImage()).placeholder(R.drawable.profile).into(holder.profileImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String clicked_user_id = getRef(position).getKey();

                        Intent intent = new Intent(FindFriendsActivity.this, UserProfileActivity.class);
                        intent.putExtra("clicked_user_id", clicked_user_id);
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_layout, viewGroup, false);
                FindFriendsViewHolder viewHolder = new FindFriendsViewHolder(view);
                return viewHolder;
            }
        };
        friendsRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder {

        TextView userName, userStatus;
        CircleImageView profileImage;

        public FindFriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_text);
            userStatus = itemView.findViewById(R.id.user_status_text);
            profileImage = itemView.findViewById(R.id.users_profile_picture);
        }
    }
}
