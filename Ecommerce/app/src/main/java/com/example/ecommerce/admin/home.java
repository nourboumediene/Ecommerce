package com.example.ecommerce.admin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ecommerce.R;

public class home extends AppCompatActivity {
ImageView homeBtn, orderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializationOfFields();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminHomeFrag()).commit();

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminHomeFrag()).commit();
                homeBtn.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                orderBtn.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);

            }
        });
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFrag()).commit();
                orderBtn.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                homeBtn.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);

            }
        });

    }


    private void initializationOfFields(){
        homeBtn = findViewById(R.id.home);
        orderBtn = findViewById(R.id.order);

    }
}