/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.modules.Module;
import javafx.scene.paint.Color;

/**
 * An analog input connection which is intended to report a value from -1.0 to 1.0.
 * These ports are used both for audio and for control.
 * The normative value range *can* be exceeded, and this connection has an output clip indicator
 * to indicate when the range has been exceeded.
 */
public class AnalogDestinationPort extends DestinationPort implements AnalogPort {

    private double _signalValue = 0.0;

    public AnalogDestinationPort(
        final Module module,
        final String name,
        final String caption
    ) {
        super(module, name, new InputJack(JACK_RADIUS, ANALOG_PORT_COLOR), caption);
    }

    @Override
    public boolean canConnectTo(
        final ActivePort partner
    ) {
        return partner instanceof AnalogSourcePort;
    }

    @Override
    public Color getWireColor() {
        return ANALOG_PORT_COLOR;
    }

    public double getSignalValue() { return _signalValue; }

    @Override
    public final synchronized void sampleSignal() {
        _signalValue = 0.0;
        for (var port : _connections) {
            _signalValue += ((AnalogSourcePort) port).getSignalValue();
        }

        var magnitude = Math.abs(_signalValue);
        if (magnitude > 1.0) {
            ((InputJack) _jack).setOverload();
        } else if (magnitude > 0.2) {
            ((InputJack) _jack).setSignalDetected();
        }
    }
}
