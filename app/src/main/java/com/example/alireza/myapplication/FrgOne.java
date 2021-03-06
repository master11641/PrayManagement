package com.example.alireza.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.example.alireza.myapplication.adapters.AdapterProduct;
import com.example.alireza.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrgOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrgOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgOne extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Product product;
    ListView lv;
    private ArrayList<Product> list;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private long categoryID;
    private OnFragmentInteractionListener mListener;

    public FrgOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryID Parameter 1.

     * @return A new instance of fragment FrgOne.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgOne newInstance(long categoryID) {
        FrgOne fragment = new FrgOne();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, categoryID);
      //  args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryID = getArguments().getLong(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.product = new Product();
        this.list = new ArrayList<Product>();
        lv = (ListView)  getView().findViewById(R.id.lv);
        reloadData();
        Product[] countries = list.toArray(new Product[list.size()]);
        // Toast.makeText(this,String.valueOf(this.list.size())  , Toast.LENGTH_SHORT).show();
        //  Toast.makeText(this,this.list.get(0).getProductName() , Toast.LENGTH_SHORT).show();
        //  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values)

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, countries);

        AdapterProduct adbPerson;
        //ArrayList<Person> myListItems  = new ArrayList<Person>();
        //then populate myListItems
        adbPerson = new AdapterProduct( getActivity() , 0, list);
        lv.setAdapter(adbPerson);
    }

    private void reloadData() {
        List<Product> ls;
        if(categoryID==0){
             ls = new Select().from(Product.class).execute();
        }else{
            ls = new Select().from(Product.class).where("Category = ?", categoryID).execute();

        }

        list.clear();
        list.addAll(ls);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_one, container, false);
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
