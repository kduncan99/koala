package com.kadware.koala.exceptions;

public class BadParameter extends RuntimeException {

    public BadParameter(
        final int requestedPort
    ) {
        super("Bad parameter specified");
    }
}
