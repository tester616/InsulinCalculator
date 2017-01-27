package com.example.x.bolusopas;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class LanguageSettings extends Activity {

    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_settings);

        setLanguage();
        createFieldsWithCurrentLanguage();
        setFieldValues();
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
        getMenuInflater().inflate(R.menu.menu_language_settings, menu);
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

    public void setFieldValues() {
        RadioButton englishRadioButton = (RadioButton) findViewById(R.id.englishRadioButton);
        RadioButton finnishRadioButton = (RadioButton) findViewById(R.id.finnishRadioButton);
        RadioButton swedishRadioButton = (RadioButton) findViewById(R.id.swedishRadioButton);

        Support s = new Support(getApplicationContext());

        if(s.getIntFromSP("language") == GV.LANGUAGE_ENGLISH) {
            englishRadioButton.setChecked(true);
        }
        else if(s.getIntFromSP("language") == GV.LANGUAGE_FINNISH) {
            finnishRadioButton.setChecked(true);
        }
        else if(s.getIntFromSP("language") == GV.LANGUAGE_SWEDISH) {
            swedishRadioButton.setChecked(true);
        }
        else {
            Log.v("Error in setFieldValues", "No language was found.");
        }
    }

    public void saveButton(View view) {
        RadioButton englishRadioButton = (RadioButton) findViewById(R.id.englishRadioButton);
        RadioButton finnishRadioButton = (RadioButton) findViewById(R.id.finnishRadioButton);
        RadioButton swedishRadioButton = (RadioButton) findViewById(R.id.swedishRadioButton);

        Support s = new Support(getApplicationContext());

        if(englishRadioButton.isChecked()) {
            s.saveIntToSP("language", GV.LANGUAGE_ENGLISH);
        }
        else if(finnishRadioButton.isChecked()) {
            s.saveIntToSP("language", GV.LANGUAGE_FINNISH);
        }
        else if(swedishRadioButton.isChecked()) {
            s.saveIntToSP("language", GV.LANGUAGE_SWEDISH);
        }
        else {
            Log.v("Error in saveButton()", "No RadioButton for language was checked.");
        }

        setLanguage();
        createFieldsWithCurrentLanguage();
        setFieldValues();

        Toast.makeText(getBaseContext(), lang.DIALOG_SETTINGS_SAVED, Toast.LENGTH_LONG).show();
    }

    public void backButton(View view) {
        finish();
    }
}
