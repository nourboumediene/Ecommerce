package com.example.ecommerce.viewHolder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

import java.text.BreakIterator;

public class ProductViewHolder extends RecyclerView.ViewHolder {


        public  TextView productTitle, productCategory, productDescription, productPrice;
        public ImageView productImage,deleteImage;


        public ProductViewHolder(@NonNull View itemView) {
                super(itemView);


                productTitle = itemView.findViewById(R.id.product_title);
                productCategory = itemView.findViewById(R.id.product_category);
                productDescription = itemView.findViewById(R.id.product_category);
                productPrice = itemView.findViewById(R.id.product_price);
                productImage = itemView.findViewById(R.id.product_image);
                deleteImage = itemView.findViewById(R.id.delete);
        }
}
