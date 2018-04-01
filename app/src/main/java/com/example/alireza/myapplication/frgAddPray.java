package com.example.alireza.myapplication;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.alireza.myapplication.model.Product;


import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frgAddPray.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frgAddPray#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgAddPray extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText txtProductName;
    EditText txtProductDescription;

    Button btnInsert;
    Product product;
    // TODO: Rename and change types of parameters
    private Long productID;


    private OnFragmentInteractionListener mListener;

    public frgAddPray() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param productID Parameter 1.
     * @return A new instance of fragment frgAddPray.
     */
    // TODO: Rename and change types and number of parameters
    public static frgAddPray newInstance(Long productID) {
        frgAddPray fragment = new frgAddPray();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, productID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productID = getArguments().getLong(ARG_PARAM1);
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        txtProductName = (EditText) getView().findViewById(R.id.txtProductName1);
        txtProductDescription = (EditText) getView().findViewById(R.id.txtProductDescription);
        btnInsert = (Button) getView().findViewById(R.id.btnAddProduct);

        if (productID!=0) {
            // btnInsert.setVisibility(View.INVISIBLE);
            btnInsert.setText("ویرایش دعا");
            loadPray();
          /*  Toast.makeText(getView().getContext(),product.getProductDescription() , Toast.LENGTH_SHORT).show();*/
            txtProductName.setText(product.getProductName());
            txtProductDescription.setText(product.getProductDescription());
            btnInsert.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String productName = txtProductName.getText().toString();
                    String ProductDescription=txtProductDescription.getText().toString();
                    product.setProductName(productName);
                    product.setProductDescription(ProductDescription);
                    product.save();
                    Toast.makeText(getView().getContext(), "رکورد با موفقیت ویرایش شد .", Toast.LENGTH_SHORT).show();
             /*   Intent intent = new Intent(getActivity(), ProductListActivity.class);
                startActivity(intent);*/
                    FrgOne frg1 = new FrgOne();
                    android.support.v4.app.FragmentManager fm =((FragmentActivity)getView().getContext()).getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frg_holder, frg1);
                    ft.commit();
                }
            });//btnInsert OnClickListener close
        }else{
            btnInsert.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    product = new Product();
                    String productName = txtProductName.getText().toString();
                    String ProductDescription=txtProductDescription.getText().toString();
                    product.setProductDescription(ProductDescription);
                    product.setProductName(productName);
                    product.save();
                    Toast.makeText(getView().getContext(), "رکورد با موفقیت درج شد .", Toast.LENGTH_SHORT).show();
             /*   Intent intent = new Intent(getActivity(), ProductListActivity.class);
                startActivity(intent);*/
                    FrgOne frg1 = new FrgOne();
                    MainActivity ma=new MainActivity();
                    android.support.v4.app.FragmentManager fm =((FragmentActivity)getView().getContext()).getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frg_holder, frg1);
                    ft.commit();
                }
            });//btnInsert OnClickListener close
        }
    }//onViewCreated close


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_add_pray, container, false);
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

/*    @Override
    public void onValidationSucceeded() {
        Toast.makeText(getActivity(), "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
    }*/

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


    private void loadPray(){

        // Check if our Car saved correctly
        boolean exists =
                new Select()
                        .from(Product.class) // Specify the table to search
                        .where("ID = ?", productID) // search criteria
                        .exists(); // Simply ask if it exists

        if(exists) {

            // Load the car from the database
            product =
                    new Select()
                            .from(Product.class) // Specify the table to search
                            .where("ID = ?", productID) // search criteria
                            .executeSingle(); // return only the first match

            // TODO: Set the TextView to display the formatted json object

        }
        else{
            //  Log.e(Tag, "loadCar car " + SERIAL_NUMBER + " does not exist!");
        }
    }


}

