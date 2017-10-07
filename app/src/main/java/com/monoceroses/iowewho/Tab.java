package com.monoceroses.iowewho;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Megan on 8/24/2017.
 */

public class Tab {
    private ArrayList<Person> nameList;
    private String tabName;
    private double currentTotal;


    Tab(String name)
    {
        tabName = name;
        nameList = new ArrayList<Person>();
    }

    protected void addToTotal(double amount){
        currentTotal+=amount;
    }

    protected double getCurrentTotal(){
        return currentTotal;
    }

    public void addName(Person name)
    {
        nameList.add(name);
    }

    public ArrayList<Person> getNameList() {
        return nameList;
    }

    public void setNameList(ArrayList<Person> nameList) {
        this.nameList = nameList;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}
