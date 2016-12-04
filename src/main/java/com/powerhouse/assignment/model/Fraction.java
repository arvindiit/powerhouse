package com.powerhouse.assignment.model;

public class Fraction {

    String month;

    double fraction;

    public Fraction(String month, double fraction) {
        this.month = month;
        this.fraction = fraction;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getFraction() {
        return fraction;
    }

    public void setFraction(double fraction) {
        this.fraction = fraction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if(o instanceof MeterReading){
            MeterReading meterReading = (MeterReading) o;
            return month != null ? month.equals(meterReading.month) : meterReading.month == null;
        }else if(o instanceof Fraction) {
            Fraction fraction = (Fraction) o;

            return month != null ? month.equals(fraction.month) : fraction.month == null;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return month != null ? month.hashCode() : 0;
    }
}
