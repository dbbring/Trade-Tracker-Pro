package com.example.tradetrackerpro;

import java.util.Date;

public class Trade {
    private int mEntryPrice;
    private int mExitPrice;
    private int mPositionSize;
    private int mAcctNum;
    private String mTicker;
    private String mTradeDescrip;
    private String mOutcomeCat;
    private Date mDate;


    public int getEntryPrice() {
        return mEntryPrice;
    }

    public void setEntryPrice(int entryPrice) {
        mEntryPrice = entryPrice;
    }

    public int getExitPrice() {
        return mExitPrice;
    }

    public void setExitPrice(int exitPrice) {
        mExitPrice = exitPrice;
    }

    public int getPositionSize() {
        return mPositionSize;
    }

    public void setPositionSize(int positionSize) {
        mPositionSize = positionSize;
    }

    public int getAcctNum() {
        return mAcctNum;
    }

    public void setAcctNum(int acctNum) {
        mAcctNum = acctNum;
    }

    public String getTicker() {
        return mTicker;
    }

    public void setTicker(String ticker) {
        mTicker = ticker;
    }

    public String getTradeDescrip() {
        return mTradeDescrip;
    }

    public void setTradeDescrip(String tradeDescrip) {
        mTradeDescrip = tradeDescrip;
    }

    public String getOutcomeCat() {
        return mOutcomeCat;
    }

    public void setOutcomeCat(String outcomeCat) {
        mOutcomeCat = outcomeCat;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
