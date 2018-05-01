package com.example.alireza.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alireza.myapplication.FrgCategoryInsert;
import com.example.alireza.myapplication.FrgOne;
import com.example.alireza.myapplication.MainActivity;
import com.example.alireza.myapplication.R;
import com.example.alireza.myapplication.frgAddPray;
import com.example.alireza.myapplication.model.Category;
import com.example.alireza.myapplication.utility.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alireza on 4/13/2018.
 */

public class AdapterCategoryForSelect extends ArrayAdapter<Category> {
    private Context mContext;
    private List<Category> categoryList = new ArrayList<>();
private boolean justImage;
    public AdapterCategoryForSelect(@NonNull Context context, ArrayList<Category> list,boolean justImage) {
        super(context, 0 , list);
        mContext = context;
        categoryList = list;
        this.justImage=justImage;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View listItem = convertView;
        final Category currentCategory = categoryList.get(position);
        if(listItem == null){
            if(this.justImage==false){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.category_select,parent,false);
                TextView txtViewProductCategory = (TextView) listItem.findViewById(R.id.textViewCategoryName);
                txtViewProductCategory.setText(currentCategory.getName());
            }else{
                listItem = LayoutInflater.from(mContext).inflate(R.layout.category_select_just_image,parent,false);
            }
        }
        ImageView imageViewCategoryOperation = listItem.findViewById(R.id.imageViewImageSrc);
        imageViewCategoryOperation.setImageBitmap(Helper.GetBimapFromPath(currentCategory.getImgSrc()));
        imageViewCategoryOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,currentCategory.getId().toString(),Toast.LENGTH_LONG).show();
                FrgOne frg1 = FrgOne.newInstance(currentCategory.getId());
                android.support.v4.app.FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder, frg1);
                ft.commit();
            }
        });
        return listItem;
    }

}
