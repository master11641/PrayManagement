package com.example.alireza.myapplication;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.alireza.myapplication.model.Category;
import com.example.alireza.myapplication.model.Product;
import com.example.alireza.myapplication.utility.Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrgCategoryInsert.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrgCategoryInsert#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgCategoryInsert extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int PICK_IMAGE = 1;
    // TODO: Rename and change types of parameters
    private long categoryID;
    EditText txtCategoryName;
    ImageButton imgBtnImageSrc;

    Button btnInsert;
    Category currentCategory;
    private OnFragmentInteractionListener mListener;

    public FrgCategoryInsert() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryID Parameter 1.
     * @return A new instance of fragment FrgCategoryInsert.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgCategoryInsert newInstance(long categoryID) {
        FrgCategoryInsert fragment = new FrgCategoryInsert();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, categoryID);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryID = getArguments().getLong(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_category_insert, container, false);
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtCategoryName = (EditText) getView().findViewById(R.id.txtCategoryName);
        this.loadCategory();
        imgBtnImageSrc = getView().findViewById(R.id.imgBtnImagSrc);
        btnInsert = getView().findViewById(R.id.btnAddPCategory);
        if (categoryID == 0) {
            btnInsert.setText("درج گروه");
            btnInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String categoryName = txtCategoryName.getText().toString();
                    currentCategory.setName(categoryName);
                    currentCategory.save();
                goToCategotyFragment();
                }
            });
        } else {
            btnInsert.setText("ویرایش گروه");
            this.txtCategoryName.setText(this.currentCategory.getName());
            if (this.currentCategory.getImgSrc() != "" && this.currentCategory.getImgSrc() != null) {

                if(Helper.GetBimapFromPath(this.currentCategory.getImgSrc())!=null){
                    imgBtnImageSrc.setImageBitmap(Helper.GetBimapFromPath(this.currentCategory.getImgSrc()));
                }else{
                    Toast.makeText(getContext(), "آدرس معتبر نیست", Toast.LENGTH_LONG).show();
                }
             //   Helper.GetBimapFromPath(this.currentCategory.getImgSrc());

            } else {
                Toast.makeText(getContext(), "آدرس گروه در بانک خالی است", Toast.LENGTH_LONG).show();
            }

            btnInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentCategory.setName(txtCategoryName.getText().toString());
                    currentCategory.save();
                    goToCategotyFragment();
                }
            });


        }


        imgBtnImageSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
    }

    private void goToCategotyFragment() {
        FrgCategoryList frg = new FrgCategoryList();
        android.support.v4.app.FragmentManager fm = ((FragmentActivity) getView().getContext()).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frg_holder, frg);
        ft.commit();
    }

    private void loadCategory() {

        // Check if our Car saved correctly
        boolean exists =
                new Select()
                        .from(Category.class) // Specify the table to search
                        .where("ID = ?", categoryID) // search criteria
                        .exists(); // Simply ask if it exists

        if (exists) {

            // Load the car from the database
            currentCategory =
                    new Select()
                            .from(Category.class) // Specify the table to search
                            .where("ID = ?", categoryID) // search criteria
                            .executeSingle(); // return only the first match


            // TODO: Set the TextView to display the formatted json object

        } else {
            this.currentCategory = new Category();
            //  Log.e(Tag, "loadCar car " + SERIAL_NUMBER + " does not exist!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    imgBtnImageSrc.setImageBitmap(bitmap);
                    this.currentCategory.setImgSrc( Helper.saveToInternalStorage(getContext(), bitmap));

                } catch (IOException e) {
                    Log.i("TAG", "Some exception " + e);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
