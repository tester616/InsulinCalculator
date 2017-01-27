package com.example.x.bolusopas;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

public class DatabaseQuery
{
    private SQLiteDatabase SQLiteDB;
    private DatabaseHelper DBH;

    public DatabaseQuery() {

    }

    public DatabaseQuery(Context ctx) {
        DBH = new DatabaseHelper(ctx);
    }

    public void closeDatabaseHelper() {
        DBH.close();
    }

    public void upgradeDatabase() {
        SQLiteDB = DBH.getWritableDatabase();
        DBH.onUpgrade(SQLiteDB, 1, 1);
    }

    public void createResult(Result r) {
        SQLiteDB = DBH.getWritableDatabase();

        Gson gson = new Gson();

        String creationTimeJson = gson.toJson(r.getCreationMoment());
        String expirationTimeJson = gson.toJson(r.getExpirationMoment());
        String insulinAmount = Double.toString(r.getInsulinAmount());


        String table = DatabaseHelper.TABLE_RESULT;

        ContentValues CV = new ContentValues();
        CV.put(DatabaseHelper.COLUMN_TIME_CREATION, creationTimeJson);
        CV.put(DatabaseHelper.COLUMN_TIME_EXPIRATION, expirationTimeJson);
        CV.put(DatabaseHelper.COLUMN_INSULIN_AMOUNT, insulinAmount);

        try {
            SQLiteDB.insert(table, null, CV);
        }
        catch(Exception e) {
            //Display message if there is an error.
            Log.v("DatabaseQuery createReminder()", e.getMessage());
        }
    }

    public Cursor getResults() {
        Cursor CR = null;
        SQLiteDB = DBH.getReadableDatabase();
        String table = DatabaseHelper.TABLE_RESULT;

        try {
            CR = SQLiteDB.query(table, null, null, null, null, null, null);
        }
        catch(Exception e) {
            Log.v("DatabaseQuery getResults()", e.getMessage());
        }

        return CR;
    }

    public void deleteResult(int id) {
        SQLiteDatabase SQLiteDB = DBH.getWritableDatabase();
        String table = DatabaseHelper.TABLE_RESULT;
        String selection = DatabaseHelper.COLUMN_RESULT_ID+" LIKE ?";
        String[] selectionArgs = {""+id};

        try {
            SQLiteDB.delete(table, selection, selectionArgs);
        }
        catch(Exception e) {
            Log.v("DatabaseQuery deleteResult()", e.getMessage());
        }
    }

    public void createCurrentResult(Result r) {
        SQLiteDB = DBH.getWritableDatabase();

        Gson gson = new Gson();

        String creationTimeJson = gson.toJson(r.getCreationMoment());
        String expirationTimeJson = gson.toJson(r.getExpirationMoment());
        String fthis = Double.toString(r.getInsulinAmount());

        String table = DatabaseHelper.TABLE_CURRENT_RESULT;

        ContentValues CV = new ContentValues();
        CV.put(DatabaseHelper.COLUMN_CURRENT_TIME_CREATION, creationTimeJson);
        CV.put(DatabaseHelper.COLUMN_CURRENT_TIME_EXPIRATION, expirationTimeJson);
        CV.put(DatabaseHelper.COLUMN_CURRENT_INSULIN_AMOUNT, fthis);

        try {
            SQLiteDB.insert(table, null, CV);
        }
        catch(Exception e) {
            Log.v("DatabaseQuery createReminder()", e.getMessage());
        }
    }

    public Cursor getCurrentResult() {
        Cursor CR = null;
        SQLiteDB = DBH.getReadableDatabase();
        String table = DatabaseHelper.TABLE_CURRENT_RESULT;

        try {
            CR = SQLiteDB.query(table, null, null, null, null, null, null);
        }
        catch(Exception e) {
            Log.v("DatabaseQuery getResults()", e.getMessage());
        }

        return CR;
    }

    public void deleteCurrentResult(int id) {
        SQLiteDatabase SQLiteDB = DBH.getWritableDatabase();
        String table = DatabaseHelper.TABLE_CURRENT_RESULT;
        String selection = DatabaseHelper.COLUMN_CURRENT_RESULT_ID+" LIKE ?";
        String[] selectionArgs = {""+id};

        try {
            SQLiteDB.delete(table, selection, selectionArgs);
        }
        catch(Exception e) {
            Log.v("DatabaseQuery deleteResult()", e.getMessage());
        }
    }

    /*public void updateCurrentResult(Result r) {
        SQLiteDatabase SQLiteD = DBH.getWritableDatabase();
        Gson gson = new Gson();

        String initialActivationJson = gson.toJson(R.getInitialActivation());

        //Variables used in the update.
        String table = DatabaseHelper.TABLE_REMINDER;
        String selection = DatabaseHelper.COLUMN_REMINDER_ID+" LIKE ?";
        String[] args = {""+R.getId()};
        ContentValues CV = new ContentValues();
        CV.put(DatabaseHelper.COLUMN_REMINDER_TYPE, R.getType());

        //Tries to execute the update.
        try
        {
            //Executes the update.
            SQLiteD.update(table, CV, selection, args);
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery updateReminder()", e.getMessage());
        }
    }*/

