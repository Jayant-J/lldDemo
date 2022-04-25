package com.patni.lld.exception;

public class PersonNotPresentException extends Exception {

    private String name;

    public PersonNotPresentException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Person with name `" + name + "` not present in house.\nTransaction not processed";
    }

}
