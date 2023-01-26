/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.messages;

/**
 * Used by SelectorButton controls to notify listeners that the button state has changed.
 */
public class EncoderKnobMessage extends ComponentMessage {

    private final int _newSelectorValue;

    public EncoderKnobMessage(
        final Object sender,
        final int identifier,
        final int newSelectorValue
    ) {
        super(sender, identifier);
        _newSelectorValue = newSelectorValue;
    }

    public int getNewSelectorValue() { return _newSelectorValue; }
}
