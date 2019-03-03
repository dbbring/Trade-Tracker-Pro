package com.example.tradetrackerpro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {

    private int mLayout;
    /*
    @descrip - Constructor. Initial layout Id is the home fragment.
     */
    public BaseFragment(){
        mLayout = R.layout.trade_tracker_activity;
    }

    public int getLayout() {
        return mLayout;
    }
    /*
    @params - Layout resource ID of the Fragment to be displayed
    @descrip - On initial initialization home is the default layout. Be sure to set the layout you need.
     */
    public void setLayout(int layout) {
        mLayout = layout;
    }
    /*
    @descrip - Override fragment's onCreateView and inflate our set layout id.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(mLayout, container, false);
        return view;
    }
}
