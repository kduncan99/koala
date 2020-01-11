package com.kadware.koala.ports;

import com.kadware.koala.Koala;

/**
 * An output port holds onto a particular value (between MIN_VALUE and MAX_VALUE inclusive)
 * so that input ports may solicit said value.
 */
public final class OutputPort extends Port {

    private double _currentValue = 0.0;

    public OutputPort(
        final String name
    ) {
        super(name);
    }

    public double getCurrentValue() {
        return _currentValue * _multiplier;
    }

    @Override
    public void reset() {
        super.reset();
        _currentValue = 0.0;
    }

    public void setCurrentValue(
        final double value
    ) {
        if (value > Koala.MAX_PORT_VALUE) {
            _currentValue = Koala.MAX_PORT_VALUE;
            _overload = true;
        } else if (value < Koala.MIN_PORT_VALUE) {
            _currentValue = Koala.MIN_PORT_VALUE;
            _overload = true;
        } else {
            _currentValue = value;
        }
    }
}
