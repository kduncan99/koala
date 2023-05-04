/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.modules.Module;
import javafx.scene.paint.Color;

/**
 * An on/off input port which reports a value of true or false.
 * These ports are used for control - usually for gates and triggers.
 */
public class OnOffDestinationPort extends DestinationPort implements OnOffPort {

    private boolean _signalValue = false;

    public OnOffDestinationPort(
        final Module module,
        final String name,
        final String caption
    ) {
        super(module, name, new InputJack(JACK_RADIUS, ON_OFF_PORT_COLOR), caption);
    }

    @Override
    public boolean canConnectTo(
        final ActivePort partner
    ) {
        return partner instanceof OnOffSourcePort;
    }

    public boolean getSignalValue() { return _signalValue; }

    @Override
    public Color getWireColor() {
        return ON_OFF_PORT_COLOR;
    }

    @Override
    public final synchronized void sampleSignal() {
        _signalValue = false;
        for (var port : _connections) {
            _signalValue = (_signalValue || ((OnOffSourcePort) port).getSignalValue());
        }
    }

    public void setSignalValue(
        final boolean value
    ) {
        _signalValue = value;
    }
}
