package com.example.tradetrackerpro;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;


public class TradeEntryDetailFragment extends BaseFragment {
    private final String TRADE_ID = "com.example.tradetrackerpro.trade_id";
    private Trade mTrade;
    private int mTradeID;
    private TextView mTicker;
    private TextView mDate;
    private TextView mEntryPrice;
    private EditText mExitPrice;
    private TextView mSize;
    private TextView mEntryDescrip;
    private EditText mExitDescrip;
    private TextView mNetChange;
    private TextView mAccountNum;
    private TextView mOutcomeCat;
    private Button mDeleteButton;
    private Boolean mTextChangeFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);
        DecimalFormat changeFormatter = new DecimalFormat("#.00");
        mTextChangeFlag = false;
        mTradeID = getArguments().getInt(TRADE_ID);

        mTrade = TradeEntries.get(getContext()).getTrade(mTradeID);
        mTicker = (TextView) view.findViewById(R.id.detailViewTicker);
        mTicker.setText("Ticker: " + mTrade.getTicker());

        mDate = (TextView) view.findViewById(R.id.detailViewDate);
        mDate.setText("Date: " + mTrade.getDate());

        mEntryPrice = (TextView) view.findViewById(R.id.detailViewEntryPrice);
        mEntryPrice.setText("Entry: $ " + mTrade.getEntryPrice());

        mExitPrice = (EditText) view.findViewById(R.id.detailViewExitPrice);
        if(mTrade.getExitPrice() == 0.00) {
            mExitPrice.setEnabled(true);
            mExitPrice.setText("");
        }
        else {
            mExitPrice.setText(mTrade.getExitPrice() + "");
        }
        mExitPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextChangeFlag = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSize = (TextView) view.findViewById(R.id.detailViewSize);
        mSize.setText(mTrade.getPositionSize() + " Shares");

        mEntryDescrip = (TextView) view.findViewById(R.id.detailViewEntryTradeDescrip);
        mEntryDescrip.setText(mTrade.getEntryTradeDescrip());

        // If our exit description is blank, let the user add one
        mExitDescrip = (EditText) view.findViewById(R.id.detailViewExitTradeDescrip);
        if (mTrade.getExitTradeDescrip().equals("")) {
            mExitDescrip.setEnabled(true);
        }
        mExitDescrip.setText(mTrade.getExitTradeDescrip());
        mExitDescrip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextChangeFlag = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNetChange = (TextView) view.findViewById(R.id.detailViewNetChange);
        double change = (mTrade.getExitPrice() - mTrade.getEntryPrice()) * mTrade.getPositionSize();
        String netChange = ( change > 0) ? "+$" + changeFormatter.format(change) : "-$" + changeFormatter.format(change);
        mNetChange.setText(netChange);

        mOutcomeCat = (TextView) view.findViewById(R.id.detailViewCategory);
        mOutcomeCat.setText(mTrade.getOutcomeCat());

        mAccountNum = (TextView) view.findViewById(R.id.detailViewAccount);
        mAccountNum.setText(mTrade.getAcctNum());

        mDeleteButton = (Button) view.findViewById(R.id.detailsDeleteBtn);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TradeEntries.get(getContext()).deleteTrade(mTrade);
                Toast.makeText(getContext(),R.string.entryDeleted,Toast.LENGTH_SHORT).show();

                TradeDetailFragment performanceFrag = new TradeDetailFragment();
                FragmentTransaction transact = getActivity().getSupportFragmentManager().beginTransaction();
                transact.replace(R.id.main_layout, performanceFrag);
                transact.commit();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        // Validate and save if the user has made any changes (check via text changed flag)
         if(mTextChangeFlag) {
            try {
                Validation validator = new Validation();
                double exitPrice = (!validator.isNumeric(mExitPrice.getText().toString())) ? Double.parseDouble(mExitPrice.getText().toString()) : 0.00;
                String exitNote = (validator.isNullOrEmpty(mExitDescrip.getText().toString())) ? "" : mExitDescrip.getText().toString();
                mTrade.setExitPrice(exitPrice);
                mTrade.setExitTradeDescrip(exitNote);
                TradeEntries.get(getContext()).updateTrade(mTrade);
                Toast.makeText(getContext(), R.string.entryUpdated, Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                Toast.makeText(getContext(),R.string.entryNotValid,Toast.LENGTH_SHORT).show();
            }
        }

        super.onPause();
    }
}
