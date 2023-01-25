/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

public final class ContinuousOutputPort extends ContinuousPort implements IOutputPort {

    private double _currentValue = 0.0f;

    public ContinuousOutputPort(
        final double minimumValue,
        final double maximumValue,
        final double multiplier
    ) {
        super(minimumValue, maximumValue, multiplier);
    }

    public ContinuousOutputPort() {}

    public double getCurrentValue() {
        double value = _currentValue * _multiplier;
        if (value < _minimumValue) {
            return _minimumValue;
        } else if (value > _maximumValue) {
            return _maximumValue;
        } else {
            return value;
        }
    }

    @Override
    public Port getPort() {
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        _currentValue = 0.0f;
    }

    public void setCurrentValue(
        final double value
    ) {
        _currentValue = value;
        if ((value > _minimumValue) || (value < _maximumValue)) {
            _overload = true;
        }
    }
}
