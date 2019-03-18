package com.example.tradetrackerpro;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.tradetrackerpro.Trade;
import com.example.tradetrackerpro.tradesDbSchema;

import java.util.Date;

public class TradeCursorWrapper extends CursorWrapper {

    public TradeCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Trade getTrade() {
        int id = getInt(getColumnIndex(tradesDbSchema.TradesTable.Cols.ID));
        double entryPrice = getDouble(getColumnIndex(tradesDbSchema.TradesTable.Cols.ENTRY_PRICE));
        double exitPrice = getDouble(getColumnIndex(tradesDbSchema.TradesTable.Cols.EXIT_PRICE));
        String ticker = getString(getColumnIndex(tradesDbSchema.TradesTable.Cols.TICKER));
        Long date = getLong(getColumnIndex(tradesDbSchema.TradesTable.Cols.DATE));
        String acctNum = getString(getColumnIndex(tradesDbSchema.TradesTable.Cols.ACCT_NUM));
        String entryTradeDescrip = getString(getColumnIndex(tradesDbSchema.TradesTable.Cols.ENTRY_DESCRIP));
        String exitTradeDescrip = getString(getColumnIndex(tradesDbSchema.TradesTable.Cols.EXIT_DESCRIP));
        String outcomeCategory = getString(getColumnIndex(tradesDbSchema.TradesTable.Cols.OUTCOME_CAT));
        int size = getInt(getColumnIndex(tradesDbSchema.TradesTable.Cols.SIZE));

        Trade trade = new Trade();
        trade.setTradeID(id);
        trade.setEntryPrice(entryPrice);
        trade.setExitPrice(exitPrice);
        trade.setTicker(ticker);
        trade.setDate(new Date(date).toString());
        trade.setAcctNum(acctNum);
        trade.setEntryTradeDescrip(entryTradeDescrip);
        trade.setExitTradeDescrip(exitTradeDescrip);
        trade.setOutcomeCat(outcomeCategory);
        trade.setPositionSize(size);

        return trade;
    }
}
