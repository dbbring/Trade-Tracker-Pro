package com.example.tradetrackerpro;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.androidplot.ui.Anchor;
import com.androidplot.ui.FixedTableModel;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.Size;
import com.androidplot.ui.SizeMode;
import com.androidplot.ui.TableOrder;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TradeDetailFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TradeAdapter mTradeAdapter;
    private XYPlot mCat1Chart;
    private Settings mSettings;
    private TradeEntries mTradeEntries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.summary, container, false);
        mSettings = Settings.get(getContext());
        mTradeEntries = TradeEntries.get(getContext());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.trade_item_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<List<Trade>> primaryList = new ArrayList<>();
        List<Trade> trades = mTradeEntries.getTradesBetweenDates(mSettings.getTradeCounter(), 1);

        primaryList.add(trades);
        primaryList.add(mTradeEntries.getTradesBetweenDates(mSettings.getTradeCounter(), 2));
        primaryList.add(mTradeEntries.getTradesBetweenDates(mSettings.getTradeCounter(), 3));
        primaryList.add(mTradeEntries.getTradesBetweenDates(mSettings.getTradeCounter(), 4));

        mCat1Chart = (XYPlot) view.findViewById(R.id.performCat1);
        CreateLineChart(mCat1Chart, mSettings.getCat1(), findInstances(primaryList, mSettings.getCat1(), 4),
                mSettings.getCat2(), findInstances(primaryList, mSettings.getCat2(), 4),
                mSettings.getCat3(), findInstances(primaryList, mSettings.getCat3(), 4),
                mSettings.getCat4(), findInstances(primaryList, mSettings.getCat4(), 4),
                mSettings.getCat5(), findInstances(primaryList, mSettings.getCat5(), 4));

        updateUI();

        return view;
    }

    /*
    @params - Array of Arrays, String, int
    @descrip - Searchs through each array of a main array for the value to find. Declare the length
    of the returning number array.
     */
    private Number[] findInstances(List<List<Trade>> mainList, String valueToFind, int arrayLength) {
        Number[] returningValues = new Number[arrayLength];
        int totalOccurances = 0;
        List<Trade> secondaryList;
        for(int x = 0; x < arrayLength; x++) {
            secondaryList = mainList.get(x);
            totalOccurances = 0;
            for(int y = 0; y < secondaryList.size(); y++) {
                totalOccurances += (secondaryList.get(y).getOutcomeCat().equals(valueToFind)) ? 1 : 0;
            }
            returningValues[x] = totalOccurances;
        }
        return returningValues;
    }

    /*
    @params - XYPlot, String, Number[], String, Number[], String, Number[], String, Number[], String, Number[]
    @descrip - Creates a Line chart with 5 lines.
     */
    private void CreateLineChart(XYPlot plot, String array1Title, Number[] segment1Val, String array2Title, Number[] segment2Val, String array3Title, Number[] segment3Val,
                               String array4Title, Number[] segment4Val, String array5Title, Number[] segment5Val) {
        // Set background color
        plot.getBackgroundPaint().setColor(Color.WHITE);
        // Set grid background color and grid lines color
        plot.getGraph().getGridBackgroundPaint().setColor(Color.BLACK);
        plot.getGraph().getRangeGridLinePaint().setColor(Color.WHITE);
        // Set a new size for our chart
        plot.getGraph().setSize(new Size(
                PixelUtils.dpToPix(400), SizeMode.ABSOLUTE,
                1.0f, SizeMode.RELATIVE));
        // Set up the legend position, size, and layout
        plot.getLegend().setTableModel(new FixedTableModel(PixelUtils.dpToPix(300), PixelUtils.dpToPix(25), TableOrder.COLUMN_MAJOR));
        plot.getLegend().position((float)-0.10, HorizontalPositioning.ABSOLUTE_FROM_RIGHT, (float)0, VerticalPositioning.RELATIVE_TO_TOP, Anchor.TOP_MIDDLE);
        plot.getLegend().setSize(new Size(
                PixelUtils.dpToPix(400), SizeMode.ABSOLUTE,
                1.0f, SizeMode.RELATIVE));
        // Set our grid line intervals
        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1);
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1);

        // Set up some new series for our graph data
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(segment1Val), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, array1Title);
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(segment2Val), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, array2Title);
        XYSeries series3 = new SimpleXYSeries(
                Arrays.asList(segment3Val), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, array3Title);
        XYSeries series4 = new SimpleXYSeries(
                Arrays.asList(segment4Val), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, array4Title);
        XYSeries series5 = new SimpleXYSeries(
                Arrays.asList(segment5Val), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, array5Title);

        // Format our new series with color, but make the shadow transparent since we overlaying
        // on a single chart also include point data
        PointLabelFormatter plf = new PointLabelFormatter();
        plf.getTextPaint().setTextSize(PixelUtils.spToPix(12));

        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(Color.RED, Color.GREEN, Color.TRANSPARENT, null);
        plf.getTextPaint().setColor(Color.RED);
        series1Format.setPointLabelFormatter(plf);
        LineAndPointFormatter series2Format =
                new LineAndPointFormatter(Color.GREEN, Color.GRAY, Color.TRANSPARENT, null);
        plf.getTextPaint().setColor(Color.GREEN);
        series2Format.setPointLabelFormatter(plf);
        LineAndPointFormatter series3Format =
                new LineAndPointFormatter(Color.CYAN, Color.DKGRAY, Color.TRANSPARENT, null);
        plf.getTextPaint().setColor(Color.CYAN);
        series3Format.setPointLabelFormatter(plf);
        LineAndPointFormatter series4Format =
                new LineAndPointFormatter(Color.YELLOW, Color.BLACK, Color.TRANSPARENT, null);
        plf.getTextPaint().setColor(Color.YELLOW);
        series4Format.setPointLabelFormatter(plf);
        LineAndPointFormatter series5Format =
                new LineAndPointFormatter(Color.WHITE, Color.MAGENTA, Color.TRANSPARENT, null);
        plf.getTextPaint().setColor(Color.WHITE);
        series5Format.setPointLabelFormatter(plf);

        // Add some smoothing to the lines
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        series3Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        series4Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        series5Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        // Finally add the series
        plot.addSeries(series1, series1Format);
        plot.addSeries(series2, series2Format);
        plot.addSeries(series3, series3Format);
        plot.addSeries(series4, series4Format);
        plot.addSeries(series5, series5Format);

    }
    /*
    @descrip - Takes the singleton class and sets the adapter on the recycler view.
     */
    private void updateUI(){
        TradeEntries pseudoEntries = TradeEntries.get(getActivity());
        List<Trade> trades = pseudoEntries.getTrades();

        mTradeAdapter = new TradeAdapter(trades);
        mRecyclerView.setAdapter(mTradeAdapter);
    }
    /*
    @descrip - Holder class for recycler view. Inflates each list item. Sets on click listener
    for each line item in the recycler view. On each line item click new fragment is displayed and this
    fragment is sent to the back stack.
     */
    private class TradeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final String TRADE_ID = "com.example.tradetrackerpro.trade_id";
        private TextView mTickerTextView;
        private TextView mDateTextView;
        private TextView mNetChangeTextView;
        private Trade mTrade;

        public void bind(Trade trade){
            DecimalFormat changeFormatter = new DecimalFormat("#.00");

            mTrade = trade;
            String netChange;
            double change = (mTrade.getExitPrice() - mTrade.getEntryPrice()) * mTrade.getPositionSize();
            if (change > 0) {
                netChange = "+$" + changeFormatter.format(change);
                mNetChangeTextView.setTextColor(Color.GREEN);
            }
            else {
                netChange = "-$" + changeFormatter.format(change);
                mNetChangeTextView.setTextColor(Color.RED);
            }
 ;

            mTickerTextView.setText(mTrade.getTicker());
            mDateTextView.setText(mTrade.getDate());
            mNetChangeTextView.setText(netChange);
        }

        public TradeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.trade_list_item, parent, false));
            itemView.setOnClickListener(this);
            mTickerTextView = (TextView) itemView.findViewById(R.id.trade_ticker);
            mDateTextView = (TextView) itemView.findViewById(R.id.trade_date);
            mNetChangeTextView = (TextView) itemView.findViewById(R.id.netChange);

        }

        @Override
        public void onClick(View view){
            Bundle bundle = new Bundle();
            bundle.putInt(TRADE_ID,mTrade.getTradeID());
            TradeEntryDetailFragment detailsFrag = new TradeEntryDetailFragment();
            detailsFrag.setArguments(bundle);
            detailsFrag.setLayout(R.layout.details);
            FragmentTransaction transact = getActivity().getSupportFragmentManager().beginTransaction();
            transact.replace(R.id.main_layout, detailsFrag);
            transact.addToBackStack(null);
            transact.commit();
        }
    }
    /*
    @descrip - Adapter class for recycler view. Binds the holder to each trade item in the singleton
     class. Returns size of the array from the singleton class as well.
     */
    private class TradeAdapter extends RecyclerView.Adapter<TradeHolder> {
        private List<Trade> mTrades;

        public TradeAdapter(List<Trade> trades){
            mTrades = trades;
        }

        @Override
        public TradeHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TradeHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(TradeHolder Holder, int i) {
            Trade trade = mTrades.get(i);
            Holder.bind(trade);
        }

        @Override
        public int getItemCount() {
            return mTrades.size();
        }

    }

}
