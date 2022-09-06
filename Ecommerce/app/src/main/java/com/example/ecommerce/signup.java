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
import com.example.ecommerce.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
TextView login;
EditText email, password, confirmpassword;
Button signup, signupgoogle;
DatabaseReference usersRef;
FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        initializationOfFields();
        login = findViewById(R.id.log_in);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signup.this,Login.class);
                startActivity(i);
            }
        });
        signup = findViewById(R.id.sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fieldsEmpty()){
                    createAccount();

                }
            }
        });
    }
    private void createAccount(){
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString()
        ,password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    saveUserDataIntoDb(new User(email.getText().toString()));

                } else{

                    Toast.makeText(signup.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void saveUserDataIntoDb(User user) {
        usersRef.child(firebaseAuth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(signup.this, home.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(signup.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void initializationOfFields(){
        email= findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirm_password);
        signup = findViewById(R.id.sign_up);
        signupgoogle = findViewById(R.id.sign_up_google);
        firebaseAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance(getString(R.string.reference)).getReference().child("Users");

    }
    private boolean fieldsEmpty(){

        if(email.getText().toString().isEmpty()){
            email.setError("Please fill your Email");
            return true;
        } if (password.getText().toString().isEmpty()){
            password.setError("Please fill your password");
            return true;
            }  if (confirmpassword.getText().toString().isEmpty()){
            confirmpassword.setError("Please confirm your password");
            return true;
        } if (!confirmpassword.getText().toString().equals(password.getText().toString())){
            confirmpassword.setError("Please match your password");
            return true;
        } else {
            return false;
        }

    }
}