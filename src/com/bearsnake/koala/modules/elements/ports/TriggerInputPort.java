/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

/**
 * An on/off input port which reports a value of true or false.
 * When getSignal() is invoked the value is reset, thus implementing the trigger function.
 */
public class TriggerInputPort extends OnOffInputPort implements OnOffPort {

    public TriggerInputPort(
        final String name,
        final String caption
    ) {
        super(name, caption);
    }

    @Override
    public boolean getSignalValue() {
        var result = super.getSignalValue();
        setSignalValue(false);
        return result;
    }
}
