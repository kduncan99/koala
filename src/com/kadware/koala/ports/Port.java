/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

/**
 * Base class for all input and output ports of all types.
 * Every module has at least one port.
 */
public abstract class Port {

    /**
     * assigns an output port as the source for an input port
     */
    public static void connectPorts(
        final IOutputPort outputPort,
        final IInputPort inputPort
    ) {
        inputPort.connectTo(outputPort);
    }

    /**
     * Resets the port.
     * Functionality is specific to the particular subclass (most probably don't do anything)
     */
    public abstract void reset();
}
