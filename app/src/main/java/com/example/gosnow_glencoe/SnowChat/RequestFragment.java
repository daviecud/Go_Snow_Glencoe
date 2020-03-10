package com.example.gosnow_glencoe.SnowChat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gosnow_glencoe.R;
import com.example.gosnow_glencoe.SnowChat.Contacts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RequestFragment extends Fragment {

    private View requestFragmentView;
    private RecyclerView requestList;
    private DatabaseReference chatRequestRef, usersRef, contactsRef;
    private FirebaseAuth auth;
    private String currentUserID;
    private Button acceptButton, cancelButton;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestFragmentView =  inflater.inflate(R.layout.fragment_request, container, false);

        auth = FirebaseAuth.getInstance();
        currentUserID = auth.getUid();
        chatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        contactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");

        requestList = requestFragmentView.findViewById(R.id.chat_request_list);
        requestList.setLayoutManager(new LinearLayoutManager(getContext()));

        return requestFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(chatRequestRef.child(currentUserID), Contacts.class)
                .build();

        final FirebaseRecyclerAdapter<Contacts, RequestViewHolder> adapter = new FirebaseRecyclerAdapter<Contacts, RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RequestViewHolder holder, int position, @NonNull Contacts model) {
                holder.itemView.findViewById(R.id.accept_request_button).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.cancel_request_button).setVisibility(View.VISIBLE);

                //get first key, get ID and get request_type from Firebase
                final String listUserID = getRef(position).getKey();
                DatabaseReference getTypeRef = getRef(position).child("request_type").getRef();

                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String type = dataSnapshot.getValue().toString();

                            if (type.equals("received")) {

                                usersRef.child(listUserID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild("image")) {

                                            final String requestedUserImage = dataSnapshot.child("image").getValue().toString();
                                            Picasso.get().load(requestedUserImage).into(holder.profileImage);

                                        }
                                            final String requestedUserName = dataSnapshot.child("name").getValue().toString();
                                            final String requestedUserStatus = dataSnapshot.child("status").getValue().toString();

                                            holder.userName.setText(requestedUserName);
                                            holder.userStatus.setText("Request to connect with you");


                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                CharSequence[] options = new CharSequence[] {
                                                        "Accept",
                                                        "Cancel"
                                                };
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle(requestedUserName + " Chat Request");
                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        //if user clicks position 0 (Accept) from options array above
                                                        if (i == 0) {
                                                            contactsRef.child(currentUserID).child(listUserID).child("Contacts")
                                                                    .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {

                                                                        contactsRef.child(listUserID).child(currentUserID).child("Contacts")
                                                                                .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    //removes request from senders request list
                                                                                    chatRequestRef.child(currentUserID).child(listUserID)
                                                                                            .removeValue()
                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    if (task.isSuccessful()) {
                                                                                                        //removes request from receivers request list after user(s) Accept or Cancel request
                                                                                                        chatRequestRef.child(listUserID).child(currentUserID)
                                                                                                                .removeValue()
                                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                        if (task.isSuccessful()) {
                                                                                                                            Toast.makeText(getContext(), "New Contact Added", Toast.LENGTH_LONG).show();

                                                                                                                        }
                                                                                                                    }
                                                                                                                });
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                }
                                                                            }
                                                                        });                                                                    }
                                                                }
                                                            });
                                                        }
                                                        //if user clicks postion 1 (Cancel) from options array above
                                                        if (i == 1) {
                                                            chatRequestRef.child(currentUserID).child(listUserID)
                                                                    .removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {

                                                                                chatRequestRef.child(listUserID).child(currentUserID)
                                                                                        .removeValue()
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    Toast.makeText(getContext(), "Contact Deleted", Toast.LENGTH_LONG).show();

                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });

                                                        }

                                                    }
                                                });
                                                builder.show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            } else if (type.equals("sent")) {
                                Button request_sent_button = holder.itemView.findViewById(R.id.accept_request_button);
                                request_sent_button.setText("Request Sent");

                                holder.itemView.findViewById(R.id.cancel_request_button).setVisibility(View.VISIBLE);

                                usersRef.child(listUserID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild("image")) {

                                            final String requestedUserImage = dataSnapshot.child("image").getValue().toString();
                                            Picasso.get().load(requestedUserImage).into(holder.profileImage);

                                        }

                                        final String requestedUserName = dataSnapshot.child("name").getValue().toString();
                                        final String requestedUserStatus = dataSnapshot.child("status").getValue().toString();

                                        holder.userName.setText(requestedUserName);
                                        holder.userStatus.setText("You have sent  request to " + requestedUserName);


                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                CharSequence[] options = new CharSequence[] {
                                                       "Cancel chat request"
                                                };
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle("Already sent request");
                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        //if user clicks postion 0 (Cancel) from options array above
                                                        if (i == 0) {
                                                            chatRequestRef.child(currentUserID).child(listUserID)
                                                                    .removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {

                                                                                chatRequestRef.child(listUserID).child(currentUserID)
                                                                                        .removeValue()
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    Toast.makeText(getContext(), "Chat request cancelled by you", Toast.LENGTH_LONG).show();

                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });

                                                        }

                                                    }
                                                });
                                                builder.show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.contact_layout, parent, false);
                RequestViewHolder holder = new RequestViewHolder(view);
                return holder;
            }
        };

        requestList.setAdapter(adapter);
        adapter.startListening();
    }


    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView userName, userStatus;
        CircleImageView profileImage;
        ImageView onlineStatusView;
        Button acceptRequestButton, cancelRequestButton;


        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_text);
            userStatus = itemView.findViewById(R.id.user_status_text);
            profileImage = itemView.findViewById(R.id.users_profile_picture);
            onlineStatusView = itemView.findViewById(R.id.user_online_symbol);
            acceptRequestButton = itemView.findViewById(R.id.accept_request_button);
            cancelRequestButton = itemView.findViewById(R.id.cancel_request_button);
        }
    }
}
