/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.exceptions;

public class BadPortIndexException extends RuntimeException {

    public final int _requestedPort;

    public BadPortIndexException(
        final int requestedPort
    ) {
        super(String.format("Bad port index %d", requestedPort));
        _requestedPort = requestedPort;
    }
}
