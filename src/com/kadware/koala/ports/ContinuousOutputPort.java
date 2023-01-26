/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.DoubleRange;

public final class ContinuousOutputPort extends ContinuousPort implements IOutputPort {

    private double _currentValue = 0.0;

    public ContinuousOutputPort(
        final DoubleRange range
    ) {
        super(range);
    }

    public ContinuousOutputPort() {}

    public double getCurrentValue() {
        return _currentValue;
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
        if ((value > getRange().getHighValue()) || (value < getRange().getLowValue())) {
            setOverload();
            _currentValue = getRange().clipValue(value);
        } else {
            _currentValue = value;
        }
    }
}
