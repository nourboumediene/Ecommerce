package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerce.model.Product;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.example.ecommerce.R;

import com.example.ecommerce.viewHolder.ProductViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
   Context context;
   ArrayList<Product> products;
   DatabaseReference productsRef;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        productsRef = FirebaseDatabase.getInstance(context.getString(R.string.reference)).getReference().child("Products");
    }


    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_row,parent,false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Picasso.get().load(products.get(position).getImage()).into(holder.productImage);
        holder.productTitle.setText(products.get(position).getName());
        holder.productDescription.setText(products.get(position).getDescription());
        holder.productPrice.setText(products.get(position).getPrice()+"DZD");
        holder.productCategory.setText(products.get(position).getCategory());

        //do the delete function
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(context);
                mydialog.setTitle("Delete" + products.get(position).getName());
                mydialog.setMessage("Do you really want to delete"+products.get(position).getName());
                mydialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productsRef.child(products.get(position).getProductID())
                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(context,"Product deleted", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                });
                mydialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mydialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
