package com.example.alireza.myapplication.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
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

import com.example.alireza.myapplication.FrgDetails;
import com.example.alireza.myapplication.ProductInsertActivity;
import com.example.alireza.myapplication.ProductListActivity;
import com.example.alireza.myapplication.R;
import com.example.alireza.myapplication.frgAddPray;
import com.example.alireza.myapplication.model.Product;

import java.util.ArrayList;


public class AdapterProduct extends ArrayAdapter<Product> {
    private Activity activity;
    private ArrayList<Product> lPerson;
    private static LayoutInflater inflater = null;
    private Context context;
    private PopupMenu popupMenu;

    public AdapterProduct(Activity activity, int textViewResourceId, ArrayList<Product> _lPerson) {
        super(activity, textViewResourceId, _lPerson);
        try {
            this.activity = activity;
            this.lPerson = _lPerson;
            this.context = activity.getBaseContext();
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lPerson.size();
    }

    public Product getItem(Product position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public ImageView imageViewOperation;
        public ImageView imageViewBookmark;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final View finalView = convertView;
        final ViewHolder holder;
        try {
            if (true) {
                vi = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.display_name = (TextView) vi.findViewById(R.id.product_name);
                holder.imageViewOperation = vi.findViewById(R.id.imageViewOperation);
                holder.imageViewBookmark = vi.findViewById(R.id.imageViewBookmark);

               final  Product currentItem = (Product) getItem(position);
                if(currentItem.getBookmark()==true){
                    holder.imageViewBookmark .setImageResource(R.drawable.ic_turned_in_black_24dp);
                }else{
                    holder.imageViewBookmark .setImageResource(R.drawable.ic_turned_in_not_black_24dp);
                }

                final PopupMenu pm = new PopupMenu(context, holder.imageViewOperation);
                pm.getMenuInflater().inflate(R.menu.popup_menu_product, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        // Toast.makeText(context, currentItem.getId() + "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        onOptionsItemSelected(item, position);
                        return true;
                    }
                });
                holder.imageViewOperation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pm.show();
                    }
                });
                holder.imageViewBookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBookmarkClick(position,view);
                    }
                });
                holder.display_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FrgDetails frg1 = FrgDetails.newInstance(currentItem.getId());
                        android.support.v4.app.FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frg_holder, frg1);
                        ft.commit();
                    }
                });

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }


            holder.display_name.setText(lPerson.get(position).getProductName());
            // holder.display_number.setText(lProducts.get(position).number);


        } catch (Exception e) {


        }
        return vi;
    }

    public void onOptionsItemSelected(MenuItem item, final int position) {
        final Product currentItem = (Product) getItem(position);
        int id = item.getItemId();
        final Product product = new Product();

        if (id == R.id.edit_popup) {
            frgAddPray frg1 = frgAddPray.newInstance(currentItem.getId());
            android.support.v4.app.FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frg_holder, frg1);
            ft.commit();
        } else if (id == R.id.delete_popup) {
            FrameLayout crdLayout = this.activity.findViewById(R.id.frgListPray);
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
                    Toast.makeText(context,   currentItem.getProductName() + "با موفقیت حذف شد .", Toast.LENGTH_SHORT).show();
                    snkbr.dismiss();


                }
            });


        }


    }
    public void onBookmarkClick(final int position,View view){
        ImageView iv= (ImageView) view;
        final Product currentProduct = (Product) getItem(position);
        currentProduct.setBookmark(!currentProduct.getBookmark());
        currentProduct.save();

        if(currentProduct.getBookmark()){
            iv.setImageResource(R.drawable.ic_turned_in_black_24dp);
            Toast.makeText(context, "به علاقه مندیها اضافه گردید .", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "از علاقه مندیها حذف گردید .", Toast.LENGTH_SHORT).show();
            iv .setImageResource(R.drawable.ic_turned_in_not_black_24dp);
        }
    }
}





