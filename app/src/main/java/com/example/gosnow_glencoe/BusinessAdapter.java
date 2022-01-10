package com.example.gosnow_glencoe;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*
 * RecyclerView.Adapter - this binds the data to the view
 * RecyclerViewHolder.ViewHolder - this holds the view from card.xml
 * */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessHolder> {

    private Context context;
    private List<BusinessDetails> infoList; //this is assigning BusinessDetails a into List called infoList

    public BusinessAdapter(Context context, List<BusinessDetails> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @NonNull
    @Override //Method inherited from RecyclerView class
    public BusinessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.local_businesses_card , null);
        return new BusinessHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override //Method inherited from RecyclerView class
    public void onBindViewHolder(@NonNull BusinessHolder holder, int position) {
        BusinessDetails info = infoList.get(position);

        holder.textViewBusName.setText(info.getBus_name());
        holder.textViewBusAdd.setText(info.getBus_address());
        holder.textViewBusTel.setText(info.getBus_tel());
        holder.textViewBusWeb.setText(info.getBus_web());
        holder.textViewBusSummary.setText(info.getBus_summary());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(info.getBus_image(), null));
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class BusinessHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewBusName;
        TextView textViewBusAdd;
        TextView textViewBusTel;
        TextView textViewBusWeb;
        TextView textViewBusSummary;

        public BusinessHolder(@NonNull View itemView) {
            super(itemView);

            textViewBusName = itemView.findViewById(R.id.bus_name);
            textViewBusAdd = itemView.findViewById(R.id.bus_address);
            textViewBusTel = itemView.findViewById(R.id.bus_tel);
            textViewBusWeb = itemView.findViewById(R.id.bus_web);
            textViewBusSummary = itemView.findViewById(R.id.bus_summary);
            imageView = itemView.findViewById(R.id.bus_logo);

        }
    }
}
