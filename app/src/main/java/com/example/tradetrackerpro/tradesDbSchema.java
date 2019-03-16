package com.example.tradetrackerpro;

public class tradesDbSchema {
    public static final class TradesTable {

        public static final String NAME = "trades";

        public static final class Cols {
            public static final String ID = "id";
            public static final String ACCT_NUM = "acct";
            public static final String SIZE = "positionSize";
            public static final String ENTRY_DESCRIP = "entryDescription";
            public static final String EXIT_DESCRIP = "exitDescription";
            public static final String OUTCOME_CAT = "outcomeCategory";
            public static final String DATE = "date";
            public static final String TICKER = "ticker";
            public static final String ENTRY_PRICE = "entryPrice";
            public static final String EXIT_PRICE = "exitPrice";
        }

    }

}
