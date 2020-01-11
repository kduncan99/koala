package com.kadware.koala.exceptions;

public class BadSignalValueException extends RuntimeException {

    public final double _parameter;

    public BadSignalValueException(
        final double parameter
    ) {
        super(String.format("Bad signal value %f", parameter));
        _parameter = parameter;
    }
}
