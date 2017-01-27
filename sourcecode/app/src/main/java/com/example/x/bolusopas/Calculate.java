package com.example.x.bolusopas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.util.ArrayList;


public class Calculate extends Activity {

    private Language lang;
    private double result = 0.0;
    private double roundedResult = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        setLanguage();
        createFieldsWithCurrentLanguage();
        populateSpinner();
        updateInsulinLeftTextView();
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
        getMenuInflater().inflate(R.menu.menu_calculate, menu);
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

    public void populateSpinner() {
        Spinner modeSpinner = (Spinner) findViewById(R.id.modeSpinner);

        ArrayList<Mode> modeSpinnerArray =  new ArrayList<Mode>();

        modeSpinnerArray.add(new Mode(getApplicationContext(), 1));
        modeSpinnerArray.add(new Mode(getApplicationContext(), 2));
        modeSpinnerArray.add(new Mode(getApplicationContext(), 3));
        modeSpinnerArray.add(new Mode(getApplicationContext(), 4));

        ArrayAdapter<Mode> modeSpinnerAdapter = new ArrayAdapter<Mode>(this, android.R.layout.simple_spinner_item, modeSpinnerArray);
        modeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeSpinnerAdapter);
    }

    private void updateInsulinLeftTextView() {
        TextView insulinLeftTextView = (TextView) findViewById(R.id.insulinLeftTextView);
        ArrayList<Double> insulinList = new ArrayList<Double>();
        Support s = new Support(getApplicationContext());
        DatabaseQuery dq = new DatabaseQuery(getApplicationContext());
        Gson gson = new Gson();
        Cursor cr;
        double totalInsulinRemaining = 0.0;

        cr = dq.getResults();
        while(cr.moveToNext()) {
            int id = cr.getInt(0);
            Moment creationMoment = gson.fromJson(cr.getString(1), Moment.class);
            Moment expirationMoment = gson.fromJson(cr.getString(2), Moment.class);
            double insulinAmount = cr.getDouble(3);

            Result r = new Result(
                    id,
                    creationMoment,
                    expirationMoment,
                    insulinAmount
            );
            double insulinLeft = s.calculateRemainingInsulin(
                    s.getDTWithMoment(r.getCreationMoment()),
                    s.getDTWithMoment(r.getExpirationMoment()),
                    r.getInsulinAmount()
            );
            insulinList.add(insulinLeft);
        }

        for(double insulinLeft : insulinList) {
            totalInsulinRemaining += insulinLeft;
        }

        totalInsulinRemaining = s.roundToTwoDecimals(totalInsulinRemaining);

        String insulinLeftTextViewContent = "Vaikuttava jäljellä oleva insuliinimäärä "+Double.toString(totalInsulinRemaining)+" yksikköä";

        insulinLeftTextView.setText(insulinLeftTextViewContent);

        dq.closeDatabaseHelper();
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

    public void calculateButton(View view) {
        EditText VSEditText = (EditText) findViewById(R.id.VSEditText);
        EditText HHEditText = (EditText) findViewById(R.id.HHEditText);
        TextView insulinNumberTextView = (TextView) findViewById(R.id.insulinNumberTextView);
        Support s = new Support(getApplicationContext());
        Spinner modeSpinner = (Spinner) findViewById(R.id.modeSpinner);
        Mode m = (Mode) modeSpinner.getSelectedItem();

        if(VSEditText.getText().toString().isEmpty() || HHEditText.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), lang.DIALOG_CALCULATION_FAILED, Toast.LENGTH_LONG).show();
            return;
        }

        result = s.calculateInsulin(
                Double.parseDouble(VSEditText.getText().toString()),
                Double.parseDouble(HHEditText.getText().toString()),
                m.getMode()
        );
        if(result < 0.0) result = 0.0;

        if(m.getIMA() == 1.0) {
            roundedResult = Math.round(result);
        }
        else if(m.getIMA() == 0.5) {
            roundedResult = s.roundToHalf(result);
        }
        else {
            Log.v("Error in calculateButton()", "Bad value for IMA. Must be either 1.0 or 0.5.");
            System.out.println(s.getDoubleFromSP("IMA"));
        }

        insulinNumberTextView.setText(Double.toString(roundedResult)+" ("+Double.toString(result)+")");
        VSEditText.setText("0.0");
        HHEditText.setText("0.0");
    }

    public void saveButton(View view) {
        Spinner modeSpinner = (Spinner) findViewById(R.id.modeSpinner);
        Mode m = (Mode) modeSpinner.getSelectedItem();
        Support s = new Support(getApplicationContext());
        DatabaseQuery dq = new DatabaseQuery(getApplicationContext());
        double oldInsulinRemaining = 0;
        /*Cursor cr;
        Gson gson = new Gson();
        Result oldR = null;

        cr = dq.getCurrentResult();

        while(cr.moveToNext()) {
            int id = cr.getInt(0);
            Moment oldCreationMoment = gson.fromJson(cr.getString(1), Moment.class);
            Moment oldExpirationMoment = gson.fromJson(cr.getString(2), Moment.class);
            double oldInsulinAmount = cr.getDouble(3);
            oldR = new Result(
                    id,
                    oldCreationMoment,
                    oldExpirationMoment,
                    oldInsulinAmount
            );
        }

        if(cr.getCount() > 0) {
            oldInsulinRemaining = s.calculateRemainingInsulin(
                    s.getDTWithMoment(oldR.getCreationMoment()),
                    s.getDTWithMoment(oldR.getExpirationMoment()),
                    oldR.getInsulinAmount()
            );
        }*/

        DateTime currentDT = s.getCurrentDT();
        Moment newCreationMoment = s.getMomentWithDT(currentDT);
        DateTime expirationDT = currentDT.plusHours((int) m.getIV());
        expirationDT = expirationDT.plusMinutes(GV.INSULIN_EFFECT_DELAY_MINUTES);
        Moment newExpirationMoment = s.getMomentWithDT(expirationDT);
        //double newResult = oldInsulinRemaining + roundedResult;
        //Result newCurrentR = new Result(0, newCreationMoment, newExpirationMoment, newResult);
        Result historyR = new Result(0, newCreationMoment, newExpirationMoment, roundedResult);

        dq.createResult(historyR);

        /*if(cr.getCount() > 0) {
            dq.deleteCurrentResult(oldR.getId());
        }
        dq.createCurrentResult(newCurrentR);*/

        updateInsulinLeftTextView();

        dq.closeDatabaseHelper();

        Toast.makeText(getBaseContext(), lang.DIALOG_RESULT_SAVED, Toast.LENGTH_LONG).show();
    }

    public void backButton(View view) {
        finish();
    }
}
