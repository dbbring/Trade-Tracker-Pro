package com.example.tradetrackerpro;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TradeEntryDetailFragment extends BaseFragment {
    private final String TRADE_ID = "com.example.tradetrackerpro.trade_id";
    private Trade mTrade;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);
        int tradeID = getArguments().getInt(TRADE_ID);
        mTrade = TradeEntries.get(getContext()).getTrade(tradeID);

        mTicker = (TextView) view.findViewById(R.id.detailViewTicker);
        mTicker.setText("Ticker: " + mTrade.getTicker());

        mDate = (TextView) view.findViewById(R.id.detailViewDate);
        mDate.setText("Date: " + mTrade.getDate());

        mEntryPrice = (TextView) view.findViewById(R.id.detailViewEntryPrice);
        mEntryPrice.setText("Entry: $" + mTrade.getEntryPrice());

        mExitPrice = (EditText) view.findViewById(R.id.detailViewExitPrice);
        if(mTrade.getExitPrice() == 0.00) {
            mExitPrice.setEnabled(true);
        }
        mExitPrice.setText("Exit: $" + mTrade.getExitPrice());

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

        mNetChange = (TextView) view.findViewById(R.id.detailViewNetChange);

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
}
