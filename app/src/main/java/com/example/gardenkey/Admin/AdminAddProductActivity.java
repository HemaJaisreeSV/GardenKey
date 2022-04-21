package com.example.gardenkey.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gardenkey.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class AdminAddProductActivity extends AppCompatActivity
{
    private String CategoryName, Pname, Details, Price, saveDate, saveTime, ProductRandomKey, downloadImgUrl;
    private Button AddProductBtn;
    private ImageView ProductImg;
    private EditText ProductName,ProductDetails,ProductPrice;
    private Uri ImageUri;
    private StorageReference ProductImgRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loading;

    private static final int GalleryPick=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        CategoryName=getIntent().getExtras().get("Category").toString();
        ProductImgRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef= FirebaseDatabase.getInstance().getReference().child("Products");
        loading= new ProgressDialog(this);

        AddProductBtn=(Button) findViewById(R.id.add_new_product);
        ProductImg=(ImageView) findViewById(R.id.select_product_image);
        ProductName=(EditText) findViewById(R.id.product_name);
        ProductDetails=(EditText) findViewById(R.id.product_details);
        ProductPrice=(EditText) findViewById(R.id.product_price);

        ProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        AddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });

    }

    private void ValidateProductData()
    {
        Details=ProductDetails.getText().toString();
        Pname=ProductName.getText().toString();
        Price=ProductPrice.getText().toString();

        if(ImageUri==null)
        {
            Toast.makeText(this,"Product Image is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Details))
        {
            Toast.makeText(this,"Write about product details",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this,"Write about product price",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this,"Write about product name",Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }



    private void StoreProductInformation()
    {
        loading.setTitle("Add New Product");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd,yyyy");
        saveDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveTime=currentTime.format(calendar.getTime());

        ProductRandomKey=saveDate+" "+saveTime;

        final StorageReference filePath= ProductImgRef.child(ImageUri.getLastPathSegment()+ProductRandomKey);
        final UploadTask Utask=filePath.putFile(ImageUri);

        Utask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Did not enter Enter");
                String msg=e.toString();
                Toast.makeText(AdminAddProductActivity.this, "ERROR: "+msg,Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Entereddddddddddddd");
                Toast.makeText(AdminAddProductActivity.this, "Product Image Uploaded.",Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask=Utask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImgUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImgUrl=task.getResult().toString();
                            Toast.makeText(AdminAddProductActivity.this, "Got Product Image Url.",Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    /*    Utask.
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                System.out.println("Entereddddddddddddd");


                Toast.makeText(AdminAddProductActivity.this, "Product Image Uploaded.", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = Utask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImgUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                });
            }})
               .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImgUrl=task.getResult().toString();
                            Toast.makeText(AdminAddProductActivity.this, "Got Product Image Url.",Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Did not enter Enter");
                        String msg=e.toString();
                        Toast.makeText(AdminAddProductActivity.this, "ERROR: "+msg,Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });  */

            }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> ProductMap =new HashMap<>();
        ProductMap.put("pid",ProductRandomKey);
        ProductMap.put("date",saveDate);
        ProductMap.put("time",saveTime);
        ProductMap.put("details",Details);
        ProductMap.put("image",downloadImgUrl);
        ProductMap.put("category",CategoryName);
        ProductMap.put("price",Price);
        ProductMap.put("pname",Pname);

        ProductRef.child(ProductRandomKey).updateChildren(ProductMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(AdminAddProductActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);

                    loading.dismiss();
                    Toast.makeText(AdminAddProductActivity.this, "Product is added.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loading.dismiss();
                    String msg=task.getException().toString();
                    Toast.makeText(AdminAddProductActivity.this, "ERROR: "+msg,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void OpenGallery()
    {
        Intent galleryIntent= new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==GalleryPick && data!=null)
        {
           ImageUri=data.getData();
           ProductImg.setImageURI(ImageUri);
        }
    }
}



/*
public class AdminAddProductActivity extends AppCompatActivity
{

    private ImageView select_product_image;
    private EditText product_name,product_details,product_price;
    private Button add_new_product;

    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;

    private String[] storagePermissions;
    private Uri image_uri;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        select_product_image = findViewById(R.id.select_product_image);
        product_name = findViewById(R.id.product_name);
        product_details = findViewById(R.id.product_details);
        product_price = findViewById(R.id.product_price);
        add_new_product = findViewById(R.id.add_new_product);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        select_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });


    }

    private String productName, productDetails, productPrice;

    private void inputData() {
        productName = product_name.getText().toString().trim();
        productDetails = product_details.getText().toString().trim();
        productPrice = product_price.getText().toString().trim();

        if(TextUtils.isEmpty(productName)){
            Toast.makeText(this,"Product name is required....", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(productDetails)){
            Toast.makeText(this,"Product details are required....", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(productPrice)){
            Toast.makeText(this,"Product price is required....", Toast.LENGTH_SHORT).show();
            return;
        }

        addProduct();
    }

    private void addProduct() {
        progressDialog.setMessage("Adding Product...");
        progressDialog.show();

        final String timestamp = ""+System.currentTimeMillis();

        if(image_uri == null){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("productId", ""+timestamp);
            hashMap.put("productName", ""+productName);
            hashMap.put("productDetails", ""+productDetails);
            hashMap.put("productPrice", ""+productPrice);
            hashMap.put("productImage", "");
            hashMap.put("timestamp", ""+timestamp);
            hashMap.put("uid", ""+firebaseAuth.getUid());

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getUid()).child("Products").child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminAddProductActivity.this,"Product added...", Toast.LENGTH_SHORT).show();
                            clearData();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminAddProductActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{
            String filePathAndName = "product_images/" + "" + timestamp;

            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                         while(!uriTask.isSuccessful());
                         Uri downloadImageUri = uriTask.getResult();

                         if(uriTask.isSuccessful()){
                             HashMap<String,Object> hashMap = new HashMap<>();
                             hashMap.put("productId", ""+timestamp);
                             hashMap.put("productName", ""+productName);
                             hashMap.put("productDetails", ""+productDetails);
                             hashMap.put("productPrice", ""+productPrice);
                             hashMap.put("productImage", ""+downloadImageUri);
                             hashMap.put("timestamp", ""+timestamp);
                             hashMap.put("uid", ""+firebaseAuth.getUid());

                             DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                             reference.child(firebaseAuth.getUid()).child("Products").child(timestamp).setValue(hashMap)
                                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {
                                             progressDialog.dismiss();
                                             Toast.makeText(AdminAddProductActivity.this,"Product added...", Toast.LENGTH_SHORT).show();
                                             clearData();
                                         }
                                     })
                                     .addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {
                                             progressDialog.dismiss();
                                             Toast.makeText(AdminAddProductActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                         }
                                     });
                         }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminAddProductActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
    private void clearData(){
        select_product_image.setImageResource(R.drawable.select_img);
        product_name.setText("");
        product_details.setText("");
        product_price.setText("");
        image_uri = null;
    }

    private void showImagePickDialog() {
        String[] options = {"Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            if(checkStoragePermission()){
                                pickFromGallery();
                            }
                            else{
                                requestStoragePermission();
                            }
                        }

                    }
                })
                .show();
    }

    private void pickFromGallery(){
        Intent intent =  new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions,STORAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(this,"Storage permission is required....", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(resultCode == IMAGE_PICK_GALLERY_CODE){
                image_uri = data.getData();
                select_product_image.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

 */