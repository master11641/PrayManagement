package com.example.alireza.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.alireza.myapplication.model.Product;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrgDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrgDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    Product product;
    // TODO: Rename and change types of parameters
    private Long productID;

private EditText txtViewDetails;
    // TODO: Rename and change types of parameters
    private String mParam1;


    private OnFragmentInteractionListener mListener;

    public FrgDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param productID Parameter 1.

     * @return A new instance of fragment FrgDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgDetails newInstance(Long productID) {
        FrgDetails fragment = new FrgDetails();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_details, container, false);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        txtViewDetails = getView().findViewById(R.id.txtViewDetails);
        loadPray();
        txtViewDetails.setText(product.getProductDescription());

    }//onViewCreated end
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
