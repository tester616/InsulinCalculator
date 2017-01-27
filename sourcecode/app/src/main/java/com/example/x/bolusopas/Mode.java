package com.example.x.bolusopas;

import android.content.Context;


public class Mode {

    private Context ctx;
    private int mode;
    private String name;
    private double TA;
    private double KS;
    private double HS;
    private double IV;
    private double IMA;

    public Mode(Context ctx, int mode) {
        this.ctx = ctx;
        this.mode = mode;
        String TAName = "TA"+mode;
        String KSName = "KS"+mode;
        String HSName = "HS"+mode;
        String IVName = "IV"+mode;
        String IMAName = "IMA"+mode;
        Support s = new Support(ctx);
        TA = s.getDoubleFromSP(TAName);
        KS = s.getDoubleFromSP(KSName);
        HS = s.getDoubleFromSP(HSName);
        IV = s.getDoubleFromSP(IVName);
        IMA = s.getDoubleFromSP(IMAName);
        if(mode == 1) name = "aamu";
        else if(mode == 2) name = "päivä";
        else if(mode == 3) name = "yö";
        else if(mode == 4) name = "oma";
        else name = "unknown mode";
    }

    @Override
    public String toString() {
        return name;
    }

    public int getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public double getTA() {
        return TA;
    }

    public double getKS() {
        return KS;
    }

    public double getHS() {
        return HS;
    }

    public double getIV() {
        return IV;
    }

    public double getIMA() {
        return IMA;
    }
}
