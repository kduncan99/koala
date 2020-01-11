/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.exceptions;

public class BadParameter extends RuntimeException {

    public BadParameter(
        final int requestedPort
    ) {
        super("Bad parameter specified");
    }
}
