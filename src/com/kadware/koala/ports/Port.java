/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

public abstract class Port {

    protected double _multiplier = 1.0;
    public final String _name;
    protected boolean _overload = false;

    public Port(
        final String name
    ) {
        _name = name;
    }

    public final void clearOverload() {
        _overload = false;
    }

    public static void connectPorts(
        final OutputPort source,
        final InputPort destination
    ) {
        destination.connectTo(source);
    }

    public void reset() {
        _multiplier = 1.0;
        _overload = false;
    }

    public final void setMultiplier(
        final double value
    ) {
        if (value > 1.0) {
            _multiplier = 1.0;
        } else if (value < 0.0) {
            _multiplier = 0.0;
        } else {
            _multiplier = value;
        }
    }
}
