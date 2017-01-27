package com.example.x.bolusopas;

import android.app.Activity;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;


public class History extends Activity implements OwnListFragment.OnFragmentInteractionListener {

    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setLanguage();
        createFieldsWithCurrentLanguage();
        deleteExpiredResults();
        //populateResultListView();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.FragmentContainer, new OwnListFragment())
                    .commit();
        }
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
        getMenuInflater().inflate(R.menu.menu_history, menu);
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

    @Override
    public void onFragmentInteraction(String id) {

    }

    private void deleteExpiredResults() {
        DatabaseQuery dq = new DatabaseQuery(getApplicationContext());
        Gson gson = new Gson();
        Cursor cr = null;
        cr = dq.getResults();
        Support s = new Support(getApplicationContext());

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

            if(
                    s.hasDateArrived(
                            s.getDTWithMoment(
                                    r.getExpirationMoment()
                            ),
                            s.getCurrentDT()
                    )
            ) {
                dq.deleteResult(r.getId());
            }
        }
    }

    public void populateResultListView() {
        ListView resultListView = (ListView) findViewById(R.id.resultListView);

        DatabaseQuery dq = new DatabaseQuery(getApplicationContext());
        Gson gson = new Gson();
        Cursor cr = null;
        cr = dq.getResults();
        ArrayList<Result> results = new ArrayList<Result>();

        while(cr.moveToNext()) {
            int id = cr.getInt(0);
            Moment creationMoment = gson.fromJson(cr.getString(1), Moment.class);
            Moment expirationMoment = gson.fromJson(cr.getString(2), Moment.class);
            double insulinAmount = cr.getDouble(3);

            results.add(
                    new Result(
                            id,
                            creationMoment,
                            expirationMoment,
                            insulinAmount
                    )
            );
        }

        ArrayAdapter<Result> adapter = new ArrayAdapter<Result>(this, android.R.layout.simple_list_item_1, android.R.id.text1, results);
        resultListView.setAdapter(adapter);

        dq.closeDatabaseHelper();
    }

    // Button methods
    public void backButton(View view) {
        finish();
    }
}
