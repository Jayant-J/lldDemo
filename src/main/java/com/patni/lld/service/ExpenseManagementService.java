package com.patni.lld.service;

import com.patni.lld.exception.HouseFullException;
import com.patni.lld.exception.PersonAlreadyPresentException;
import com.patni.lld.exception.PersonNotPresentException;
import com.patni.lld.model.House;
import com.patni.lld.model.Person;
import com.patni.lld.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.patni.lld.utils.Constants.FAILURE;
import static com.patni.lld.utils.Constants.SUCCESS;

public class ExpenseManagementService {
    private House house;

    public ExpenseManagementService(House house) {
        this.house = house;
    }

    public void personMoveIn(String personName) throws PersonAlreadyPresentException, HouseFullException {
        Person newPerson = new Person(personName);
        if (isHouseFull()) {
            throw new HouseFullException();
        }
        if (isPersonPresent(newPerson)) {
            throw new PersonAlreadyPresentException(personName);
        }
        house.getPersonList().add(newPerson);
        house.getBalanceSheet().getBalanceSheet().put(newPerson, new HashMap<>());
        System.out.println(SUCCESS);
    }

    public void personMoveOut(String personName) throws PersonNotPresentException {
        Person person = new Person(personName);
        if (!isPersonPresent(person)) {
            throw new PersonNotPresentException(personName);
        }
        Map<Person, Map<Person, Integer>> balanceSheet = house.getBalanceSheet().getBalanceSheet();
//        check if person has to pay someone ie dues
        Map<Person, Integer> personDuesMap = balanceSheet.get(person);
        for (Map.Entry<Person, Integer> eachDue : personDuesMap.entrySet()) {
            if (eachDue.getValue() != 0) {
                System.out.println(FAILURE);
                return;
            }
        }
//        check if person has lent someone ie person will get
        for (Map.Entry<Person, Map<Person, Integer>> personMapEntry : balanceSheet.entrySet()) {
            if (!personMapEntry.getKey().equals(person)) {
                Map<Person, Integer> lentPersonMap = personMapEntry.getValue();
                if (lentPersonMap.get(person) != null && lentPersonMap.get(person) != 0) {
                    System.out.println(FAILURE);
                    return;
                }
            }
        }
        removePersonFromBalanceSheet(balanceSheet, person);
        house.getPersonList().remove(person);
        System.out.println(SUCCESS);
    }

    public void spentLogic(int amount, String byWhichPersonName, String[] spentForPersonNames) throws PersonNotPresentException {
        Person payingPerson = new Person(byWhichPersonName);

        if (!isPersonPresent(payingPerson)) {
            throw new PersonNotPresentException(byWhichPersonName);
        }
        for (String eachPerson : spentForPersonNames) {
            Person forWhomPerson = new Person(eachPerson);
            if (!isPersonPresent(forWhomPerson)) {
                throw new PersonNotPresentException(eachPerson);
            }
        }

        Map<Person, Map<Person, Integer>> balanceSheet = house.getBalanceSheet().getBalanceSheet();

        Integer eachPersonShare = amount / (spentForPersonNames.length + 1);
        for (String each : spentForPersonNames) {
            Person eachSpentForPerson = new Person(each);
            if (balanceSheet.get(payingPerson).get(eachSpentForPerson) == null || balanceSheet.get(payingPerson).get(eachSpentForPerson) == 0) {
                Map<Person, Integer> personAmountMap = balanceSheet.get(eachSpentForPerson);
                if (personAmountMap.get(payingPerson) == null) {
                    personAmountMap.put(payingPerson, eachPersonShare);
                } else {
                    personAmountMap.put(payingPerson, personAmountMap.get(payingPerson) + eachPersonShare);
                }
            } else {
                int remainingShare = balanceSheet.get(payingPerson).get(eachSpentForPerson) - eachPersonShare;
                Map<Person, Integer> personAmountMap = balanceSheet.get(payingPerson);
                if (remainingShare > 0) {
                    personAmountMap.put(eachSpentForPerson, Math.abs(remainingShare));
                } else {
                    personAmountMap.put(eachSpentForPerson, 0);
                    personAmountMap = balanceSheet.get(eachSpentForPerson);
                    personAmountMap.put(payingPerson, personAmountMap.getOrDefault(payingPerson, 0 + Math.abs(remainingShare)));
                }
            }
        }
        System.out.println(SUCCESS);
    }

    public void getDues(String personName) throws PersonNotPresentException {
        Person person = new Person(personName);
        if (!isPersonPresent(person)) {
            throw new PersonNotPresentException(personName);
        }
        Map<Person, Map<Person, Integer>> balanceSheet = house.getBalanceSheet().getBalanceSheet();
        Map<Person, Integer> personAmountMap = balanceSheet.get(person);
        for (Map.Entry<Person, Integer> each : personAmountMap.entrySet()) {
            System.out.println(each.getKey().getName() + " " + each.getValue());
        }
    }

    public void clearDues(String personNameWhoOwes, String personNameWhoLent, int amount) throws PersonNotPresentException {
        Person personWhoOwes = new Person(personNameWhoOwes);
        if (!isPersonPresent(personWhoOwes)) {
            throw new PersonNotPresentException(personNameWhoOwes);
        }
        Person personWhoLent = new Person(personNameWhoLent);
        if (!isPersonPresent(personWhoLent)) {
            throw new PersonNotPresentException(personNameWhoLent);
        }
        Map<Person, Map<Person, Integer>> balanceSheet = house.getBalanceSheet().getBalanceSheet();
        Map<Person, Integer> personAmountMap = balanceSheet.get(personWhoOwes);
        if (personAmountMap.get(personWhoLent) != null && personAmountMap.get(personWhoLent) >= amount) {
            int remainingDues = personAmountMap.get(personWhoLent) - amount;
            personAmountMap.put(personWhoLent, personAmountMap.get(personWhoLent) - amount);
            System.out.println(remainingDues);

        } else {
            System.out.println(Constants.INCORRECT_PAYMENT);
        }
    }

    private boolean isPersonPresent(Person person) {
        if (house.getPersonList().contains(person)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isHouseFull() {
        if (house.getPersonList().size() == house.getSize()) {
            return true;
        } else {
            return false;
        }
    }

    private void removePersonFromBalanceSheet(Map<Person, Map<Person, Integer>> balanceSheet, Person person) {
        balanceSheet.remove(person);
        for (Map.Entry<Person, Map<Person, Integer>> balanceSheetEntry : balanceSheet.entrySet()) {
            balanceSheetEntry.getValue().remove(person);
        }
    }
}