package com.example.ecommerce.user;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ecommerce.R;
import com.example.ecommerce.UserHomeFrag;
import com.example.ecommerce.admin.AdminHomeFrag;

public class UserHome extends AppCompatActivity {
ImageView home,cart,profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        initializationOfFields();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserHomeFrag()).commit();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserHomeFrag()).commit();
                home.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                cart.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                profile.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);

            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFrag()).commit();
                home.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                cart.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                profile.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFrag()).commit();
                home.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                cart.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                profile.setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);
            }
        });





    }
    private void initializationOfFields(){
        home = findViewById(R.id.home);
        cart = findViewById(R.id.cart);
        profile = findViewById(R.id.profile);
    }
}