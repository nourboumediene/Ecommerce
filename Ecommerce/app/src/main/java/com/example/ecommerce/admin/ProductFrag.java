package com.example.ecommerce.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.Adapter.ProductAdapter;
import com.example.ecommerce.R;
import com.example.ecommerce.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProductFrag extends Fragment {

FloatingActionButton add;
View mview;
RecyclerView product_list;
DatabaseReference productRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview =inflater.inflate(R.layout.fragment_product, container, false);
        add = mview.findViewById(R.id.add);
        product_list = mview.findViewById(R.id.product_list);
        productRef = FirebaseDatabase.getInstance(getContext().getString(R.string.reference))
                        .getReference()
                                .child("Products");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddProduct.class);
                startActivity(i);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        product_list.setLayoutManager(manager);
        initAdapter();
        return mview;
    }
    public void initAdapter(){
        ArrayList<Product> products =new ArrayList<>();
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    products.add(product);
                }
                ProductAdapter adapter = new ProductAdapter(getContext(),products);
                product_list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}