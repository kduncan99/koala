/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.messages;

public class PulseWidthMessage extends PanelMessage {

    private final double _value;

    public PulseWidthMessage(
        final Object sender,
        final int identifier,
        final double value
    ) {
        super(sender, identifier);
        _value = value;
    }

    public double getValue() { return _value; }
}
