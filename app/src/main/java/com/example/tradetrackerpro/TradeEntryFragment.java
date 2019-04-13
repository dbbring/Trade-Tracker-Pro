package com.example.tradetrackerpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class TradeEntryFragment extends BaseFragment {
    private Button mDateButton;
    private Button mSubmitButton;
    private Trade mTrade;
    private EditText mTicker;
    private EditText mEntryPrice;
    private EditText mExitPrice;
    private Spinner mAcctNum;
    private Spinner mOutcomeCat;
    private EditText mSize;
    private EditText mEntryDescrip;
    private EditText mExitDescrip;
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private Settings mSettings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.entry, container, false);
        mSettings = Settings.get(getContext());

        mTicker =(EditText) view.findViewById(R.id.entryTicker);
        mEntryPrice =(EditText) view.findViewById(R.id.entryEntryPrice);
        mExitPrice =(EditText) view.findViewById(R.id.entryExitPrice);
        mSize =(EditText) view.findViewById(R.id.entrySize);
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

        mAcctNum =(Spinner) view.findViewById(R.id.acctNum);
        mOutcomeCat =(Spinner) view.findViewById(R.id.outcomeCat);

        String[] acctSelections = {mSettings.getAcct1(), mSettings.getAcct2(),mSettings.getAcct3()};
        String[] catSelections = {mSettings.getCat1(), mSettings.getCat2(), mSettings.getCat3(), mSettings.getCat4(), mSettings.getCat5()};

        ArrayAdapter<String> acctAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, acctSelections);
        acctAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAcctNum.setAdapter(acctAdapter);

        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, catSelections);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOutcomeCat.setAdapter(catAdapter);


        mSubmitButton = (Button) view.findViewById(R.id.saveBtn);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrade = new Trade();

                mTrade.setTicker(mTicker.getText().toString());
                mTrade.setEntryPrice(Double.parseDouble(mEntryPrice.getText().toString()));
                mTrade.setExitPrice(Double.parseDouble(mExitPrice.getText().toString()));
                mTrade.setPositionSize(Integer.parseInt(mSize.getText().toString()));
                mTrade.setOutcomeCat(mOutcomeCat.getSelectedItem().toString());
                mTrade.setAcctNum(mAcctNum.getSelectedItem().toString());
                mTrade.setEntryTradeDescrip(mEntryDescrip.getText().toString());
                mTrade.setExitTradeDescrip(mExitDescrip.getText().toString());
                mTrade.setDate(mDateButton.getText().toString());
                mTrade.setTradeID(new Random().nextInt());

                TradeEntries.get(getActivity()).addTrade(mTrade);
                Toast.makeText(getActivity(),R.string.newEntrySuccessful,Toast.LENGTH_SHORT).show();

                mTicker.setText("");
                mEntryPrice.setText("");
                mExitPrice.setText("");
                mSize.setText("");
                mDateButton.setText("Enter Date");
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
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            String strDate = dateFormat.format(date);
            mDateButton.setText(strDate);
        }
    }


}
