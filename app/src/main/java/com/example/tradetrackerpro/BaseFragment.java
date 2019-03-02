package com.example.tradetrackerpro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {

    private int mLayout;

    public BaseFragment(){
        mLayout = R.layout.trade_tracker_activity;
    }

    public int getLayout() {
        return mLayout;
    }

    public void setLayout(int layout) {
        mLayout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(mLayout, container, false);
        return view;
    }
}
