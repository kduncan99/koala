/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

/**
 * An analog input connection which is intended to report a value from -1.0 to 1.0.
 * These ports are used both for audio and for control.
 * The normative value range *can* be exceeded, and this connection has an output clip indicator.
 * to indicate when the range has been exceeded.
 */
public class AnalogInputPort extends InputPort implements AnalogPort {

    private double _signalValue = 0.0;

    public AnalogInputPort(
        final String caption
    ) {
        super(new InputJack(JACK_RADIUS, ANALOG_PORT_COLOR), caption);
    }

    @Override
    public boolean canConnectTo(
        final Port partner
    ) {
        return partner instanceof AnalogOutputPort;
    }

    public double getSignalValue() { return _signalValue; }

    @Override
    public void repaint() {}

    @Override
    public final synchronized void sampleSignal() {
        _signalValue = 0.0;
        for (var port : _connections) {
            _signalValue += ((AnalogOutputPort) port).getSignalValue();
        }

        if (Math.abs(_signalValue) > 1.0) {
            setOverloadIndicator(true);
        }
    }
}