/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.modules.Module;
import javafx.scene.paint.Color;

/**
 * An analog output connection which is intended to report a value from -1.0 to 1.0.
 * These ports are used both for audio and for control.
 * The normative value range *can* be exceeded, and this connection has an output clip indicator.
 * to indicate when the range has been exceeded.
 */
public class AnalogSourcePort extends SourcePort implements AnalogPort {

    private double _signalValue = 0.0;

    public AnalogSourcePort(
        final Module module,
        final String name,
        final String caption
    ) {
        super(module, name, new OutputJack(JACK_RADIUS, ANALOG_PORT_COLOR), caption);
    }

    @Override
    public boolean canConnectTo(
        final ActivePort partner
    ) {
        return partner instanceof AnalogDestinationPort;
    }

    public double getSignalValue() { return _signalValue; }

    @Override
    public Color getWireColor() {
        return ANALOG_PORT_COLOR;
    }

    public final void setSignalValue(
        final double signalValue
    ) {
        _signalValue = signalValue;
    }
}
