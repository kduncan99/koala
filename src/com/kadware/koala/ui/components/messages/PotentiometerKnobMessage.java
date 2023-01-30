/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.messages;

/**
 * Used by SelectorButton controls to notify listeners that the button state has changed.
 */
public class PotentiometerKnobMessage extends ComponentMessage {

    private final double _position;

    public PotentiometerKnobMessage(
        final Object sender,
        final int identifier,
        final double position
    ) {
        super(sender, identifier);
        _position = position;
    }

    public double getPosition() { return _position; }
}
