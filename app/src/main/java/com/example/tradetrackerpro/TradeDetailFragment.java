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
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import java.util.Arrays;
import java.util.List;

public class TradeDetailFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TradeAdapter mTradeAdapter;
    private XYPlot mCat1Chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.summary, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.trade_item_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCat1Chart = (XYPlot) view.findViewById(R.id.performCat1);

        Number[] cat1Vals = {5,4,6,7};
        Number[] cat2Vals = {2,4,1,5};
        Number[] cat3Vals = {1,3,6,8};
        Number[] cat4Vals = {9,4,1,6};
        Number[] cat5Vals = {3,2,0,5};

        CreateBarChart(mCat1Chart, "Cat 1", cat1Vals, "Cat 2", cat2Vals, "Cat 3", cat3Vals, "Cat 4", cat4Vals, "Cat 5", cat5Vals);
        updateUI();

        return view;
    }

    public void CreateBarChart(XYPlot plot, String array1Title, Number[] segment1Val, String array2Title, Number[] segment2Val, String array3Title, Number[] segment3Val,
                               String array4Title, Number[] segment4Val, String array5Title, Number[] segment5Val) {
        plot.getBackgroundPaint().setColor(Color.argb(0,255,255,255));
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
        // on a single chart
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(Color.RED, Color.GREEN, Color.TRANSPARENT, null);
        LineAndPointFormatter series2Format =
                new LineAndPointFormatter(Color.BLACK, Color.GRAY, Color.TRANSPARENT, null);
        LineAndPointFormatter series3Format =
                new LineAndPointFormatter(Color.CYAN, Color.DKGRAY, Color.TRANSPARENT, null);
        LineAndPointFormatter series4Format =
                new LineAndPointFormatter(Color.YELLOW, Color.BLACK, Color.TRANSPARENT, null);
        LineAndPointFormatter series5Format =
                new LineAndPointFormatter(Color.WHITE, Color.MAGENTA, Color.TRANSPARENT, null);
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
        private Trade mTrade;

        public void bind(Trade trade){
            mTrade = trade;
            mTickerTextView.setText(mTrade.getTicker());
            mDateTextView.setText(mTrade.getDate());
        }

        public TradeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.trade_list_item, parent, false));
            itemView.setOnClickListener(this);
            mTickerTextView = (TextView) itemView.findViewById(R.id.trade_ticker);
            mDateTextView = (TextView) itemView.findViewById(R.id.trade_date);

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
