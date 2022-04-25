package com.patni.lld.service;

import com.patni.lld.exception.HouseFullException;
import com.patni.lld.exception.PersonAlreadyPresentException;
import com.patni.lld.exception.PersonNotPresentException;
import com.patni.lld.model.BalanceSheet;
import com.patni.lld.model.House;
import com.patni.lld.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.patni.lld.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseManagementServiceTest {

    House house;
    List<Person> personList;
    ExpenseManagementService expenseManagementService;
    BalanceSheet balanceSheet;
    ByteArrayOutputStream os = new ByteArrayOutputStream(100);
    PrintStream capture = new PrintStream(os);


    @BeforeEach
    void setUp() {
        house = new House();
        personList = new ArrayList<>();
        balanceSheet = new BalanceSheet();
        house.setSize(HOUSE_SIZE);
        house.setPersonList(personList);
        house.setBalanceSheet(balanceSheet);
        expenseManagementService = new ExpenseManagementService(house);
    }

    @Test
    void personMoveIn_shouldBeSuccessFull() throws PersonAlreadyPresentException {
        System.setOut(capture);
        expenseManagementService.personMoveIn("person1");
        capture.flush();
        String res = os.toString().trim();
        assertEquals(SUCCESS, res);
    }

    @Test
    void personMoveIn_shouldThrowHouseFullException() throws PersonAlreadyPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");
        expenseManagementService.personMoveIn("person3");
        assertThrows(HouseFullException.class, () -> expenseManagementService.personMoveIn("person4"));
    }

    @Test
    void personMoveIn_shouldThrowPersonAlreadyPresentException() throws PersonAlreadyPresentException {
        expenseManagementService.personMoveIn("person1");
        assertThrows(PersonAlreadyPresentException.class, () -> expenseManagementService.personMoveIn("person1"));
    }

    @Test
    void personMoveOut_shouldFailBecauseOfDues() throws PersonNotPresentException, PersonAlreadyPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");
//        Add dues to person 1
        Map<Person, Integer> person1Dues = balanceSheet.getBalanceSheet().get(new Person("person1"));
        person1Dues.put(new Person("preson2"), 100);

        System.setOut(capture);
        expenseManagementService.personMoveOut("person1");
        capture.flush();
        String res = os.toString().trim();

        assertEquals(FAILURE, res);
    }

    @Test
    void personMoveOut_shouldFailBecausePersonOwesFromSomeone() throws PersonNotPresentException, PersonAlreadyPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");
//        Add dues to person 1
        Map<Person, Integer> person1Dues = balanceSheet.getBalanceSheet().get(new Person("person1"));
        person1Dues.put(new Person("person2"), 100);

        System.setOut(capture);
        expenseManagementService.personMoveOut("person2");
        capture.flush();
        String res = os.toString().trim();

        assertEquals(FAILURE, res);
    }

    @Test
    void personMoveOut_Success() throws PersonNotPresentException, PersonAlreadyPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");

        System.setOut(capture);
        expenseManagementService.personMoveOut("person2");
        capture.flush();
        String res = os.toString().trim();

        assertEquals(SUCCESS, res);
    }

    @Test
    void personMoveOut_shouldThrowPersonNotPresentException() throws PersonNotPresentException {
        assertThrows(PersonNotPresentException.class, () -> expenseManagementService.personMoveOut("person1"));
    }

    @Test
    void spentLogic_shouldThrowPersonNotPresentException() throws PersonAlreadyPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");

        assertThrows(PersonNotPresentException.class, () -> expenseManagementService.spentLogic(1000, "person1", new String[]{"person"}));
        assertThrows(PersonNotPresentException.class, () -> expenseManagementService.spentLogic(1000, "person", new String[]{"person2"}));
    }

    @Test
    void spentLogic_SuccessWhenNoEarlierBalance() throws PersonAlreadyPresentException, PersonNotPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");

        expenseManagementService.spentLogic(1000, "person1", new String[]{"person2"});
    }

    @Test
    void spentLogic_SuccessWithEarlierBalance() throws PersonAlreadyPresentException, PersonNotPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");

        expenseManagementService.spentLogic(1000, "person1", new String[]{"person2"});
        expenseManagementService.spentLogic(1000, "person1", new String[]{"person2"});
        expenseManagementService.spentLogic(1000, "person2", new String[]{"person1"});
        expenseManagementService.spentLogic(1000, "person2", new String[]{"person1"});
    }

    @Test
    void getDues_shouldThrowPersonNotPresentException() throws PersonNotPresentException {
        assertThrows(PersonNotPresentException.class, () -> expenseManagementService.getDues("person"));
    }

    @Test
    void getDues_Success() throws PersonAlreadyPresentException, PersonNotPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");
        expenseManagementService.spentLogic(1000, "person1", new String[]{"person2"});
        System.setOut(capture);
        expenseManagementService.getDues("person2");
        capture.flush();
        String res = os.toString().trim();
        assertEquals("person1 500", res);
    }

    @Test
    void clearDues_shouldThrowPersonNotPresentException() throws PersonAlreadyPresentException, PersonNotPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");
        expenseManagementService.spentLogic(1000, "person1", new String[]{"person2"});

        assertThrows(PersonNotPresentException.class, () -> expenseManagementService.clearDues("person", "person2", 500));
        assertThrows(PersonNotPresentException.class, () -> expenseManagementService.clearDues("person1", "person", 500));
    }

    @Test
    void clearDues_shouldReturnIncorrectPayment() throws PersonAlreadyPresentException, PersonNotPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");
        expenseManagementService.spentLogic(1000, "person1", new String[]{"person2"});

        System.setOut(capture);
        expenseManagementService.clearDues("person2", "person1", 1000);
        capture.flush();
        String res = os.toString().trim();

        assertEquals(res, INCORRECT_PAYMENT);
    }

    @Test
    void clearDues_Success() throws PersonAlreadyPresentException, PersonNotPresentException {
        expenseManagementService.personMoveIn("person1");
        expenseManagementService.personMoveIn("person2");
        expenseManagementService.spentLogic(1000, "person1", new String[]{"person2"});

        System.setOut(capture);
        expenseManagementService.clearDues("person2", "person1", 500);
        capture.flush();
        String res = os.toString().trim();

        assertEquals(res, "0");
    }
}