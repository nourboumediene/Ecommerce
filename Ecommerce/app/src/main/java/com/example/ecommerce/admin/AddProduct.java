package com.example.ecommerce.admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddProduct extends AppCompatActivity {
    ImageView selectImage;
    EditText productName, productDescription, productPrice;
    TextView selectCategory;
    Uri imageFile;
    StorageReference productImageRef;
    DatabaseReference productRef;
    Button addProduct;
    String imageURL;
    String [] categories;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initializationOfFields();
        //variable to get image from gallery
        ActivityResultLauncher<Intent> openGalleryResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageFile = data.getData();
                            selectImage.setImageURI(imageFile);
                        }

                    }
                }
        );
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryResult.launch(openGallery());
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){
                    uploadImageToGoogleCloud();
                }
            }
        });
        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(AddProduct.this);
                mbuilder.setTitle("Please select the category");
                mbuilder.setSingleChoiceItems(categories, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectCategory.setText(categories[which]);
                        dialog.dismiss();
                    }
                });
                mbuilder.show();
            }
        });
    }


    private void initializationOfFields() {
        selectImage = findViewById(R.id.select_image);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_desc);
        productPrice = findViewById(R.id.product_price);
        selectCategory = findViewById(R.id.select_category);
        productImageRef = FirebaseStorage.getInstance().getReference().child("product images");
    addProduct= findViewById(R.id.add_product);
    categories = new String[]{"Gmaer", "Student","Developer","Workstation","Notebook","Convertible","Professional"};
    loading = new ProgressDialog(this);
    loading.setTitle("Uploading your product");
    loading.setMessage("Please wait while we are adding your product");
    }

    private Intent openGallery() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        return i;
    }
    private String generateID(){
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyMMdd");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calForDate.getTime());
        return saveCurrentDate+saveCurrentTime;


    }
    private void saveProductsDataIntoDb(Product product){
        productRef.setValue(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loading.dismiss();
                            Toast.makeText(AddProduct.this,"Product added",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            loading.dismiss();
                            Toast.makeText(AddProduct.this,"Error",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

private boolean checkFields(){
    if(productName.getText().toString().isEmpty()){
        productName.setError("Please set product name");
        return false;
    }else if (productDescription.getText().toString().isEmpty()){
        productDescription.setError("Please set description");
        return false;
    }else if (productPrice.getText().toString().isEmpty()){
        productPrice.setError("Please select price");
        return false;
    }else if(imageFile==null){
        Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        return false;
    }else{
        return true;
    }
}
private void uploadImageToGoogleCloud(){
        loading.show();
        String id = generateID();
        productImageRef.child(id+".jpeg")
                .putFile(imageFile)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            productImageRef.child(id+"jpeg")
                                    .getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageURL = uri.toString();
                                            productRef= FirebaseDatabase.getInstance(getString(R.string.reference)).getReference()
                                                    .child("Products");
                                            productRef = productRef.push();
                                            String id = productRef.getKey();
                                            Product product =new Product(productName.getText().toString(),
                                                    productDescription.getText().toString(),
                                                    imageURL,selectCategory.getText().toString(),id,Integer.valueOf(productPrice.getText().toString()));
                                            saveProductsDataIntoDb(product);
                                        }
                                    });
                        }else{
                            loading.dismiss();
                            Toast.makeText(AddProduct.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
}


}