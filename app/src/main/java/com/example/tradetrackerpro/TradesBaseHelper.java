package com.example.tradetrackerpro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.tradetrackerpro.tradesDbSchema.*;

public class TradesBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tradeTrackerPro.db";

    public TradesBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TradesTable.NAME + "(" +
                TradesTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TradesTable.Cols.DATE + " TEXT," +
                TradesTable.Cols.TICKER + " TEXT," +
                TradesTable.Cols.ENTRY_PRICE + " INTEGER," +
                TradesTable.Cols.EXIT_PRICE + " INTEGER," +
                TradesTable.Cols.ACCT_NUM + " TEXT," +
                TradesTable.Cols.SIZE + " INTEGER," +
                TradesTable.Cols.ENTRY_DESCRIP + " TEXT," +
                TradesTable.Cols.EXIT_DESCRIP + " TEXT," +
                TradesTable.Cols.OUTCOME_CAT + " TEXT)"

                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
