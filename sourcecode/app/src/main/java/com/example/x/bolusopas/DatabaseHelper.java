package com.example.x.bolusopas;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bolus_opas_database",
            TABLE_RESULT = "result",
            COLUMN_RESULT_ID = "id",
            COLUMN_TIME_CREATION = "time_created",
            COLUMN_TIME_EXPIRATION = "time_expiration",
            COLUMN_INSULIN_AMOUNT = "insulin_amount",
            TABLE_CURRENT_RESULT = "current_result",
            COLUMN_CURRENT_RESULT_ID = "id",
            COLUMN_CURRENT_TIME_CREATION = "time_created",
            COLUMN_CURRENT_TIME_EXPIRATION = "time_expiration",
            COLUMN_CURRENT_INSULIN_AMOUNT = "insulin_amount",
            CREATE_TABLE_RESULT = "CREATE TABLE " + TABLE_RESULT + "("
                    + COLUMN_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TIME_CREATION + " VARCHAR NOT NULL, "
                    + COLUMN_TIME_EXPIRATION + " VARCHAR NOT NULL, "
                    + COLUMN_INSULIN_AMOUNT + " VARCHAR NOT NULL"
                    +");",
            CREATE_TABLE_CURRENT_RESULT = "CREATE TABLE " + TABLE_CURRENT_RESULT + "("
                    + COLUMN_CURRENT_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_CURRENT_TIME_CREATION + " VARCHAR NOT NULL, "
                    + COLUMN_CURRENT_TIME_EXPIRATION + " VARCHAR NOT NULL, "
                    + COLUMN_CURRENT_INSULIN_AMOUNT + " VARCHAR NOT NULL"
                    +");";

    public DatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_RESULT);
        db.execSQL(CREATE_TABLE_CURRENT_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_RESULT);

        onCreate(db);

        Log.v("Database upgrade", "Version " + oldVersion + " to " + newVersion);
    }
}
