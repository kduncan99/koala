/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

/**
 * Methods which must be implemented by an input port.
 */
public interface IInputPort {

    void connectTo(
        final IOutputPort outputPort
    );

    void disconnect();
    Port getPort();
    IOutputPort getSourcePort();
    boolean isConnected();
    void reset();
}
