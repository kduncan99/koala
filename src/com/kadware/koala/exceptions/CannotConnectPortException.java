/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.exceptions;

import com.kadware.koala.ports.Port;

public class CannotConnectPortException extends RuntimeException {

    public CannotConnectPortException(
        final Port inputPort,
        final Port outputPort
    ) {
        super(String.format("Cannot connect input port %s to output port %s",
                            inputPort,
                            outputPort));
    }
}
