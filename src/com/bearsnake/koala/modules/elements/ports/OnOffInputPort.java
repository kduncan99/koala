/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

/**
 * An on/off input port which reports a value of true or false.
 * These ports are used for control - usually for gates and triggers.
 */
public class OnOffInputPort extends InputPort implements OnOffPort {

    private boolean _signalValue = false;

    public OnOffInputPort(
        final String caption
    ) {
        super(new InputJack(JACK_RADIUS, ON_OFF_PORT_COLOR), caption);
    }

    @Override
    public boolean canConnectTo(
        final Port partner
    ) {
        return partner instanceof OnOffOutputPort;
    }

    public boolean getSignalValue() { return _signalValue; }

    @Override
    public void repaint() {}

    @Override
    public final synchronized void sampleSignal() {
        _signalValue = false;
        for (var port : _connections) {
            _signalValue = (_signalValue || ((OnOffOutputPort) port).getSignalValue());
        }
    }

    public void setSignalValue(
        final boolean value
    ) {
        _signalValue = value;
    }
}
