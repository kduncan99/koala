/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.components;

/**
 * Used by PotentiometerKnob components to notify listeners that the knob has been adjusted.
 */
public class PotentiometerKnobComponentMessage extends ComponentMessage {

    private final double _position;     //  knob position from 0.0 to 1.0

    public PotentiometerKnobComponentMessage(
        final double position
    ) {
        _position = position;
    }

    public double getPosition() { return _position; }
}
