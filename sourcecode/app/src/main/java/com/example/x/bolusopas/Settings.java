package com.example.x.bolusopas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;


public class Settings extends Activity {

    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setLanguage();
        createFieldsWithCurrentLanguage();
        populateIVSpinner();
        populateIMASpinner();
        populateModeSpinner();
        setModeSpinnerListener();
    }

    private void setLanguage() {
        Support s = new Support(getApplicationContext());
        lang = new Language(s.getIntFromSP("language"));
    }

    private void createFieldsWithCurrentLanguage() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateModeSpinner() {
        Spinner modeSpinner = (Spinner) findViewById(R.id.modeSpinner);

        //A list is needed for the ArrayAdapter.
        ArrayList<Mode> modeSpinnerArray =  new ArrayList<Mode>();

        //The list needs to be filled with appropriate data.
        modeSpinnerArray.add(new Mode(getApplicationContext(), 1));
        modeSpinnerArray.add(new Mode(getApplicationContext(), 2));
        modeSpinnerArray.add(new Mode(getApplicationContext(), 3));
        modeSpinnerArray.add(new Mode(getApplicationContext(), 4));

        //Creation of ArrayAdapter.
        ArrayAdapter<Mode> modeSpinnerAdapter = new ArrayAdapter<Mode>(this, android.R.layout.simple_spinner_item, modeSpinnerArray);

        //Adjusting it to have the correct type of dropdown selection.
        modeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Linking together the spinner with the adapter.
        modeSpinner.setAdapter(modeSpinnerAdapter);
    }

    private void setModeSpinnerListener() {
        Spinner modeSpinner = (Spinner) findViewById(R.id.modeSpinner);

        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                updateFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });
    }

    private void populateIVSpinner() {
        Spinner IVSpinner = (Spinner) findViewById(R.id.IVSpinner);
        ArrayList<Double> IVSpinnerArray = new ArrayList<Double>();

        IVSpinnerArray.add(3.0);
        IVSpinnerArray.add(4.0);
        IVSpinnerArray.add(5.0);

        ArrayAdapter<Double> IVSpinnerAdapter = new ArrayAdapter<Double>(this, android.R.layout.simple_spinner_item, IVSpinnerArray);
        IVSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IVSpinner.setAdapter(IVSpinnerAdapter);
    }

    private void populateIMASpinner() {
        Spinner IMASpinner = (Spinner) findViewById(R.id.IMASpinner);
        ArrayList<Double> IMASpinnerArray = new ArrayList<Double>();

        IMASpinnerArray.add(0.5);
        IMASpinnerArray.add(1.0);

        ArrayAdapter<Double> IMASpinnerAdapter = new ArrayAdapter<Double>(this, android.R.layout.simple_spinner_item, IMASpinnerArray);
        IMASpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IMASpinner.setAdapter(IMASpinnerAdapter);
    }

    private void updateFields() {
        EditText TAEditText = (EditText) findViewById(R.id.TAEditText);
        EditText KSEditText = (EditText) findViewById(R.id.KSEditText);
        EditText HSEditText = (EditText) findViewById(R.id.HSEditText);
        Spinner IVSpinner = (Spinner) findViewById(R.id.IVSpinner);
        Spinner IMASpinner = (Spinner) findViewById(R.id.IMASpinner);
        Spinner modeSpinner = (Spinner) findViewById(R.id.modeSpinner);
        Mode m = (Mode) modeSpinner.getSelectedItem();

        TAEditText.setText(Double.toString(m.getTA()));
        KSEditText.setText(Double.toString(m.getKS()));
        HSEditText.setText(Double.toString(m.getHS()));

        // Loop spinners and set selection to matching value of the selected mode.
        for(int i = 0; i < IVSpinner.getCount(); i++) {
            if(Double.parseDouble(IVSpinner.getItemAtPosition(i).toString()) == m.getIV()) IVSpinner.setSelection(i);
        }
        for(int i = 0; i < IMASpinner.getCount(); i++) {
            if(Double.parseDouble(IMASpinner.getItemAtPosition(i).toString()) == m.getIMA()) IMASpinner.setSelection(i);
        }
    }

    // Button methods
    public void helpButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apu tähän.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveButton(View view) {
        EditText TAEditText = (EditText) findViewById(R.id.TAEditText);
        EditText KSEditText = (EditText) findViewById(R.id.KSEditText);
        EditText HSEditText = (EditText) findViewById(R.id.HSEditText);
        Spinner IVSpinner = (Spinner) findViewById(R.id.IVSpinner);
        Spinner IMASpinner = (Spinner) findViewById(R.id.IMASpinner);
        Spinner modeSpinner = (Spinner) findViewById(R.id.modeSpinner);
        Mode m = (Mode) modeSpinner.getSelectedItem();
        int modeInt = m.getMode();
        Support s = new Support(getApplicationContext());

        s.saveDoubleToSP("TA"+modeInt, Double.parseDouble(TAEditText.getText().toString()));
        s.saveDoubleToSP("KS"+modeInt, Double.parseDouble(KSEditText.getText().toString()));
        s.saveDoubleToSP("HS"+modeInt, Double.parseDouble(HSEditText.getText().toString()));
        s.saveDoubleToSP("IV"+modeInt, Double.parseDouble(IVSpinner.getSelectedItem().toString()));
        s.saveDoubleToSP("IMA"+modeInt, Double.parseDouble(IMASpinner.getSelectedItem().toString()));

        Toast.makeText(getBaseContext(), lang.DIALOG_SETTINGS_SAVED, Toast.LENGTH_LONG).show();
    }

    // Button methods
    public void backButton(View view) {
        finish();
    }
}