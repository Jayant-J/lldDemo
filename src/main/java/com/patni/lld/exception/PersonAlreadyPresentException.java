package com.patni.lld.exception;

public class PersonAlreadyPresentException extends Exception {

    private String name;

    public PersonAlreadyPresentException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Person with name `" + name + "` already present.\nUse another name";
    }

}
