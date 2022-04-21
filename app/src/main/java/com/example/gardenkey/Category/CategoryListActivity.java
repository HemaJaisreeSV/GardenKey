package com.example.gardenkey.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.gardenkey.R;

public class CategoryListActivity extends AppCompatActivity
{
    private ImageView backBtn;
    private Button cVegetableBtn, cFruitBtn, cFloweringBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        backBtn = (ImageView) findViewById(R.id.arrow);
        cVegetableBtn = (Button) findViewById(R.id.men_btn);
        cFruitBtn = (Button) findViewById(R.id.women_btn);
        cFloweringBtn = (Button) findViewById(R.id.kids_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListActivity.super.onBackPressed();
            }
        });

        cVegetableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryListActivity.this, VegetableActivity.class);
                startActivity(intent);
            }
        });

        cFruitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryListActivity.this, FruitActivity.class);
                startActivity(intent);
            }
        });

        cFloweringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryListActivity.this, FloweringActivity.class);
                startActivity(intent);
            }
        });

    }
}
