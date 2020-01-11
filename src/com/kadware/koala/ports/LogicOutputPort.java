/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

public final class LogicOutputPort extends DiscretePort implements IOutputPort {

    private boolean _currentValue = false;

    public LogicOutputPort(
        final String name,
        final String abbreviation
    ) {
        super(name, abbreviation);
    }

    public boolean getCurrentValue() {
        return _currentValue;
    }

    @Override
    public void reset() {
        _currentValue = false;
    }

    public void setCurrentValue(
        final boolean value
    ) {
        _currentValue = value;
    }
}
