package com.example.alireza.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.example.alireza.myapplication.adapters.AdapterCategory;
import com.example.alireza.myapplication.adapters.AdapterCategoryForSelect;
import com.example.alireza.myapplication.model.Category;

import java.util.ArrayList;
import java.util.List;


public class FrgCategorySelect extends DialogFragment {
    int mNum;
    private Category category;
    private ArrayList<Category> list;
    ListView lv;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static FrgCategorySelect newInstance(int num) {
        FrgCategorySelect f = new FrgCategorySelect();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frg_category_select, container, false);

    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.category = new Category();
        this.list = new ArrayList<Category>();
        lv = (ListView)  getView().findViewById(R.id.lvCategorySelect);
        reloadData();
        AdapterCategoryForSelect adbCategory = new AdapterCategoryForSelect( getContext() ,  list,true);
        lv.setAdapter(adbCategory);
    }
    private void reloadData() {
        List<Category> ls = new Select().from(Category.class).execute();
        list.clear();
        list.addAll(ls);

    }
}
