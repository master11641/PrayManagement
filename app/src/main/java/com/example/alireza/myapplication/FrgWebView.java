package com.example.alireza.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;



import libs.mjn.prettydialog.PrettyDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrgWebView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrgWebView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgWebView extends Fragment {
    private WebView webView;
   private PrettyDialog dialog1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String link;


    private OnFragmentInteractionListener mListener;

    public FrgWebView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param link Parameter 1.
     * @return A new instance of fragment FrgWebView.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgWebView newInstance(String link) {
        FrgWebView fragment = new FrgWebView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, link);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        webView = (WebView) getActivity().findViewById(R.id.webView);
       // progressBar = (BallView) getActivity().findViewById(R.id.ballViewLoading);


this.dialog1=new PrettyDialog(getContext())
        .setTitle("پیام!")
        .setMessage(" سایت در حال بارگذاری است ، لطفا صبور باشید ");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(90);
        webView.getSettings().setSupportZoom(true);
        dialog1.show();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                dialog1.show();
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                dialog1.dismiss();
            }
        });

        webView.loadUrl(link);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            link = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frg_web_view, container, false);
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
