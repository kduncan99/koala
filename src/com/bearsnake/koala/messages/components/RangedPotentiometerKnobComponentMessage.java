/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.components;

/**
 * Used by PotentiometerKnob components to notify listeners that the knob has been adjusted.
 */
public class RangedPotentiometerKnobComponentMessage extends ComponentMessage {

    private final double _scaledValue;      //  knob value according to the potentiometer range

    public RangedPotentiometerKnobComponentMessage(
        final double scaledValue
    ) {
        _scaledValue = scaledValue;
    }

    public double getScaledValue() { return _scaledValue; }
}
