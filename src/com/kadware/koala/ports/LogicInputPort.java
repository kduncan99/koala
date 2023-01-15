/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.exceptions.CannotConnectPortException;

public class LogicInputPort extends DiscretePort implements IInputPort {

    private LogicOutputPort _source = null;

    public LogicInputPort(
        final String name,
        final String abbreviation
    ) {
        super(name, abbreviation);
    }

    @Override
    public void connectTo(
        final IOutputPort source
    ) {
        if (source instanceof LogicOutputPort) {
            _source = (LogicOutputPort) source;
        } else {
            throw new CannotConnectPortException(this, (Port) source);
        }
    }

    @Override
    public void disconnect() {
        _source = null;
    }

    @Override
    public IOutputPort getSourcePort() {
        return _source;
    }

    @Override
    public Port getPort() {
        return this;
    }

    public boolean getValue() {
        if (_source == null) {
            return false;
        } else {
            return _source.getCurrentValue();
        }
    }

    @Override
    public final boolean isConnected() {
        return _source != null;
    }

    @Override
    public void reset() {
    }
}
