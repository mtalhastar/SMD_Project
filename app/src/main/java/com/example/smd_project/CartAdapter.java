package com.example.smd_project;



import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<ProductModel> chatItems;
    Context context;
    private CartListener cartListener;

    public CartAdapter(List<ProductModel> chatItems, Context context,CartListener cartListener) {
        this.chatItems = chatItems;
        this.context = context;
        this.cartListener = cartListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
         public TextView shortDescription;
         public  TextView textViewPrice,number;
        public ImageView plus;
        public ImageView minus;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.images);
            title = itemView.findViewById(R.id.title);
            shortDescription=itemView.findViewById(R.id.shortDescription);
            textViewPrice=itemView.findViewById(R.id.textViewPrice);
            plus=itemView.findViewById(R.id.plus);
            minus=itemView.findViewById(R.id.minus);
            number=itemView.findViewById(R.id.number);


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitemlayout, parent, false);
        return new ViewHolder(view);
    }

    public interface CartListener {
        void onItemQuantityChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductModel chatItem = chatItems.get(position);
        holder.title.setText(chatItem.getChocolato());
        Picasso.get().load(chatItem.getImageUrl()).into(holder.image);
        holder.shortDescription.setText(chatItem.getDescription());
        holder.textViewPrice.setText(chatItem.getPrice());
        holder.number.setText(chatItem.getQuantity());
         notifyCartListener();

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the new activity with the Intent
               chatItem.decreaseQuantity();
               holder.number.setText(chatItem.getQuantity());
               notifyCartListener();
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatItem.increaseQuantity();
                holder.number.setText(chatItem.getQuantity());

                notifyCartListener();
            }
        });
        // Set other properties based on your data
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

    public void filterList(ArrayList<ProductModel> chatItem){
        chatItems=chatItem;
        notifyDataSetChanged();
    }

    public void removeitem(int postition){
        chatItems.get(postition).setQuantity(0);
        chatItems.remove(postition);
        notifyDataSetChanged();
    }

    private void notifyCartListener() {
        if (cartListener != null) {
            cartListener.onItemQuantityChanged();
        }
    }
}