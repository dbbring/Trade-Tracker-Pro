package com.example.tradetrackerpro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends BaseFragment {
        private Settings mSettings;
        private EditText mCat1Field;
        private EditText mCat2Field;
        private EditText mCat3Field;
        private EditText mCat4Field;
        private EditText mCat5Field;
        private EditText mAcct1Field;
        private EditText mAcct2Field;
        private EditText mAcct3Field;
        private Spinner mTradeCounterSelect;
        private Button mExportBtn;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String[] ddl_list;
            int ddl_list_index = 0;

            View view = inflater.inflate(R.layout.settings, container, false);
            mSettings = Settings.get(getContext());

            mCat1Field = (EditText) view.findViewById(R.id.settingsTrendCat1);
            mCat1Field.setText(mSettings.getCat1());

            mCat2Field = (EditText) view.findViewById(R.id.settingsTrendCat2);
            mCat2Field.setText(mSettings.getCat2());

            mCat3Field = (EditText) view.findViewById(R.id.settingsTrendCat3);
            mCat3Field.setText(mSettings.getCat3());

            mCat4Field = (EditText) view.findViewById(R.id.settingsTrendCat4);
            mCat4Field.setText(mSettings.getCat4());

            mCat5Field = (EditText) view.findViewById(R.id.settingsTrendCat5);
            mCat5Field.setText(mSettings.getCat5());

            mAcct1Field = (EditText) view.findViewById(R.id.settingsAcctNum1);
            mAcct1Field.setText(mSettings.getAcct1());

            mAcct2Field = (EditText) view.findViewById(R.id.settingsAcctNum2);
            mAcct2Field.setText(mSettings.getAcct2());

            mAcct3Field = (EditText) view.findViewById(R.id.settingsAcctNum3);
            mAcct3Field.setText(mSettings.getAcct3());

            mExportBtn = (Button) view.findViewById(R.id.settingsExportTradesBtn);
            mExportBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    writeDataLineByLine(getContext(),"exportTradeData.csv");

                }
            });

            mTradeCounterSelect = (Spinner) view.findViewById(R.id.settingsTradeCounter);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.tradeCounterSelection,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTradeCounterSelect.setAdapter(adapter);
            ddl_list = getResources().getStringArray(R.array.tradeCounterSelection);
            // Set our Spinner Selection based on what our settings file string is
            for(int x = 0; x < ddl_list.length; x++) {
                if(ddl_list[x].equals(mSettings.getTradeCounter())){
                    ddl_list_index = x;
                }
            }
            mTradeCounterSelect.setSelection(ddl_list_index);

            return view;
        }

        @Override
        public void onPause() {
            mSettings = Settings.get(getContext());
            Validation Vaildator = new Validation();
            mSettings.setImportantMessage(null);
            // Validate settings
            String cat1 = (!Vaildator.isNullOrEmpty(mCat1Field.getText().toString())) ? mCat1Field.getText().toString() : "";
            String cat2 = (!Vaildator.isNullOrEmpty(mCat2Field.getText().toString())) ? mCat2Field.getText().toString() : "";
            String cat3 = (!Vaildator.isNullOrEmpty(mCat3Field.getText().toString())) ? mCat3Field.getText().toString() : "";
            String cat4 = (!Vaildator.isNullOrEmpty(mCat4Field.getText().toString())) ? mCat4Field.getText().toString() : "";
            String cat5 = (!Vaildator.isNullOrEmpty(mCat5Field.getText().toString())) ? mCat5Field.getText().toString() : "";
            String acct1 = (!Vaildator.isNullOrEmpty(mAcct1Field.getText().toString())) ? mAcct1Field.getText().toString() : "";
            String acct2 = (!Vaildator.isNullOrEmpty(mAcct2Field.getText().toString())) ? mAcct2Field.getText().toString() : "";
            String acct3 = (!Vaildator.isNullOrEmpty(mAcct3Field.getText().toString())) ? mAcct3Field.getText().toString() : "";
            // If anything is blank throw error to important message block
            if (cat1.equals("") || cat2.equals("") || cat3.equals("") || cat4.equals("") || cat5.equals("")
            || acct1.equals("") || acct2.equals("") || acct3.equals("")) {
                mSettings.setImportantMessage("Please Enter Your Settings!");
            }
            // Set our singleton Settings class so our new enteries will be available without restarting
            mSettings.setCat1(cat1);
            mSettings.setCat2(cat2);
            mSettings.setCat3(cat3);
            mSettings.setCat4(cat4);
            mSettings.setCat5(cat5);
            mSettings.setAcct1(acct1);
            mSettings.setAcct2(acct2);
            mSettings.setAcct3(acct3);
            mSettings.setTradeCounter(mTradeCounterSelect.getSelectedItem().toString());
            // Write to file to save for persistant storage
            try {
                OutputStreamWriter writer = new OutputStreamWriter(getContext().openFileOutput("settings.txt", MODE_PRIVATE));
                writer.write(mSettings.getCat1() + "," + mSettings.getCat2() + "," + mSettings.getCat3()  + ","
                        + mSettings.getCat4() + "," + mSettings.getCat5() + "," + mSettings.getAcct1() + "," + mSettings.getAcct2() + "," +
                        mSettings.getAcct3() + "," + mSettings.getTradeCounter() + "," + mSettings.getImportantMessage());
                writer.close();
            }
            catch (IOException e) {
                Toast.makeText(getContext(),"There was Error Saving Your Settings.",Toast.LENGTH_LONG).show();
            }
            // Ya, we all know what this is
            super.onPause();
        }

    private void writeDataLineByLine(Context context, String filePath)
    {
        File file = new File(context.getFilesDir().getPath() + "/" + filePath);
        List<Trade> allTrades = TradeEntries.get(context).getTrades();
        try {
            file.createNewFile();
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = { "Ticker", "Entry Price", "Exit Price", "Date", "Shares", "Account", "Outcome Category",
                               "Entry Notes", "Exit Notes"};
            writer.writeNext(header);

            for (Trade trade : allTrades) {
                String[] data = { trade.getTicker(),
                                  Double.toString(trade.getEntryPrice()),
                                  Double.toString(trade.getExitPrice()),
                                  trade.getDate(),
                                  Integer.toString(trade.getPositionSize()),
                                  trade.getAcctNum(),
                                  trade.getOutcomeCat(),
                                  trade.getEntryTradeDescrip(),
                                  trade.getExitTradeDescrip()};
                writer.writeNext(data);
            }
            writer.close();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/csv");
            // Use FileProvider for safe access from other apps to TTP's directory
            Uri uri = FileProvider.getUriForFile(context, "com.example.tradetrackerpro", file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            // Make sure our new app can handle it
            PackageManager pm = getActivity().getPackageManager();
            if (intent.resolveActivity(pm) != null) {
                startActivity(Intent.createChooser(intent,"Export With:"));
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}

