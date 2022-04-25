package com.patni.lld.model;

import java.util.HashMap;
import java.util.Map;

public class BalanceSheet {

    //    personWhoOwes->(personTo, amount)
    Map<Person, Map<Person, Integer>> balanceSheet;

    public BalanceSheet() {
        this.balanceSheet = new HashMap<>();
    }

    public Map<Person, Map<Person, Integer>> getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(Map<Person, Map<Person, Integer>> balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

}
