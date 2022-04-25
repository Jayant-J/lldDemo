package com.example.geektrust.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonTest {

    @Test
    public void equals_shouldCompareUsingName() {
        Person person1 = new Person("Abc");
        Person person2 = new Person("Abc");
        Person person3 = new Person("Def");
        assertTrue(person1.equals(person2));
        assertFalse(person1.equals(person3));
    }

}