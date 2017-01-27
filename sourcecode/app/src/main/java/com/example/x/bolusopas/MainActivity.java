package com.example.x.bolusopas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLanguage();
        createFieldsWithCurrentLanguage();
        setDefaultSPValues();

        //Only used to delete current tables from the database and recreating them empty.
        //recreateDatabase();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // Recreate database if it doesn't exist..
    private void recreateDatabase() {
        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());
        DQ.upgradeDatabase();
    }

    public void setDefaultSPValues() {
        Support s = new Support(getApplicationContext());
        for(int i = 1; i < 5; i++) {
            if(!s.checkIfSPKeywordExists("TA"+i)) s.saveDoubleToSP("TA"+i, (double) GV.BADVALUE);
            if(!s.checkIfSPKeywordExists("KS"+i)) s.saveDoubleToSP("KS"+i, (double) GV.BADVALUE);
            if(!s.checkIfSPKeywordExists("HS"+i)) s.saveDoubleToSP("HS"+i, (double) GV.BADVALUE);
            if(!s.checkIfSPKeywordExists("IV"+i)) s.saveDoubleToSP("IV"+i, (double) GV.BADVALUE);
            if(!s.checkIfSPKeywordExists("IMA"+i)) s.saveDoubleToSP("IMA"+i, (double) GV.BADVALUE);
        }
        s.saveIntToSP("language", GV.LANGUAGE_FINNISH);
    }

    // Button methods.
    public void calculateButton(View view) {
        Intent newUserIntent = new Intent(this, Calculate.class);
        startActivity(newUserIntent);
    }

    public void settingsButton(View view) {
        Intent newUserIntent = new Intent(this, Settings.class);
        startActivity(newUserIntent);
    }

    public void historyButton(View view) {
        Intent newUserIntent = new Intent(this, History.class);
        startActivity(newUserIntent);
    }

    public void languageButton(View view) {
        Intent newUserIntent = new Intent(this, LanguageSettings.class);
        startActivity(newUserIntent);
    }
}
