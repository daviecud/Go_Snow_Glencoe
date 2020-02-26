package com.example.gosnow_glencoe.SnowChat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gosnow_glencoe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateMessagesAdapter extends RecyclerView.Adapter<PrivateMessagesAdapter.PrivateMessageViewHolder> {

    private List<PrivateMessages> privateMessagesList;
    private FirebaseAuth auth;
    private DatabaseReference usersRef;

    public PrivateMessagesAdapter(List<PrivateMessages> privateMessagesList) {
        this.privateMessagesList = privateMessagesList;
    }

    public class PrivateMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView senderMessageText, receiverMessageText;
        public CircleImageView receiverProfileImage;

        public PrivateMessageViewHolder(@NonNull View itemView) {

            super(itemView);

            senderMessageText = itemView.findViewById(R.id.sender_message_text);
            receiverMessageText = itemView.findViewById(R.id.receiver_message_text);
            receiverProfileImage = itemView.findViewById(R.id.private_message_profile_image);
        }
    }

    @NonNull
    @Override
    public PrivateMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.private_message_layout, parent, false);

        auth = FirebaseAuth.getInstance();

        return new PrivateMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PrivateMessageViewHolder holder, int position) {
        String messageSenderId = auth.getCurrentUser().getUid();
        PrivateMessages messages = privateMessagesList.get(position);

        String fromUserId = messages.getFrom();
        String fromMessageType = messages.getType();

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserId);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("image")) {
                    String receiverProImage = dataSnapshot.child("image").getValue().toString();

                    Picasso.get().load(receiverProImage).placeholder(R.drawable.profile).into(holder.receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (fromMessageType.equals("text")) {
            holder.receiverMessageText.setVisibility(View.INVISIBLE);
            holder.receiverProfileImage.setVisibility(View.INVISIBLE);
            holder.senderMessageText.setVisibility(View.INVISIBLE);


            if (fromUserId.equals(messageSenderId)) {
                holder.senderMessageText.setVisibility(View.VISIBLE);
                holder.senderMessageText.setBackgroundResource(R.drawable.sender_message_layout);
                holder.senderMessageText.setText(messages.getMessage());
            }

            else {
                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.receiverMessageText.setVisibility(View.VISIBLE);

                holder.receiverMessageText.setBackgroundResource(R.drawable.receiver_message_layout);
                holder.receiverMessageText.setText(messages.getMessage());

            }
        }
    }

    @Override
    public int getItemCount() {
        return privateMessagesList.size();
    }
}
