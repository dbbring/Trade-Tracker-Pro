package com.example.tradetrackerpro;


public class Trade {

    private int mTradeID;
    private double mEntryPrice;
    private double mExitPrice;
    private int mPositionSize;
    private String mAcctNum;
    private String mTicker;
    private String mEntryTradeDescrip;
    private String mExitTradeDescrip;
    private String mOutcomeCat;
    private String mDate;


    public int getTradeID() {
        return mTradeID;
    }

    public void setTradeID(int tradeID) {
        mTradeID = tradeID;
    }

    public double getEntryPrice() {
        return mEntryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        mEntryPrice = entryPrice;
    }

    public double getExitPrice() {
        return mExitPrice;
    }

    public void setExitPrice(double exitPrice) {
        mExitPrice = exitPrice;
    }

    public int getPositionSize() {
        return mPositionSize;
    }

    public void setPositionSize(int positionSize) {
        mPositionSize = positionSize;
    }

    public String getAcctNum() {
        return mAcctNum;
    }

    public void setAcctNum(String acctNum) {
        mAcctNum = acctNum;
    }

    public String getTicker() {
        return mTicker;
    }

    public void setTicker(String ticker) {
        mTicker = ticker;
    }

    public String getEntryTradeDescrip() {
        return mEntryTradeDescrip;
    }

    public void setEntryTradeDescrip(String entryTradeDescrip) {
        mEntryTradeDescrip = entryTradeDescrip;
    }

    public String getExitTradeDescrip() {
        return mExitTradeDescrip;
    }

    public void setExitTradeDescrip(String exitTradeDescrip) {
        mExitTradeDescrip = exitTradeDescrip;
    }

    public String getOutcomeCat() {
        return mOutcomeCat;
    }

    public void setOutcomeCat(String outcomeCat) {
        mOutcomeCat = outcomeCat;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }


}
