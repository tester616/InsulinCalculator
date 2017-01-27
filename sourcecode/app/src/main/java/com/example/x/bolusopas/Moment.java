package com.example.x.bolusopas;



public class Moment {

    private int day,
            month,
            year,
            hour,
            minute;

    public Moment() {

    }

    public Moment(
            int day,
            int month,
            int year,
            int hour,
            int minute
    ) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString()
    {
        return getDay()+"."+
                getMonth()+"."+
                getYear()+" "+
                getHour()+":"+
                getMinute();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    //Return all information about this class as a String.
    public String getClassData() {
        return "(MOMENT BEGIN"+
                " day = "+day+
                " month = "+month+
                " year = "+year+
                " hour = "+hour+
                " minute = "+minute+
                " MOMENT END)";
    }
}
