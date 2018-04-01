package com.example.alireza.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alireza.myapplication.model.Product;
import com.example.alireza.myapplication.utility.ListTypeSerializer;
import com.example.alireza.myapplication.utility.httpHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rupins.drawercardbehaviour.CardDrawerLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements FrgOne.OnFragmentInteractionListener, frgAddPray.OnFragmentInteractionListener
,FrgDetails.OnFragmentInteractionListener{

    ListView mListView;
    String[] mobileBrands;
    EditText txtProductName;
    Button btnInsert;
    Toolbar mToolbar;
    Button btnShow;
    Product product;
    CardDrawerLayout drawer;
    NavigationView navigationView;
    ImageButton imgButtonShowMenu;
    ImageButton imgButtonHome;
    ImageButton imageGetFromServer;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        imgButtonShowMenu = findViewById(R.id.showMenues);
        drawer = (CardDrawerLayout) findViewById(R.id.drawer_layout);
        drawer.useCustomBehavior(Gravity.START); //assign custom behavior for "Right" drawer
        navigationView = (NavigationView) findViewById(R.id.nav_view_notification);
        navigationView.setBackgroundColor(getResources().getColor(R.color.White));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btnInsert = (Button) findViewById(R.id.btnAdd);
        btnShow = (Button) findViewById(R.id.btnList);

/*****************************Click handler for btnInsert button***************************/
        btnInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                frgAddPray frg1 = frgAddPray.newInstance((long) 0);
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder, frg1);
                ft.commit();
                changeDrawerStatus();
            }
        });

        imgButtonHome = findViewById(R.id.imageButtonHome);
        imgButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListFragment(false);
            }
        });
        imageGetFromServer = findViewById(R.id.imageGetFromServer);
        imageGetFromServer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                final PrettyDialog dialog = new PrettyDialog(MainActivity.this)
                        .setTitle("پیغام!")
                        .setMessage(" نیاز به بارگیری اطلاعات از سرور است .آیا مایل به ادامه انجام عملیات هستید ؟");
                dialog.show();
                dialog.addButton(
                        "بلی",     // button text
                        R.color.pdlg_color_white,  // button text color
                        R.color.pdlg_color_green,  // button background color
                        new PrettyDialogCallback() {  // button OnClick listener
                            @Override
                            public void onClick() {
                                httpHandler http = new httpHandler();
                                String result = null;
                                try {
                                    result = http.run("http://192.168.43.33:8080/api/prays");
                                    ListTypeSerializer ts = new ListTypeSerializer();
                                    List<Product> products = (List<Product>) ts.deserialize(result);
                                  //  btnInsert.setText(result);//برای لاگ برگشت اطلاعات از سرور
                                    // Gson gson = new Gson();
                                    //  List<Product>  products = gson.fromJson(result, new TypeToken<List<Product>>(){}.getType());
                                    //Toast.makeText(MainActivity.this, products.get(0).getProductName(), Toast.LENGTH_SHORT).show();
                                    addPrays(products);
                                    addListFragment(false);
                                    dialog.dismiss();
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "خطا", Toast.LENGTH_SHORT).show();
                                    btnInsert.setText(e.getMessage());
                                    e.printStackTrace();
                                }
                            }//onClick
                        }
                );//dialog.addButton
                dialog.addButton(
                        "خیر",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                dialog.dismiss();
                            }
                        }
                );

            }
        });


        btnShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           /*     Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                startActivity(intent);*/
                addListFragment(true);

            }
        });

        imgButtonShowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDrawerStatus();
            }
        });
  /*      btnListPrayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FrgOne frg1 = new FrgOne();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder, frg1);
                ft.commit();

            }
        });
*/
        // mListView = (ListView) findViewById(R.id.list_view);
        // mobileBrands = new String[] {"LG", "Samsung", "Apple", "Sony", "Huawei", "HTC", "Motorola",
        //    "ZTE", "Nokia", "ASUS", "Alcatel", "Xiaomi", "Archos", "Lenovo", "OnePlus", "OPPO"};

        // ArrayAdapter<String> mAdapter =
        // new ArrayAdapter<String>(this, R.layout.list_item, R.id.text_view, mobileBrands );
//
        //mListView.setAdapter(mAdapter);
        addListFragment(false);
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
   /*     if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        }else{
            drawer.openDrawer(Gravity.RIGHT);
        }*/

        return true;
    }

    private void addListFragment(boolean changeDrawer) {
        FrgOne frg1 = new FrgOne();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frg_holder, frg1);
        ft.commit();
        if (changeDrawer) {
            changeDrawerStatus();
        }

    }

    private void changeDrawerStatus() {
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START);
        } else {
            drawer.openDrawer(Gravity.START);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void addPrays(List<Product> products) {
        for (Product p : products) {
            Product temp = new Product();
            temp.setProductName(p.getProductName());
            temp.setProductDescription(p.getProductDescription());
            temp.save();
        }
    }

}