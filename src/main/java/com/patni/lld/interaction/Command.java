package com.patni.lld.interaction;

import com.patni.lld.exception.InvalidParameterException;

public interface Command {
    String helpText();

    void execute(String[] params) throws InvalidParameterException;
}
