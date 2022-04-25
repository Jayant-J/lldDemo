package com.patni.lld.exception;

import static com.patni.lld.utils.Constants.HOUSEFUL;

public class HouseFullException extends RuntimeException {

    @Override
    public String getMessage() {
        return HOUSEFUL;
    }
}
