/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

public abstract class Port {

    public final String _abbreviation;
    public final String _name;

    public Port(
        final String name,
        final String abbreviation
    ) {
        _name = name;
        _abbreviation = abbreviation;
    }

    public static void connectPorts(
        final IOutputPort outputPort,
        final IInputPort inputPort
    ) {
        inputPort.connectTo(outputPort);
    }

    public final String getName() {
        return _name;
    }

    public abstract void reset();
}
