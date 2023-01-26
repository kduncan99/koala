/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.messages;

public class SelectorButtonMessage extends Message {

    private final int _newSelectorValue;

    public SelectorButtonMessage(
        final Object sender,
        final int newSelectorValue
    ) {
        super(sender);
        _newSelectorValue = newSelectorValue;
    }

    public int getNewSelectorValue() { return _newSelectorValue; }
}
