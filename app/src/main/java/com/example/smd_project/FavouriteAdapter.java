package com.example.smd_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private List<ProductModel> chatItems;
    Context context;
    private CartAdapter.CartListener cartListener;

    public FavouriteAdapter(List<ProductModel> chatItems, Context context) {
        this.chatItems = chatItems;
        this.context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView shortDescription;
        public  TextView textViewPrice,number;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageFavourite);
            title = itemView.findViewById(R.id.titleFavourite);
            shortDescription=itemView.findViewById(R.id.descriptionFavourite);
            textViewPrice=itemView.findViewById(R.id.priceFavourite);
        }
    }


    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favouriteitemlayout, parent, false);
        return new FavouriteAdapter.ViewHolder(view);
    }

    public interface CartListener {
        void onItemQuantityChanged();
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.ViewHolder holder, int position) {
        ProductModel favouriteItem = chatItems.get(position);
        holder.title.setText(favouriteItem.getChocolato());
        Picasso.get().load(favouriteItem.getImageUrl()).into(holder.image);
        holder.shortDescription.setText(favouriteItem.getDescription());
        holder.textViewPrice.setText("$"+favouriteItem.getPrice());



        // Set other properties based on your data
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }
    public void removeitem(int postition){
        chatItems.get(postition).setQuantity(0);
        chatItems.remove(postition);
        notifyDataSetChanged();
    }


}
