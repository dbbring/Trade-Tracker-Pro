package com.example.tradetrackerpro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class TradeEntryFragment extends BaseFragment {
    private Button mDateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.entry, container, false);
        mDateButton = (Button) view.findViewById(R.id.dateBtn);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"YAY",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
