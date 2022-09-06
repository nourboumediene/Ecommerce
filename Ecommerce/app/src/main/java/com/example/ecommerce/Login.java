package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.admin.home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextView signin;
    EditText email,password;
    Button login, logingoogle;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializationOfFields();
        signin = findViewById(R.id.log_in);
        signin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,signup.class);
                startActivity(i);
            }
        }));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){
                    logIn();

                }
            }
        });

    }

    private void logIn(){
        String my_email = email.getText().toString();
        String my_password = password.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(my_email,my_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent i = new Intent(Login.this, home.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void initializationOfFields(){
        email = findViewById(R.id.email);
        password= findViewById(R.id.password);
        login = findViewById(R.id.sign_up);
        logingoogle = findViewById(R.id.sign_up_google);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private boolean checkFields(){
        if(email.getText().toString().isEmpty()){
            email.setError("Please fill the email");
            return false;
        }else if (password.getText().toString().isEmpty()){
            password.setError("Pleaser fill your password");
            return false;

        }else {
            return true;
        }
    }




}