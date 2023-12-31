package com.example.smd_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    ArrayList<ProductModel> yourDataList;
    public ProductAdapter(Context context, ArrayList<ProductModel> yourDataList) {
        this.context = context;
        this.yourDataList=yourDataList;
    }

    @Override
    public int getCount() {
        // Return the number of items in your data source
        return yourDataList.size();
    }

    @Override
    public Object getItem(int position) {
        // Return the data item at the specified position
        return yourDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Return the ID of the item at the specified position
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the grid item layout
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.griditem, parent, false);
        }

        // Get references to the views within the grid item layout
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);
        TextView textViewPrice = convertView.findViewById(R.id.textViewPrice);
        ImageButton imageButtonAdd = convertView.findViewById(R.id.imageButtonAdd);
        LinearLayout productCard=convertView.findViewById(R.id.productCard);

        ProductModel currentItem = yourDataList.get(position);
        Picasso.get().load(currentItem.getImageUrl()).into(imageView);
        textViewName.setText(currentItem.getChocolato());
        textViewDescription.setText(currentItem.getDescription());
        textViewPrice.setText("$"+currentItem.getPrice());

        // Set click listener for the add button
        productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,ActivityProductDetail.class);
                intent.putExtra("imageUrl", currentItem.getImageUrl());
                intent.putExtra("Name", currentItem.getChocolato());
                intent.putExtra("LongDescription", currentItem.getLongDescription());
                intent.putExtra("shortDescription", currentItem.getDescription());
                intent.putExtra("price", currentItem.getPrice());

                context.startActivity(intent);
            }
        });

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
    public void filterList(ArrayList<ProductModel> chatItems){
        yourDataList=chatItems;
        notifyDataSetChanged();
    }
}
