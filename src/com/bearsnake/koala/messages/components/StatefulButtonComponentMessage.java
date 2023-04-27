/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.components;

/**
 * Used by the StatefulButton controls to notify listeners that the button state has changed.
 */
public class StatefulButtonComponentMessage extends ComponentMessage {

    private final boolean _enabled;

    public StatefulButtonComponentMessage(
        final boolean enabled
    ) {
        _enabled = enabled;
    }

    public boolean isEnabled () { return _enabled; }
}
