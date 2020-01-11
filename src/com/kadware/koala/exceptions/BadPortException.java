/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.exceptions;

public class BadPortException extends RuntimeException {

    public final int _requestedPort;

    public BadPortException(
        final int requestedPort
    ) {
        super(String.format("Bad port number %d", requestedPort));
        _requestedPort = requestedPort;
    }
}
