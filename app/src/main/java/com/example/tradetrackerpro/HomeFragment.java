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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

        List<Trade> trades = mTradeEntries.getTradesBetweenDates("Weekly");
        double totalAmount = 0.00;

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
        if (msg == null || msg.equals("null")) {
            mImportantMessage.setVisibility(View.GONE);
        }
        else {
            mImportantMessage.setText(msg);
        }

        mNumberOfTrades.setText(Integer.toString(trades.size()));

        for(Trade trade : trades) {
            totalAmount += (trade.getExitPrice() - trade.getEntryPrice()) * trade.getPositionSize();
        }
        DecimalFormat totalAmountFormatter = new DecimalFormat("#.00");
        mMoneyChange.setText(totalAmountFormatter.format(totalAmount));

        // sort through data if above 0 then its a win if below 0 its a loss
        // then grab top 2 cats of each to display charts and number of occurances

        CreatePieChart(mLosingPieChart,"BTFD",10,"Hesistaion", 15,
                "",0,"", 0, "", 0);
        CreatePieChart(mWinningPieChart,"Timing",17,"Fear",10,
                "Some",5, "",0,"",0);

        return view;
    }

    /*
    @ params - PieChart, String, int, String, int, String, int, String, int, String, int
    @ descrip - Sets up a new pie chart with up to 5 segements. title and value are args.
     */
    public void CreatePieChart(PieChart pie, String seg1title, int segment1Val, String seg2title, int segment2Val,
                               String seg3title, int segment3Val, String seg4title, int segment4Val,
                               String seg5title, int segment5Val) {
        pie.getBackgroundPaint().setColor(Color.argb(0,255,255,255));
        pie.getLegend().setVisible(true);
        pie.getLegend().setTableModel(new FixedTableModel(PixelUtils.dpToPix(300), PixelUtils.dpToPix(25), TableOrder.COLUMN_MAJOR));
        pie.getLegend().position((float)-0.10, HorizontalPositioning.ABSOLUTE_FROM_RIGHT, (float)0.20, VerticalPositioning.RELATIVE_TO_TOP, Anchor.TOP_MIDDLE);

        // Create new segments based on our params
        Segment s1 = new Segment(seg1title, segment1Val);
        Segment s2 = new Segment(seg2title, segment2Val);
        Segment s3 = new Segment(seg3title, segment3Val);
        Segment s4 = new Segment(seg4title, segment4Val);
        Segment s5 = new Segment(seg5title, segment5Val);

        // Set up our colors
        SegmentFormatter sf1 = new SegmentFormatter(Color.BLACK);
        SegmentFormatter sf2 = new SegmentFormatter(Color.BLUE);
        SegmentFormatter sf3 = new SegmentFormatter(Color.RED);
        SegmentFormatter sf4 = new SegmentFormatter(Color.YELLOW);
        SegmentFormatter sf5 = new SegmentFormatter(Color.CYAN);

        // If our value is 0 dont bother showing it
        if (segment1Val != 0) {
            pie.addSegment(s1, sf1);
        }
        if (segment2Val != 0) {
            pie.addSegment(s2, sf2);
        }
        if (segment3Val != 0) {
            pie.addSegment(s3, sf3);
        }
        if (segment4Val != 0) {
            pie.addSegment(s4, sf4);
        }
        if (segment5Val != 0) {
            pie.addSegment(s5, sf5);
        }

    }

}
