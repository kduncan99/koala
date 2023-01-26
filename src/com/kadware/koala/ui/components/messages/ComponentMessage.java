/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.messages;

import com.kadware.koala.messages.Message;

/**
 * A message sent by a component
 */
public abstract class ComponentMessage extends Message {

    private final int _identifier;

    public ComponentMessage(
        final Object sender,
        final int identifier
    ) {
        super(sender);
        _identifier = identifier;
    }

    public Object getIdentifier() { return _identifier; }
}
