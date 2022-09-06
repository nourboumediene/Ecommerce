package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ecommerce.admin.home;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
  Handler h = new Handler();
  FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth =FirebaseAuth.getInstance();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseAuth.getCurrentUser() != null){
                    Intent i = new Intent(MainActivity.this, home.class);
                    startActivity(i);
                }else{
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
                finish();}
            }
        },2000);
    }
}