/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.components;

/**
 * Used by SelectorButton controls to notify listeners that the button state has changed.
 */
public class EncoderKnobComponentMessage extends ComponentMessage {

    public enum Direction {
        CLOCK_WISE,
        COUNTER_CLOCK_WISE,
    }

    private final Direction _direction;

    public EncoderKnobComponentMessage(
        final Direction direction
    ) {
        _direction = direction;
    }

    public Direction getDirection() { return _direction; }
}
