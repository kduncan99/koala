/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.components;

/**
 * Used by SelectorButton controls to notify listeners that the button state has changed.
 */
public class SelectorButtonComponentMessage extends ComponentMessage {

    private final int _newSelectorValue;

    public SelectorButtonComponentMessage(
        final int newSelectorValue
    ) {
        _newSelectorValue = newSelectorValue;
    }

    public int getNewSelectorValue() { return _newSelectorValue; }
}
