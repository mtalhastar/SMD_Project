package com.example.smd_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderModel> chatItems;
    Context context;


    public OrderAdapter(List<OrderModel> chatItems, Context context) {
        this.chatItems = chatItems;
        this.context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewFlavor, textViewPrice,
                textViewAddressLabel, textViewAddress,
                textViewSubtotalLabel, textViewSubtotalValue,
                textViewDeliveryLabel, textViewDeliveryID;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewAddressLabel = itemView.findViewById(R.id.textViewAddressLabel);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewSubtotalLabel = itemView.findViewById(R.id.textViewSubtotalLabel);
            textViewSubtotalValue = itemView.findViewById(R.id.textViewSubtotalValue);
            textViewDeliveryLabel = itemView.findViewById(R.id.textViewDeliveryLabel);
            textViewDeliveryID = itemView.findViewById(R.id.textViewDeliveryID);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlayout, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        OrderModel favouriteItem = chatItems.get(position);
        holder.textViewName.setText(favouriteItem.getItems());
        holder.textViewAddress.setText(favouriteItem.getHomeaddress());
        holder.textViewDeliveryID.setText(favouriteItem.getUserId());
        holder.textViewSubtotalValue.setText("$"+favouriteItem.getPrice().toString());

    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }




}
