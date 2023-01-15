/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.exceptions.CannotConnectPortException;

public final class DiscreteInputPort extends ContinuousPort implements IInputPort {

    private DiscreteOutputPort _source = null;

    public DiscreteInputPort(
        final String name,
        final String abbreviation
    ) {
        super(name, abbreviation);
    }

    @Override
    public void connectTo(
        final IOutputPort source
    ) {
        if (source instanceof DiscreteOutputPort) {
            _source = (DiscreteOutputPort) source;
        } else {
            throw new CannotConnectPortException(this, (Port) source);
        }
    }

    @Override
    public void disconnect() {
        _source = null;
    }

    @Override
    public Port getPort() {
        return this;
    }

    @Override
    public IOutputPort getSourcePort() {
        return _source;
    }

    public int getValue() {
        if (_source == null) {
            return 0;
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
        super.reset();
    }
}
