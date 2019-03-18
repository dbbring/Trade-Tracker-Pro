package com.example.tradetrackerpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TradeEntryFragment extends BaseFragment {
    private Button mDateButton;
    private Button mSubmitButton;
    private Trade mTrade;
    private EditText mTicker;
    private EditText mEntryPrice;
    private EditText mExitPrice;
    private EditText mAcctNum;
    private EditText mOutcomeCat;
    private EditText mSize;
    private EditText mEntryDescrip;
    private EditText mExitDescrip;
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.entry, container, false);

        mTicker =(EditText) view.findViewById(R.id.entryTicker);
        mEntryPrice =(EditText) view.findViewById(R.id.entryEntryPrice);
        mExitPrice =(EditText) view.findViewById(R.id.entryExitPrice);
        mSize =(EditText) view.findViewById(R.id.entrySize);
        mAcctNum =(EditText) view.findViewById(R.id.acctNum);
        mOutcomeCat =(EditText) view.findViewById(R.id.outcomeCat);
        mEntryDescrip =(EditText) view.findViewById(R.id.tradeEntryDescrip);
        mExitDescrip =(EditText) view.findViewById(R.id.tradeExitDescrip);

        mDateButton = (Button) view.findViewById(R.id.dateBtn);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(date);
                dialog.setTargetFragment(TradeEntryFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSubmitButton = (Button) view.findViewById(R.id.saveBtn);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrade = new Trade();

                mTrade.setTicker(mTicker.getText().toString());
                mTrade.setEntryPrice(Double.parseDouble(mEntryPrice.getText().toString()));
                mTrade.setExitPrice(Double.parseDouble(mExitPrice.getText().toString()));
                mTrade.setPositionSize(Integer.parseInt(mSize.getText().toString()));
                mTrade.setOutcomeCat(mOutcomeCat.getText().toString());
                mTrade.setAcctNum(mAcctNum.getText().toString());
                mTrade.setEntryTradeDescrip(mEntryDescrip.getText().toString());
                mTrade.setExitTradeDescrip(mExitDescrip.getText().toString());
                mTrade.setDate(mDateButton.getText().toString());

                TradeEntries.get(getActivity()).addTrade(mTrade);
                Toast.makeText(getActivity(),R.string.newEntrySuccessful,Toast.LENGTH_SHORT).show();

                mTicker.setText("");
                mEntryPrice.setText("");
                mExitPrice.setText("");
                mSize.setText("");
                mDateButton.setText("Enter Date");
                mAcctNum.setText("");
                mOutcomeCat.setText("");
                mEntryDescrip.setText("");
                mExitDescrip.setText("");

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
            String strDate = dateFormat.format(date);
            mDateButton.setText(strDate);
        }
    }


}
