/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.exceptions.CannotConnectPortException;

public final class ContinuousInputPort extends ContinuousPort implements IInputPort {

    private ContinuousOutputPort _source = null;

    public ContinuousInputPort(
        final DoubleRange range
    ) {
        super(range);
    }

    public ContinuousInputPort() {}

    @Override
    public void connectTo(
        final IOutputPort source
    ) {
        if (source instanceof ContinuousOutputPort) {
            _source = (ContinuousOutputPort) source;
        } else {
            throw new CannotConnectPortException(this, (Port) source);
        }
    }

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

    public double getValue() {
        var value = (_source == null) ? 0.0 : _source.getCurrentValue();
        if (value > getRange().getHighValue() || value < getRange().getLowValue()) {
            setOverload();
            value = getRange().clipValue(value);
        }
        return value;
    }

    @Override
    public boolean isConnected() {
        return _source != null;
    }

    @Override
    public void reset() {
        super.reset();
    }
}
