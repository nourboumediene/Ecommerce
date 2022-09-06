package com.example.ecommerce.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

public class GridProductHolder extends RecyclerView.ViewHolder {
    public ImageView product_image;
    public TextView product_title, product_category,product_description,product_price;
    public GridProductHolder(View itemView){
        super(itemView);
        product_image = itemView.findViewById(R.id.product_image_user);
        product_title = itemView.findViewById(R.id.product_title_user);
        product_category = itemView.findViewById(R.id.product_category_user);
        product_description = itemView.findViewById(R.id.product_description_user);
        product_price = itemView.findViewById(R.id.product_description_user);


    }
}
