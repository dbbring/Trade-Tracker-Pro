package com.example.tradetrackerpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static com.example.tradetrackerpro.tradesDbSchema.*;

public class TradeEntries {
    private static TradeEntries sTradeEntries;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public void addTrade(Trade trade) {
        ContentValues values = getContentValues(trade);
        mDatabase.insert(TradesTable.NAME, null, values);
    }

    public static TradeEntries get(Context context){
        if(sTradeEntries == null) {
            sTradeEntries = new TradeEntries(context);
        }
        return sTradeEntries;
    }

    private TradeEntries(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new TradesBaseHelper(mContext).getWritableDatabase();
    }
    public List<Trade> getTrades(){
        List<Trade> trades = new ArrayList<>();
        TradeCursorWrapper cursor = queryTrades(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                trades.add(cursor.getTrade());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return trades;
    }

    public Trade getTrade(int _id) {
        TradeCursorWrapper cursor = queryTrades(TradesTable.Cols.ID + " = ? ",new String[] {Integer.toString(_id)});

        try {
            if(cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTrade();
        }
        finally {
            cursor.close();
        }
    }


    private TradeCursorWrapper queryTrades(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(TradesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new TradeCursorWrapper(cursor);
    }

    public static ContentValues getContentValues(Trade trade){
        ContentValues values = new ContentValues();
        values.put(TradesTable.Cols.ID, trade.getTradeID());
        values.put(TradesTable.Cols.TICKER, trade.getTicker());
        values.put(TradesTable.Cols.ENTRY_PRICE, trade.getEntryPrice());
        values.put(TradesTable.Cols.EXIT_PRICE, trade.getExitPrice());
        values.put(TradesTable.Cols.DATE, trade.getDate());
        values.put(TradesTable.Cols.ACCT_NUM, trade.getAcctNum());
        values.put(TradesTable.Cols.OUTCOME_CAT, trade.getOutcomeCat());
        values.put(TradesTable.Cols.SIZE, trade.getPositionSize());
        values.put(TradesTable.Cols.ENTRY_DESCRIP, trade.getEntryTradeDescrip());
        values.put(TradesTable.Cols.EXIT_DESCRIP, trade.getExitTradeDescrip());

        return values;


    }

}
