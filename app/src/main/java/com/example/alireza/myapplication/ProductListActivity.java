package com.example.alireza.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.alireza.myapplication.adapters.AdapterProduct;
import com.example.alireza.myapplication.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.*;

public class ProductListActivity extends AppCompatActivity {
    Product product;
    ListView lv;
    private ArrayList<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        this.product = new Product();
        this.list = new ArrayList<Product>();
        lv = (ListView) findViewById(R.id.lv);
        reloadData();
        Product[] countries = list.toArray(new Product[list.size()]);
        // Toast.makeText(this,String.valueOf(this.list.size())  , Toast.LENGTH_SHORT).show();
        //  Toast.makeText(this,this.list.get(0).getProductName() , Toast.LENGTH_SHORT).show();
        //  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values)

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, countries);

        AdapterProduct adbPerson;
        //ArrayList<Person> myListItems  = new ArrayList<Person>();
        //then populate myListItems
        adbPerson = new AdapterProduct(this, 0, list);

        lv.setAdapter(adbPerson);
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void reloadData() {
        List<Product> ls = new Select().from(Product.class).execute();
        list.clear();
        list.addAll(ls);
        // Collections.reverse(list);
        // adapter.notifyDataSetChanged();
    }
}
