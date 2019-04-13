package com.example.tradetrackerpro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

            mSettings.setEmail(mEmailField.getText().toString());
            mSettings.setCat1(mCat1Field.getText().toString());
            mSettings.setCat2(mCat2Field.getText().toString());
            mSettings.setCat3(mCat3Field.getText().toString());
            mSettings.setCat4(mCat4Field.getText().toString());
            mSettings.setCat5(mCat5Field.getText().toString());
            mSettings.setAcct1(mAcct1Field.getText().toString());
            mSettings.setAcct2(mAcct2Field.getText().toString());
            mSettings.setAcct3(mAcct3Field.getText().toString());
            mSettings.setTradeCounter(mTradeCounterSelect.getSelectedItem().toString());
            mSettings.setImportantMessage(null);

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

            super.onPause();
        }
    }

