/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.Koala;

/**
 * An input port pulls a value from a connected output port upon request by a containing module.
 */
public final class InputPort extends Port {

    private OutputPort _source = null;

    public InputPort(
        final String name
    ) {
        super(name);
    }

    public void connectTo(
        final OutputPort source
    ) {
        _source = source;
    }

    public void disconnect() {
        _source = null;
    }

    public double getValue() {
        if (_source == null) {
            return 0.0;
        } else {
            double value = _source.getCurrentValue() * _multiplier;
            if (value > Koala.MAX_PORT_VALUE) {
                _overload = true;
                return Koala.MAX_PORT_VALUE;
            } else if (value < Koala.MIN_PORT_VALUE) {
                _overload = true;
                return Koala.MIN_PORT_VALUE;
            } else {
                return value;
            }
        }
    }

    public boolean isConnected() {
        return _source != null;
    }

    @Override
    public void reset() {
        super.reset();
    }
}
