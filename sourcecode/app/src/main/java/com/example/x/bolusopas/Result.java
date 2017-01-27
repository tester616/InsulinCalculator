package com.example.x.bolusopas;




public class Result {

    private int id;
    private Moment creationMoment;
    private Moment expirationMoment;
    private double insulinAmount;

    public Result(int id, Moment creationMoment, Moment expirationMoment, double insulinAmount) {
        this.id = id;
        this.creationMoment = creationMoment;
        this.expirationMoment = expirationMoment;
        this.insulinAmount = insulinAmount;
    }

    @Override
    public String toString() {
        return String.format("Insuliinin määrä: "+insulinAmount+"%n"+
                "Luotiin: "+creationMoment.toString()+"%n"+
                "Loppuu: "+expirationMoment.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Moment getCreationMoment() {
        return creationMoment;
    }

    public void setCreationMoment(Moment creationMoment) {
        this.creationMoment = creationMoment;
    }

    public Moment getExpirationMoment() {
        return expirationMoment;
    }

    public void setExpirationMoment(Moment expirationMoment) {
        this.expirationMoment = expirationMoment;
    }

    public double getInsulinAmount() {
        return insulinAmount;
    }

    public void setInsulinAmount(double insulinAmount) {
        this.insulinAmount = insulinAmount;
    }

    public String getClassData()
    {
        return "(RESULT BEGIN"+
                " id = "+id+
                ", creationMoment = "+creationMoment+
                ", expirationMoment = "+expirationMoment+
                ", insulinAmount = "+insulinAmount+
                " RESULT END)";
    }
}
