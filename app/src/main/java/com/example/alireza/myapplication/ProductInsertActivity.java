package com.example.alireza.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alireza.myapplication.model.Product;

public class ProductInsertActivity extends AppCompatActivity {
    EditText txtProductName;
    Button btnInsert;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_insert);
        txtProductName= (EditText) findViewById(R.id.txtProductName1);
        btnInsert=(Button) findViewById(R.id.btnAddProduct);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                   product = new Product();
                 String productName = txtProductName.getText().toString();
                product.setProductName(productName);
                product.save();
                Toast.makeText(getApplicationContext(), "رکورد با موفقیت درج شد .", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductInsertActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
