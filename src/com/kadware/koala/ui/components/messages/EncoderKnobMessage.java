/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.messages;

/**
 * Used by SelectorButton controls to notify listeners that the button state has changed.
 */
public class EncoderKnobMessage extends ComponentMessage {

    public enum Direction {
        CLOCK_WISE,
        COUNTER_CLOCK_WISE,
    }

    private final Direction _direction;

    public EncoderKnobMessage(
        final Object sender,
        final int identifier,
        final Direction direction
    ) {
        super(sender, identifier);
        _direction = direction;
    }

    public Direction getDirection() { return _direction; }
}
