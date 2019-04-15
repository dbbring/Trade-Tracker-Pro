package com.example.tradetrackerpro;

import android.content.Context;
import android.content.Intent;
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

import javax.xml.validation.Validator;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends BaseFragment {
        private Settings mSettings;
        private EditText mEmailField;
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

            mEmailField = (EditText) view.findViewById(R.id.settingsExportEmailAddress);
            mEmailField.setText(mSettings.getEmail());

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
                    writeDataLineByLine(getContext(),"test.csv");

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
            String email = (Vaildator.isValidEmail(mEmailField.getText().toString())) ? mEmailField.getText().toString() : "";
            String cat1 = (!Vaildator.isNullOrEmpty(mCat1Field.getText().toString())) ? mCat1Field.getText().toString() : "";
            String cat2 = (!Vaildator.isNullOrEmpty(mCat2Field.getText().toString())) ? mCat2Field.getText().toString() : "";
            String cat3 = (!Vaildator.isNullOrEmpty(mCat3Field.getText().toString())) ? mCat3Field.getText().toString() : "";
            String cat4 = (!Vaildator.isNullOrEmpty(mCat4Field.getText().toString())) ? mCat4Field.getText().toString() : "";
            String cat5 = (!Vaildator.isNullOrEmpty(mCat5Field.getText().toString())) ? mCat5Field.getText().toString() : "";
            String acct1 = (!Vaildator.isNullOrEmpty(mAcct1Field.getText().toString())) ? mAcct1Field.getText().toString() : "";
            String acct2 = (!Vaildator.isNullOrEmpty(mAcct2Field.getText().toString())) ? mAcct2Field.getText().toString() : "";
            String acct3 = (!Vaildator.isNullOrEmpty(mAcct3Field.getText().toString())) ? mAcct3Field.getText().toString() : "";
            // If anything is blank throw error to important message block
            if (email.equals("") || cat1.equals("") || cat2.equals("") || cat3.equals("") || cat4.equals("") || cat5.equals("")
            || acct1.equals("") || acct2.equals("") || acct3.equals("")) {
                mSettings.setImportantMessage("Please Enter Your Settings!");
            }
            // Set our singleton Settings class so our new enteries will be available without restarting
            mSettings.setEmail(email);
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
                writer.write(mSettings.getEmail() + "," + mSettings.getCat1() + "," + mSettings.getCat2() + "," + mSettings.getCat3()  + ","
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

    public static void writeDataLineByLine(Context context, String filePath)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(context.getFilesDir().getPath() + filePath);
        Toast.makeText(context, context.getFilesDir().getPath(), Toast.LENGTH_LONG).show();
        try {
            file.createNewFile();
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Name", "Class", "Marks" };
            writer.writeNext(header);

            // add data to csv
            String[] data1 = { "Aman", "10", "620" };
            writer.writeNext(data1);
            String[] data2 = { "Suraj", "10", "630" };
            writer.writeNext(data2);

            // closing writer connection
            writer.close();
            //Intent intent = new Intent(Intent.ACTION_SEND);
            //intent.setType("text/csv");
            //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            //intent.putExtra(Intent.)

            //Toast.makeText(context, "Successfully Exported",Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}

