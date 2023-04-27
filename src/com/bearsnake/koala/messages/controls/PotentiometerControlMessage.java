/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.controls;

import com.bearsnake.koala.messages.components.ComponentMessage;

/**
 * Used by PotentiometerKnob controls to notify listeners that the knob has been adjusted.
 */
public class PotentiometerControlMessage extends ComponentMessage {

    private final double _value;

    public PotentiometerControlMessage(
        final double position
    ) {
        _value = position;
    }

    public double getValue() { return _value; }
}
