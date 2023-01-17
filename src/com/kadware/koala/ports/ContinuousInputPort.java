/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.exceptions.CannotConnectPortException;

public final class ContinuousInputPort extends ContinuousPort implements IInputPort {

    private ContinuousOutputPort _source = null;

    public ContinuousInputPort(
        final float minimumValue,
        final float maximumValue,
        final float multiplier
    ) {
        super(minimumValue, maximumValue, multiplier);
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

    public float getValue() {
        if (_source == null) {
            return (_maximumValue + _minimumValue) / 2.0f;
        } else {
            float value = _source.getCurrentValue() * _multiplier;
            if (value > _maximumValue) {
                _overload = true;
                return _maximumValue;
            } else if (value < _minimumValue) {
                _overload = true;
                return _minimumValue;
            } else {
                return value;
            }
        }
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
