package com.example.alireza.myapplication.adapters;
import android.app.Activity;
import android.app.LauncherActivity;
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
import com.example.alireza.myapplication.MainActivity;
import com.example.alireza.myapplication.R;
import com.example.alireza.myapplication.frgAddPray;
import com.example.alireza.myapplication.model.Category;
import com.example.alireza.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;


public class AdapterCategory extends ArrayAdapter<Category> {

    private Context mContext;
    private List<Category> categoryList = new ArrayList<>();

    public AdapterCategory(@NonNull Context context,  ArrayList<Category> list) {
        super(context, 0 , list);
        mContext = context;
        categoryList = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.category_list_template,parent,false);
        Category currentMovie = categoryList.get(position);
        TextView txtViewProductCategory = (TextView) listItem.findViewById(R.id.productCategory);
        txtViewProductCategory.setText(currentMovie.getName());


     ImageView imageViewCategoryOperation = listItem.findViewById(R.id.imageViewCategoryOperation);


        final PopupMenu pm = new PopupMenu(mContext, imageViewCategoryOperation);
        pm.getMenuInflater().inflate(R.menu.popup_menu_product, pm.getMenu());
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
              //   Toast.makeText(mContext,  item.getTitle(), Toast.LENGTH_SHORT).show();

                FrameLayout crdLayout =parent.findViewById(R.id.frgCategoryInsert);
                onOptionsItemSelected(item, position,crdLayout);

                return true;
            }
        });
        imageViewCategoryOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pm.show();
            }
        });


        return listItem;
    }



    public void onOptionsItemSelected(MenuItem item, final int position,final FrameLayout layout) {
        final Category currentItem = (Category) getItem(position);
        int id = item.getItemId();
        final Category category = new Category();
        if (id == R.id.edit_popup) {
            FrgCategoryInsert frg1 = FrgCategoryInsert.newInstance(currentItem.getId());
            android.support.v4.app.FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frg_holder, frg1);
            ft.commit();
        } else if (id == R.id.delete_popup) {
            if(mContext instanceof MainActivity){
                Toast.makeText(mContext,  item.getTitle(), Toast.LENGTH_SHORT).show();
                Activity activity=(MainActivity)mContext;
                FrameLayout crdLayout = activity.findViewById(R.id.frgCategoryList);
                final Snackbar snkbr = Snackbar.make(crdLayout, "آیا از حذف رکورد اطمینان دارید ؟",
                        Snackbar.LENGTH_LONG);
                snkbr.show();
                snkbr.setAction("بلی", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentItem.delete();
                        currentItem.save();
                        remove(currentItem);
                        notifyDataSetChanged();
                        Toast.makeText(mContext,   currentItem.getName() + "با موفقیت حذف شد .", Toast.LENGTH_SHORT).show();
                        snkbr.dismiss();


                    }
                });
            }


        }


    }

}