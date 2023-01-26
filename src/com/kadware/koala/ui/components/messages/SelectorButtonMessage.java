/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.messages;

/**
 * Used by SelectorButton controls to notify listeners that the button state has changed.
 */
public class SelectorButtonMessage extends ComponentMessage {

    private final int _newSelectorValue;

    public SelectorButtonMessage(
        final Object sender,
        final int identifier,
        final int newSelectorValue
    ) {
        super(sender, identifier);
        _newSelectorValue = newSelectorValue;
    }

    public int getNewSelectorValue() { return _newSelectorValue; }
}
