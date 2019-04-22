package com.example.tradetrackerpro;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.ui.Anchor;
import com.androidplot.ui.FixedTableModel;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.TableOrder;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeFragment extends BaseFragment {
    private Settings mSettings;
    private TradeEntries mTradeEntries;
    private TextView mImportantMessage;
    private String msg;
    private TextView mNumberOfTrades;
    private TextView mPercentChange;
    private TextView mMoneyChange;
    private PieChart mLosingPieChart;
    private PieChart mWinningPieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        mSettings = Settings.get(getContext());
        mTradeEntries = TradeEntries.get(getContext());

        List<Trade> trades = mTradeEntries.getTradesBetweenDates(mSettings.getTradeCounter(), 1);
        List<Trade> allTrades = mTradeEntries.getTrades();
        List<KeyValue> winningChartValues = new ArrayList<>();
        List<KeyValue> losingChartValues = new ArrayList<>();
        double totalAmount, allTradesTotalAmount, percentChange;
        int winningCat1, winningCat2, winningCat3, winningCat4, winningCat5,
                losingCat1, losingCat2, losingCat3, losingCat4, losingCat5;
        DecimalFormat totalAmountFormatter = new DecimalFormat("#.00");

        mImportantMessage = (TextView) view.findViewById(R.id.homeMessageArea);
        mNumberOfTrades = (TextView) view.findViewById(R.id.homeTotalTrades);
        mPercentChange = (TextView) view.findViewById(R.id.homePercentChange);
        mMoneyChange = (TextView) view.findViewById(R.id.homeMoneyChange);
        mLosingPieChart = (PieChart) view.findViewById(R.id.homeLosingTrend);
        mWinningPieChart = (PieChart) view.findViewById(R.id.homeWinningTrend);

        msg = mSettings.getImportantMessage();
        // Check for important messages, if we are null then dont worry about it
        // On initial load, after the user visits the settings screen then the important msg is null
        mSettings = Settings.get(getContext());
        // Check for string as well, because when we read the file, it will interupt it as a string
        if (msg == null || msg.equals("null")) {
            mImportantMessage.setVisibility(View.GONE);
        }
        else {
            mImportantMessage.setText(msg);
        }

        mNumberOfTrades.setText(Integer.toString(trades.size()));
        totalAmount = 0;
        winningCat1 = winningCat2 = winningCat3 = winningCat4 = winningCat5 = 0;
        losingCat1 = losingCat2 = losingCat3 = losingCat4 = losingCat5 = 0;

        for(Trade trade : trades) {
            // Find our most popular outcome categories while we are summing up our totals based on user selected
            // time period
            if (trade.getExitPrice() >= trade.getEntryPrice()) {
                winningCat1 += (trade.getOutcomeCat().equals(mSettings.getCat1())) ? 1 : 0;
                winningCat2 += (trade.getOutcomeCat().equals(mSettings.getCat2())) ? 1 : 0;
                winningCat3 += (trade.getOutcomeCat().equals(mSettings.getCat3())) ? 1 : 0;
                winningCat4 += (trade.getOutcomeCat().equals(mSettings.getCat4())) ? 1 : 0;
                winningCat5 += (trade.getOutcomeCat().equals(mSettings.getCat5())) ? 1 : 0;
            }
            else {
                losingCat1 += (trade.getOutcomeCat().equals(mSettings.getCat1())) ? 1 : 0;
                losingCat2 += (trade.getOutcomeCat().equals(mSettings.getCat2())) ? 1 : 0;
                losingCat3 += (trade.getOutcomeCat().equals(mSettings.getCat3())) ? 1 : 0;
                losingCat4 += (trade.getOutcomeCat().equals(mSettings.getCat4())) ? 1 : 0;
                losingCat5 += (trade.getOutcomeCat().equals(mSettings.getCat5())) ? 1 : 0;
            }

            totalAmount += (trade.getExitPrice() - trade.getEntryPrice()) * trade.getPositionSize();
        }
        mMoneyChange.setText(totalAmountFormatter.format(totalAmount));

        allTradesTotalAmount = 0;
        for( Trade trade : allTrades) {
            allTradesTotalAmount += (trade.getExitPrice() - trade.getEntryPrice()) * trade.getPositionSize();
        }
        percentChange = (allTradesTotalAmount - totalAmount) / allTradesTotalAmount * 100;
        percentChange = (Double.isNaN(percentChange)) ? 0.00 : percentChange;
        mPercentChange.setText(totalAmountFormatter.format(percentChange));

        // set up our winning pie chart numbers
        winningChartValues.add(new KeyValue(mSettings.getCat1(), winningCat1));
        winningChartValues.add(new KeyValue(mSettings.getCat2(), winningCat2));
        winningChartValues.add(new KeyValue(mSettings.getCat3(), winningCat3));
        winningChartValues.add(new KeyValue(mSettings.getCat4(), winningCat4));
        winningChartValues.add(new KeyValue(mSettings.getCat5(), winningCat5));
        Collections.sort(winningChartValues, new DescSort());

        CreatePieChart(mWinningPieChart,winningChartValues.get(0).getKey(),winningChartValues.get(0).getValue(),
                        winningChartValues.get(1).getKey(), winningChartValues.get(1).getValue());

        // Set up our losing pie chart numbers
        losingChartValues.add(new KeyValue(mSettings.getCat1(), losingCat1));
        losingChartValues.add(new KeyValue(mSettings.getCat2(), losingCat2));
        losingChartValues.add(new KeyValue(mSettings.getCat3(), losingCat3));
        losingChartValues.add(new KeyValue(mSettings.getCat4(), losingCat4));
        losingChartValues.add(new KeyValue(mSettings.getCat5(), losingCat5));
        Collections.sort(losingChartValues, new DescSort());

        CreatePieChart(mLosingPieChart,losingChartValues.get(0).getKey(),losingChartValues.get(0).getValue(),
                losingChartValues.get(1).getKey(), losingChartValues.get(1).getValue());

        return view;
    }

    /*
    @ params - PieChart, String, int, String, int
    @ descrip - Sets up a new pie chart with up to 2 segements. title and value are args. Because we have 5 categories
               We are picking the top two for each winning and losing
     */
    public void CreatePieChart(PieChart pie, String seg1title, int segment1Val, String seg2title, int segment2Val) {
        pie.getBackgroundPaint().setColor(Color.argb(0,255,255,255));
        pie.getLegend().setVisible(true);
        pie.getLegend().getTextPaint().setColor(Color.BLACK);
        pie.getLegend().setTableModel(new FixedTableModel(PixelUtils.dpToPix(300), PixelUtils.dpToPix(25), TableOrder.COLUMN_MAJOR));
        pie.getLegend().position(PixelUtils.dpToPix(50), HorizontalPositioning.ABSOLUTE_FROM_RIGHT, (float)0.20, VerticalPositioning.RELATIVE_TO_TOP, Anchor.TOP_MIDDLE);
        pie.getPie().position(PixelUtils.dpToPix(-50), HorizontalPositioning.ABSOLUTE_FROM_LEFT, 0, VerticalPositioning.RELATIVE_TO_TOP);

        // Create new segments based on our params
        Segment s1 = new Segment(seg1title, segment1Val);
        Segment s2 = new Segment(seg2title, segment2Val);

        // Set up our colors
        SegmentFormatter sf1 = new SegmentFormatter(Color.BLACK);
        SegmentFormatter sf2 = new SegmentFormatter(Color.BLUE);

        pie.addSegment(s1, sf1);
        pie.addSegment(s2, sf2);
    }

    /*
    The KeyValue class is a simple class that allows an key value object to be made. Useful for tracking
    integer values with an name. Similar to a Python dictionary.
     */
    private class KeyValue {
        private String key;
        private int value;

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        public  KeyValue (String _key, int _value) {
            key = _key;
            value = _value;
        }

    }

    /*
    Simple class for sorting Collection objects in a descending order. Similar to the JavaScript comparing function.
     */
    private class DescSort implements Comparator<KeyValue>
    {
        public int compare(KeyValue a, KeyValue b)
        {
            return a.value + b.value;
        }
    }

}
