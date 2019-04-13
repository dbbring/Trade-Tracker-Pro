package com.example.tradetrackerpro;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.ui.Anchor;
import com.androidplot.ui.FixedTableModel;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.TableOrder;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;


public class HomeFragment extends BaseFragment {
    private TextView mImportantMessage;
    private String msg = null;
    private TextView mNumberOfTrades;
    private TextView mPercentChange;
    private TextView mMoneyChange;
    private PieChart mLosingPieChart;
    private PieChart mWinningPieChart;

    private Settings mSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        mImportantMessage = (TextView) view.findViewById(R.id.homeMessageArea);
        mNumberOfTrades = (TextView) view.findViewById(R.id.homeTotalTrades);
        mPercentChange = (TextView) view.findViewById(R.id.homePercentChange);
        mMoneyChange = (TextView) view.findViewById(R.id.homeMoneyChange);
        mLosingPieChart = (PieChart) view.findViewById(R.id.homeLosingTrend);
        mWinningPieChart = (PieChart) view.findViewById(R.id.homeWinningTrend);
        if (msg == null) {
            mImportantMessage.setVisibility(View.GONE);
        }

        CreatePieChart(mLosingPieChart,10, 15,0, 0, 0);
        CreatePieChart(mWinningPieChart,17,10,5,2,0);

        return view;
    }

    public void CreatePieChart(PieChart pie, int segment1Val, int segment2Val, int segment3Val, int segment4Val, int segment5Val) {
        pie.getBackgroundPaint().setColor(Color.argb(0,255,255,255));
        pie.getLegend().setVisible(true);
        pie.getLegend().setTableModel(new FixedTableModel(PixelUtils.dpToPix(300), PixelUtils.dpToPix(25), TableOrder.COLUMN_MAJOR));
        pie.getLegend().position((float)-0.10, HorizontalPositioning.ABSOLUTE_FROM_RIGHT, (float)0.20, VerticalPositioning.RELATIVE_TO_TOP, Anchor.TOP_MIDDLE);

        Segment s1 = new Segment("s1", segment1Val);
        Segment s2 = new Segment("s2", segment2Val);
        Segment s3 = new Segment("s3", segment3Val);
        Segment s4 = new Segment("s4", segment4Val);
        Segment s5 = new Segment("s5", segment5Val);

        SegmentFormatter sf1 = new SegmentFormatter(Color.BLACK);
        SegmentFormatter sf2 = new SegmentFormatter(Color.BLUE);
        SegmentFormatter sf3 = new SegmentFormatter(Color.RED);
        SegmentFormatter sf4 = new SegmentFormatter(Color.YELLOW);
        SegmentFormatter sf5 = new SegmentFormatter(Color.CYAN);

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
