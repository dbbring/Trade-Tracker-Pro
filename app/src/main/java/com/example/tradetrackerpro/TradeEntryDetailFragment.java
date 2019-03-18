package com.example.tradetrackerpro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TradeEntryDetailFragment extends BaseFragment {
    private final String TRADE_ID = "com.example.tradetrackerpro.trade_id";
    private Trade mTrade;
    private TextView mTicker;
    private TextView mDate;
    private TextView mEntryPrice;
    private TextView mExitPrice;
    private TextView mSize;
    private TextView mEntryDescrip;
    private TextView mExitDescrip;
    private TextView mNetChange;
    private TextView mAccountNum;
    private TextView mOutcomeCat;

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

        mExitPrice = (TextView) view.findViewById(R.id.detailViewExitPrice);
        mExitPrice.setText("Exit: $" + mTrade.getExitPrice());

        mSize = (TextView) view.findViewById(R.id.detailViewSize);
        mSize.setText(mTrade.getPositionSize() + " Shares");

        mEntryDescrip = (TextView) view.findViewById(R.id.detailViewEntryTradeDescrip);
        mEntryDescrip.setText(mTrade.getEntryTradeDescrip());

        mExitDescrip = (TextView) view.findViewById(R.id.detailViewExitTradeDescrip);
        mExitDescrip.setText(mTrade.getExitTradeDescrip());

        mNetChange = (TextView) view.findViewById(R.id.detailViewNetChange);

        mOutcomeCat = (TextView) view.findViewById(R.id.detailViewCategory);
        mOutcomeCat.setText(mTrade.getOutcomeCat());

        mAccountNum = (TextView) view.findViewById(R.id.detailViewAccount);
        mAccountNum.setText(mTrade.getAcctNum());

        return view;
    }
}
