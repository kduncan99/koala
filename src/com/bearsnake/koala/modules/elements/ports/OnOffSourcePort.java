/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.modules.Module;
import javafx.scene.paint.Color;

/**
 * An on/off output port which reports a value of true or false.
 * These ports are used for control - usually for gates and triggers.
 */
public class OnOffSourcePort extends SourcePort implements OnOffPort {

    private boolean _signalValue = false;

    public OnOffSourcePort(
        final Module module,
        final String name,
        final String caption
    ) {
        super(module, name, new OutputJack(JACK_RADIUS, ON_OFF_PORT_COLOR), caption);
    }

    @Override
    public boolean canConnectTo(
        final ActivePort partner
    ) {
        return partner instanceof OnOffDestinationPort;
    }

    public boolean getSignalValue() { return _signalValue; }

    @Override
    public Color getWireColor() {
        return ON_OFF_PORT_COLOR;
    }

    public final void setSignalValue(
        final boolean signalValue
    ) {
        _signalValue = signalValue;
    }
}
