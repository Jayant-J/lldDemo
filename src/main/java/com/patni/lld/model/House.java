package com.patni.lld.model;

import java.util.LinkedList;
import java.util.List;

import static com.patni.lld.utils.Constants.HOUSE_SIZE;

public class House {

    List<Person> personList;
    int size;
    BalanceSheet balanceSheet;

    public House() {
        this.personList = new LinkedList<>();
        this.size = HOUSE_SIZE;
        this.balanceSheet = new BalanceSheet();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public int getSize() {
        return size;
    }

    public BalanceSheet getBalanceSheet() {
        return balanceSheet;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setBalanceSheet(BalanceSheet balanceSheet) {
        this.balanceSheet = balanceSheet;
    }
}
