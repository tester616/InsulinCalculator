package com.example.x.bolusopas;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.DecimalFormat;
import java.util.Date;


public class Support {

    private Context ctx;
    private SharedPreferences prefs;

    public Support(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveStringToSP(String keyword, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyword, value);
        editor.commit();
    }

    public void saveIntToSP(String keyword, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(keyword, value);
        editor.commit();
    }

    public void saveDoubleToSP(String keyword, double value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(keyword, Double.doubleToRawLongBits(value));
        editor.commit();
    }

    public String getStringFromSP(String keyword) {
        return prefs.getString(keyword, "NULL");
    }

    public int getIntFromSP(String keyword) {
        return prefs.getInt(keyword, GV.BADVALUE);
    }

    public double getDoubleFromSP(String keyword) {
        return Double.longBitsToDouble(prefs.getLong(keyword, (long) GV.BADVALUE));
    }

    public void recreateSharedPreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public boolean checkIfSPKeywordExists(String keyword) {
        return prefs.contains(keyword);
    }

    public double calculateInsulin(double VS, double HH, int mode) {
        double TA = getDoubleFromSP("TA"+mode);
        double KS = getDoubleFromSP("KS"+mode);
        double HS = getDoubleFromSP("HS"+mode);
        double IOB = getDoubleFromSP("IOB");
        if(
                VS >= 0 &&
                HH >= 0 &&
                TA != GV.BADVALUE &&
                KS != GV.BADVALUE &&
                HS != GV.BADVALUE &&
                IOB != GV.BADVALUE
        ) {
            double result = ((VS - TA) / KS + (HH / HS)) - IOB;

            System.out.println(result);
            return result;
        }
        else {
            return (double) GV.BADVALUE;
        }
    }

    public double calculateRemainingInsulin(DateTime creationDT, DateTime expirationDT, double insulinAmount) {
        if(hasDateArrived(expirationDT, getCurrentDT())) {
            return 0;
        }

        DateTime creationDTWithDelay = creationDT.plusMinutes(GV.INSULIN_EFFECT_DELAY_MINUTES);
        long totalDuration = expirationDT.getMillis() - creationDTWithDelay.getMillis();
        long durationLeft = expirationDT.getMillis() - getCurrentDT().getMillis();
        double leftInDecimal = (double) durationLeft / (double) totalDuration;
        if(leftInDecimal > 1) {
            leftInDecimal = 1;
        }
        double insulinLeft = insulinAmount * leftInDecimal;

        System.out.println(totalDuration);
        System.out.println(durationLeft);
        System.out.println(leftInDecimal);
        System.out.println(insulinAmount);
        System.out.println(insulinLeft);

        return insulinLeft;
    }

    public double roundToTwoDecimals(double d) {

        /*double x = 45.6789;
        System.out.println("in parts: initial "+x);
        x = Math.round(x * 100);
        System.out.println("in parts: * 100 & rounded "+x);
        x = x / 100;
        System.out.println("in parts: final "+x);

        double y = 45.6789;
        System.out.println("in one go: initial "+y);
        y = Math.round(y * 100) / 100;
        System.out.println("in one go: final "+y);*/

        return Math.round(d * 100.0) / 100.0;
    }

    public DateTime getCurrentDT() {
        return new DateTime(DateTimeZone.getDefault());
    }

    public Moment getMomentWithDT(DateTime dt) {
        return new Moment(
                dt.getDayOfMonth(),
                dt.getMonthOfYear(),
                dt.getYear(),
                dt.getHourOfDay(),
                dt.getMinuteOfHour()
        );
    }

    public DateTime getDTWithMoment(Moment m) {
        return new DateTime(
                m.getYear(),
                m.getMonth(),
                m.getDay(),
                m.getHour(),
                m.getMinute()
        );
    }

    public double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    //Check if a date has arrived compared to another (current date used in program).
    public boolean hasDateArrived(DateTime comparisonDT, DateTime currentDT)
    {
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "year: " + comparisonDT.year().get() + " vs " + currentDT.year().get());
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "month: "+comparisonDT.monthOfYear().get()+" vs "+currentDT.monthOfYear().get());
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "day: "+comparisonDT.dayOfMonth().get()+" vs "+currentDT.dayOfMonth().get());
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "hour: "+comparisonDT.hourOfDay().get()+" vs "+currentDT.hourOfDay().get());
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "minute: "+comparisonDT.minuteOfHour().get()+" vs "+currentDT.minuteOfHour().get());

        //A series of if/else statements starting from year, moving all the way to minute while checking if
        //the current date/time has passed the activation date/time.
        if(comparisonDT.year().get() < currentDT.year().get())
        {
            Log.v("MTCYear", "first, true");
            return true;
        }
        else if(comparisonDT.year().get() == currentDT.year().get())
        {
            Log.v("MTCYear", "second, neutral");
            if(comparisonDT.monthOfYear().get() < currentDT.monthOfYear().get())
            {
                Log.v("MTCmonth", "first, true");
                return true;
            }
            else if(comparisonDT.monthOfYear().get() == currentDT.monthOfYear().get())
            {
                Log.v("MTCmonth", "second, neutral");
                if(comparisonDT.dayOfMonth().get() < currentDT.dayOfMonth().get())
                {
                    Log.v("MTCday", "first, true");
                    return true;
                }
                else if(comparisonDT.dayOfMonth().get() == currentDT.dayOfMonth().get())
                {
                    Log.v("MTCday", "second, neutral");
                    if(comparisonDT.hourOfDay().get() < currentDT.hourOfDay().get())
                    {
                        Log.v("MTChour", "first, true");
                        return true;
                    }
                    else if(comparisonDT.hourOfDay().get() == currentDT.hourOfDay().get())
                    {
                        Log.v("MTChour", "second, neutral");
                        if(comparisonDT.minuteOfHour().get() <= currentDT.minuteOfHour().get())
                        {
                            Log.v("MTCminute", "first, true");
                            return true;
                        }
                        else
                        {
                            Log.v("MTCminute", "second, false");
                            return false;
                        }
                    }
                    else
                    {
                        Log.v("MTChour", "third, false");
                        return false;
                    }
                }
                else
                {
                    Log.v("MTCday", "third, false");
                    return false;
                }
            }
            else
            {
                Log.v("MTCmonth", "third, false");
                return false;
            }
        }
        else
        {
            Log.v("MTCYear", "third, false");
            return false;
        }
    }
}
