/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.messages;

public abstract class Message {

    private Object _sender;
    private int _identifier;

    public Message(
        final Object sender,
        final int identifier
    ) {
        _sender = sender;
        _identifier = identifier;
    }

    public int getIdentifier() { return _identifier; }
    public Object getSender() { return _sender; }

    public Message setSender(
        final Object object
    ) {
        _sender = object;
        return this;
    }
}
