package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.viewHolder.GridProductHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserProductAdapter extends RecyclerView.Adapter<GridProductHolder> {
    Context context;
    ArrayList<Product> products;

    public UserProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public GridProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_grid,parent,false);

        return new GridProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridProductHolder holder, int position) {
        holder.product_title.setText(products.get(position).getName());
        holder.product_category.setText(products.get(position).getCategory());
        holder.product_price.setText(products.get(position).getPrice());
        Picasso.get().load(products.get(position).getImage()).into(holder.product_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,ProductDetails.class);
                i.putExtra("productID", products.get(position).getProductID());
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
