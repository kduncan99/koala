/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

/**
 * An on/off output port which reports a value of true or false.
 * These ports are used for control - usually for gates and triggers.
 */
public class OnOffOutputPort extends OutputPort implements OnOffPort {

    private boolean _signalValue = false;

    public OnOffOutputPort(
        final String caption
    ) {
        super(new OutputJack(JACK_RADIUS, ON_OFF_PORT_COLOR), caption);
    }

    @Override
    public boolean canConnectTo(
        final Port partner
    ) {
        return partner instanceof OnOffInputPort;
    }

    public boolean getSignalValue() { return _signalValue; }

    @Override
    public void repaint() {}

    public final void setSignalValue(
        final boolean signalValue
    ) {
        _signalValue = signalValue;
    }
}
