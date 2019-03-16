package com.example.tradetrackerpro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TradeEntries {
    private static TradeEntries sTradeEntries;
    private Context mContext;
    private SQLiteDatabase mDatabase;

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
        TradeCursorWrapper cursor = queryTrades(tradesDbSchema.TradesTable.Cols.ID + " = ? ",new String[] {Integer.toString(_id)});

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
        Cursor cursor = mDatabase.query(tradesDbSchema.TradesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new TradeCursorWrapper(cursor);
    }

}
