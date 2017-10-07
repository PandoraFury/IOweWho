package com.monoceroses.iowewho;

/**
 * Created by Megan on 8/27/2017.
 */

public class Person {
    private String personName;
    private double currentPersonTotal;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public double getCurrentPersonTotal() {
        return currentPersonTotal;
    }

    public void setCurrentPersonTotal(double currentPersonTotal) {
        this.currentPersonTotal = currentPersonTotal;
    }

    public void addToCurrentTotal(double amount){
        currentPersonTotal+=amount;
    }
}
