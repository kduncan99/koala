/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

/**
 * An analog output connection which is intended to report a value from -1.0 to 1.0.
 * These ports are used both for audio and for control.
 * The normative value range *can* be exceeded, and this connection has an output clip indicator.
 * to indicate when the range has been exceeded.
 */
public class AnalogOutputPort extends OutputPort implements AnalogPort {

    private double _signalValue = 0.0;

    public AnalogOutputPort(
        final String caption
    ) {
        super(new OutputJack(JACK_RADIUS, ANALOG_PORT_COLOR), caption);
    }

    @Override
    public boolean canConnectTo(
        final Port partner
    ) {
        return partner instanceof AnalogInputPort;
    }

    public double getSignalValue() { return _signalValue; }

    @Override
    public void repaint() {}

    public final void setSignalValue(
        final double signalValue
    ) {
        _signalValue = signalValue;
        if (Math.abs(_signalValue) > 1.0) {
            setOverloadIndicator(true);
        }
    }
}
