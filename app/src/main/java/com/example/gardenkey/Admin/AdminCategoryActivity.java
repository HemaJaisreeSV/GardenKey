package com.example.gardenkey.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gardenkey.HomeActivity;
import com.example.gardenkey.MainActivity;
import com.example.gardenkey.R;

public class AdminCategoryActivity extends AppCompatActivity
{
    private ImageView vegetable,fruit,flowering,small_pot;
    private ImageView non_flowering,big_pot,chemical,organic;
    private ImageView fungi,machinery, soil,insect;
    private Button LogoutBtn, CheckOrdersBtn, maintainProductsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        Toast.makeText(this,"Welcome to admin panel.",Toast.LENGTH_SHORT).show();

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);
        maintainProductsBtn = (Button) findViewById(R.id.maintain_btn);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });

        vegetable=(ImageView) findViewById(R.id.vegetable);
        fruit=(ImageView) findViewById(R.id.fruit);
        flowering=(ImageView) findViewById(R.id.flowering);
        non_flowering=(ImageView) findViewById(R.id.non_flowering);

        small_pot=(ImageView) findViewById(R.id.small_pot);
        big_pot=(ImageView) findViewById(R.id.big_pot);
        chemical=(ImageView) findViewById(R.id.chemical);
        organic=(ImageView) findViewById(R.id.organic);

        fungi=(ImageView) findViewById(R.id.fungi);
        machinery=(ImageView) findViewById(R.id.machinery);
        soil=(ImageView) findViewById(R.id.soil);
        insect=(ImageView) findViewById(R.id.insect);

        vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddProductActivity.class);
                intent.putExtra("Category","Vegetable Seeds");
                startActivity(intent);
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Fruit Seeds");
                startActivity(intent);
            }
        });

        flowering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Flowering Plants");
                startActivity(intent);
            }
        });

        non_flowering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Non Flowering Plants");
                startActivity(intent);
            }
        });

        small_pot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Small Pots");
                startActivity(intent);
            }
        });

        big_pot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Big Pots");
                startActivity(intent);
            }
        });

        chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Chemical Fertilizers");
                startActivity(intent);
            }
        });

        organic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Organic Fertilizers");
                startActivity(intent);
            }
        });

        insect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Insecticides");
                startActivity(intent);
            }
        });

        fungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Fungicides");
                startActivity(intent);
            }
        });

        machinery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Machinery");
                startActivity(intent);
            }
        });

        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("Category","Soil");
                startActivity(intent);
            }
        });

    }
}
