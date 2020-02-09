package com.example.gosnow_glencoe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import java.util.zip.Inflater;

public class RequestFragment extends Fragment {

    private View requestFragmentView;
    private RecyclerView requestList;
    private DatabaseReference chatRequestRef, usersRef, contactsRef;
    private FirebaseAuth auth;
    private String currentUserID;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestFragmentView =  inflater.inflate(R.layout.fragment_request, container, false);

        chatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        auth = FirebaseAuth.getInstance();
        currentUserID = auth.getUid();
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
                                                CharSequence options[] = new CharSequence[] {
                                                        "Accept",
                                                        "Cancel"
                                                };
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle(requestedUserName + " Chat Request");
                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

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

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
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
