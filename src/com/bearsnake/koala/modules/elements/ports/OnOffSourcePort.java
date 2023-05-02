/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import javafx.scene.paint.Color;

/**
 * An on/off output port which reports a value of true or false.
 * These ports are used for control - usually for gates and triggers.
 */
public class OnOffSourcePort extends SourcePort implements OnOffPort {

    private boolean _signalValue = false;

    public OnOffSourcePort(
        final String moduleName,
        final String name,
        final String caption
    ) {
        super(moduleName, name, new OutputJack(JACK_RADIUS, ON_OFF_PORT_COLOR), caption);
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

    @Override
    public void repaint() {}

    public final void setSignalValue(
        final boolean signalValue
    ) {
        _signalValue = signalValue;
    }
}
