package com.example.gosnow_glencoe.SnowChat;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gosnow_glencoe.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private View privateMessagesView;
    private RecyclerView messagesList;
    private DatabaseReference messagesRef, userRef;
    private FirebaseAuth auth;
    private String currentUserID;



    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        privateMessagesView = inflater.inflate(R.layout.fragment_chats, container, false);

        auth = FirebaseAuth.getInstance();
        currentUserID = auth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserID);
        messagesList = privateMessagesView.findViewById(R.id.messages_list);
        messagesList.setLayoutManager(new LinearLayoutManager(getContext()));

        return privateMessagesView;
    }

    @Override
    public void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(messagesRef, Contacts.class)
                .build();

        FirebaseRecyclerAdapter<Contacts, MessagesViewHolder> adapter = new FirebaseRecyclerAdapter<Contacts, MessagesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MessagesViewHolder holder, int position, @NonNull Contacts model) {
                final String usersID = getRef(position).getKey();
                final String[] getImage = {"default_image"};

                userRef.child(usersID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.hasChild("image")) {
                                getImage[0] = dataSnapshot.child("image").getValue().toString();
                                Picasso.get().load(getImage[0]).into(holder.profileImageView);
                            }

                            final String getName = dataSnapshot.child("name").getValue().toString();
                            final String getStatus = dataSnapshot.child("status").getValue().toString();

                            holder.userName.setText(getName);

                            if (dataSnapshot.child("userStatus").hasChild("state")) {
                                String status = dataSnapshot.child("userStatus").child("state").getValue().toString();
                                String date = dataSnapshot.child("userStatus").child("date").getValue().toString();
                                String time = dataSnapshot.child("userStatus").child("time").getValue().toString();

                                if (status.equals("online")) {
                                    holder.userStatus.setText("online");
                                }
                                if (status.equals("offline")) {
                                    holder.userStatus.setText("Last seen: " + date + " " + time);
                                }



                            } else {
                                holder.userStatus.setText("offline");
                            }

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getContext(), ChatsActivity.class);
                                    intent.putExtra("visit_user_id", usersID);
                                    intent.putExtra("visit_user_name", getName);
                                    intent.putExtra("visit_image", getImage[0]);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_layout, viewGroup, false);
                return new MessagesViewHolder(view);

            }
        };

        messagesList.setAdapter(adapter);
        adapter.startListening();
    }


    public static class MessagesViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profileImageView;
        TextView userStatus, userName;

        public MessagesViewHolder(@NonNull View itemView) {

            super(itemView);

            profileImageView = itemView.findViewById(R.id.users_profile_picture);
            userStatus = itemView.findViewById(R.id.user_status_text);
            userName = itemView.findViewById(R.id.user_name_text);
        }
    }
}
