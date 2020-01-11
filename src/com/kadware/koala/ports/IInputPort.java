/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

/**
 * Methods which must be implemented by an input port.
 */
public interface IInputPort {

    public void connectTo(
        final IOutputPort outputPort
    );

    public void disconnect();
    public IOutputPort getSourcePort();
    public boolean isConnected();
    public void reset();
}
