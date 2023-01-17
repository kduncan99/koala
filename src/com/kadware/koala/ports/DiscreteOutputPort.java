/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

public final class DiscreteOutputPort extends DiscretePort implements IOutputPort {

    private int _currentValue = 0;

    public DiscreteOutputPort() {}

    public int getCurrentValue() {
        return _currentValue;
    }

    @Override
    public Port getPort() {
        return this;
    }

    @Override
    public void reset() {
        _currentValue = 0;
    }

    public void setCurrentValue(
        final int value
    ) {
        _currentValue = value;
    }
}
