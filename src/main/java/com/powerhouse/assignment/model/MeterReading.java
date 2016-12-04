package com.powerhouse.assignment.model;


public class MeterReading {

    String month;

    int reading;

    public MeterReading(String month, int reading) {
        this.month = month;
        this.reading = reading;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getReading() {
        return reading;
    }

    public void setReading(int reading) {
        this.reading = reading;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if(o instanceof Fraction){
            Fraction that = (Fraction) o;
            return month != null ? month.equalsIgnoreCase(that.month) : that.month == null;
        }else if(o instanceof MeterReading) {
            MeterReading that = (MeterReading) o;
            return month != null ? month.equalsIgnoreCase(that.month) : that.month == null;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return month != null ? month.hashCode() : 0;
    }
}