    //Skapa en användare.
    /*public void createUser(String username, String password)
    {
        SQLiteDB = DBH.getWritableDatabase();
        String table = DatabaseHelper.TABLE_USER;
        ContentValues CV = new ContentValues();
        CV.put(DatabaseHelper.COLUMN_USER_USERNAME, username);
        CV.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);

        try
        {
            //Metoden insert() används för att skapa en ny rad data till databasen där table är
            // namnet för tabellen och CV innehåller kolumnernas data.
            SQLiteDB.insert(table, null, CV);
        }
        catch(Exception e)
        {
            Log.v("DatabaseQuery createUser()", e.getMessage());
        }
    }

    //Radera en användare.
    public void deleteUser(String username)
    {
        SQLiteDatabase SQLiteDB = DBH.getWritableDatabase();
        String table = DatabaseHelper.TABLE_USER;
        String selection = DatabaseHelper.COLUMN_USER_USERNAME+" LIKE ?";
        String[] selectionArgs = {username};

        try
        {
            //Metoden delete() används för att radera data från databasen. Här pekar table på
            // tabellen, medan selection pekar på en kolumn och en eller flera värden radernas data
            // i den kolumnen kan ta för att raderas. Namnet/namnen på ? plats/platser ges av
            // selectionArgs.
            SQLiteDB.delete(table, selection, selectionArgs);
        }
        catch(Exception e)
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery deleteUser()", e.getMessage());
        }
    }

    //Returnera lösenordet av en användare.
    public Cursor getPasswordForUsername(String username)
    {
        Cursor CR = null;
        SQLiteDB = DBH.getReadableDatabase();
        String table = DatabaseHelper.TABLE_USER;
        String[] columns = {DatabaseHelper.COLUMN_USER_PASSWORD};
        String selection = DatabaseHelper.COLUMN_USER_USERNAME+" LIKE ?";
        String[] selectionArgs = {username};

        try
        {
            //För returnering av data används query(). Variablerna table, selection och
            // selectionArgs används med samma logik som i delete(). Kolumnerna som returneras
            // per rad av data anges med columns.
            CR = SQLiteDB.query(table, columns, selection, selectionArgs, null, null, null);
        }
        catch(Exception e)
        {
            Log.v("DatabaseQuery getPasswordForUsername()", e.getMessage());
        }

        return CR;
    }
    //Resten av klassens metoder använder samma principer som metoderna ovan.

    //For deleting all users.
    public void deleteAllUsers()
    {
        //Prepares SQLiteDB for the delete by getting a writable database from DBH.
        SQLiteDatabase SQLiteDB = DBH.getWritableDatabase();

        //Variables used in the delete.
        String table = DatabaseHelper.TABLE_USER;

        //Tries to execute the delete.
        try
        {
            //Executes the delete.
            SQLiteDB.delete(table, null, null);
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery deleteAllUsers()", e.getMessage());
        }
    }

    //For checking if a specific user exists.
    public Boolean userExists(String username)
    {
        //Empty cursor initialization.
        Cursor CR = null;

        //Prepares SQLiteDB for the query by getting a readable database from DBH.
        SQLiteDB = DBH.getReadableDatabase();

        //Variables for usage in the query.
        String table = DatabaseHelper.TABLE_USER;
        String selection = DatabaseHelper.COLUMN_USER_USERNAME+" LIKE ?";
        String[] selectionArgs = {username};

        //Tries to execute the query.
        try
        {
            CR = SQLiteDB.query(table, null, selection, selectionArgs, null, null, null);
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery userExists()", e.getMessage());
        }

        //If CR manages to move to the first position a user was found and true is returned.
        if(CR == null)
        {
            return false;
        }
        else
        {
            return CR.moveToFirst();
        }
    }

    //For checking if there are any users. True for empty table.
    public int getTableAmountOfRows(String table)
    {
        //Empty cursor initialization.
        Cursor CR = null;

        //Prepares SQLiteDB for the query by getting a readable database from DBH.
        SQLiteDB = DBH.getReadableDatabase();

        //Tries to execute the query.
        try
        {
            CR = SQLiteDB.query(table, null, null, null, null, null, null);
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery getTableAmountOfRows()", e.getMessage());
        }

        //Return amount of rows the table has.
        if(CR == null)
        {
            return 0;
        }
        else
        {
            return CR.getCount();
        }
    }

    //For creating a new Reminder.
    public void createReminder(Reminder R)
    {
        //Prepares SQLiteD for the insert by getting a writable database from DBH.
        SQLiteDB = DBH.getWritableDatabase();

        //Creates new object of Gson.
        Gson gson = new Gson();

        //Uses Gson to convert objects to JSON strings (JSON String is still a normal String) that can be stored to the database.
        String initialActivationJson = gson.toJson(R.getInitialActivation());
        String imageDataJson = gson.toJson(R.getImageData());
        String soundDataJson = gson.toJson(R.getSoundData());
        String repeatDataJson = gson.toJson(R.getRepeatData());
        String latestActivationJson = gson.toJson(R.getLatestActivation());

        //Variables used in the insert.
        String table = DatabaseHelper.TABLE_REMINDER;
        ContentValues CV = new ContentValues();
        CV.put(DatabaseHelper.COLUMN_REMINDER_TYPE, R.getType());
        CV.put(DatabaseHelper.COLUMN_REMINDER_NAME, R.getName());
        CV.put(DatabaseHelper.COLUMN_REMINDER_NOTE, R.getNote());
        CV.put(DatabaseHelper.COLUMN_REMINDER_INITIAL_ACTIVATION, initialActivationJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_IMAGE_DATA, imageDataJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_SOUND_DATA, soundDataJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_REPEAT_DATA, repeatDataJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_LATEST_ACTIVATION, latestActivationJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_RUN_AMOUNT, 0);

        //Tries to execute the insert.
        try
        {
            //Executes the insert. A call to insert also returns a long, but it's not used for anything in this program.
            SQLiteDB.insert(table, null, CV);
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery createReminder()", e.getMessage());
        }
    }

    //For updating information about a previously created reminder row.
    public void updateReminder(Reminder R)
    {
        //Prepares SQLiteD for the update by getting a writable database from DBH.
        SQLiteDatabase SQLiteD = DBH.getWritableDatabase();

        //Creates new object of Gson.
        Gson gson = new Gson();

        //Uses Gson to convert objects to JSON strings (JSON String is still a normal String) that can be stored to the database.
        String initialActivationJson = gson.toJson(R.getInitialActivation());
        String imageDataJson = gson.toJson(R.getImageData());
        String soundDataJson = gson.toJson(R.getSoundData());
        String repeatDataJson = gson.toJson(R.getRepeatData());
        String latestActivationJson = gson.toJson(R.getLatestActivation());

        //Variables used in the update.
        String table = DatabaseHelper.TABLE_REMINDER;
        String selection = DatabaseHelper.COLUMN_REMINDER_ID+" LIKE ?";
        String[] args = {""+R.getId()};
        ContentValues CV = new ContentValues();
        CV.put(DatabaseHelper.COLUMN_REMINDER_TYPE, R.getType());
        CV.put(DatabaseHelper.COLUMN_REMINDER_NAME, R.getName());
        CV.put(DatabaseHelper.COLUMN_REMINDER_NOTE, R.getNote());
        CV.put(DatabaseHelper.COLUMN_REMINDER_INITIAL_ACTIVATION, initialActivationJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_IMAGE_DATA, imageDataJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_SOUND_DATA, soundDataJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_REPEAT_DATA, repeatDataJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_LATEST_ACTIVATION, latestActivationJson);
        CV.put(DatabaseHelper.COLUMN_REMINDER_RUN_AMOUNT, R.getAmountOfTimesDisplayed());

        //Tries to execute the update.
        try
        {
            //Executes the update.
            SQLiteD.update(table, CV, selection, args);
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery updateReminder()", e.getMessage());
        }
    }

    //For deleting a previously created reminder row.
    public void deleteReminder(int id)
    {
        //Prepares SQLiteD for the delete by getting a writable database from DBH.
        SQLiteDatabase SQLiteD = DBH.getWritableDatabase();

        //Variables used in the delete.
        String table = DatabaseHelper.TABLE_REMINDER;
        String selection = DatabaseHelper.COLUMN_REMINDER_ID+" LIKE ?";
        String[] selectionArgs = {""+id};

        //Tries to execute the delete.
        try
        {
            //Executes the delete.
            SQLiteD.delete(table, selection, selectionArgs);
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery deleteReminder()", e.getMessage());
        }
    }

    //Get a list containing information about reminders.
    //0 for all reminders.
    //1 for medicines only.
    //2 for notifications only.
    public Cursor getReminderInformation(int type)
    {
        //Empty cursor initialization.
        Cursor CR = null;

        //Prepares SQLiteD for the query by getting a readable database from DBH.
        SQLiteDB = DBH.getReadableDatabase();

        //Variables for usage in the query.
        String table = DatabaseHelper.TABLE_REMINDER;
        String selection = DatabaseHelper.COLUMN_REMINDER_TYPE+" LIKE ?";
        String[] selectionArgs = {""+type};

        //Null needs to be sent if all rows are to be returned in the query.
        //Runs only if TYPE_ALL (0) is given as type.
        if(type == GV.TYPE_ALL)
        {
            selection = null;
            selectionArgs = null;
        }

        //Tries to execute the query.
        try
        {
            //Execute query.
            CR = SQLiteDB.query(table, null, selection, selectionArgs, null, null, null);
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message if there is an error.
            Log.v("DatabaseQuery getReminderInformation()", e.getMessage());
        }

        //Return CR to the calling method.
        return CR;
    }*/
}