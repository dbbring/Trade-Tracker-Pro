package com.example.tradetrackerpro;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class _fakeTradeEntries {
    private static _fakeTradeEntries sPsuedoEntries;
    private List<Trade> mTrades;

    public static _fakeTradeEntries get(Context context){
        if(sPsuedoEntries == null) {
            sPsuedoEntries = new _fakeTradeEntries(context);
        }
        return sPsuedoEntries;
    }

    private _fakeTradeEntries(Context context){
        mTrades = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            Trade tradeEntry = new Trade();
            tradeEntry.setTicker("Ticker #" + i);
            tradeEntry.setDate(new Date(2018,12,i));
            mTrades.add(tradeEntry);
        }
    }
    public List<Trade> getTrades(){
        return mTrades;
    }

}
