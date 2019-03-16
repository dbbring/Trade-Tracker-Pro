package com.example.tradetrackerpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class TradeDetailFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TradeAdapter mTradeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.summary, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.trade_item_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
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
        private TextView mTickerTextView;
        private TextView mDateTextView;
        private Trade mTrade;

        public void bind(Trade trade){
            mTrade = trade;
            mTickerTextView.setText(mTrade.getTicker());
            mDateTextView.setText(mTrade.getDate().toString());
        }

        public TradeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.trade_list_item, parent, false));
            itemView.setOnClickListener(this);
            mTickerTextView = (TextView) itemView.findViewById(R.id.trade_ticker);
            mDateTextView = (TextView) itemView.findViewById(R.id.trade_date);

        }

        @Override
        public void onClick(View view){
            BaseFragment detailsFrag = new BaseFragment();
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
