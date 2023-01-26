/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.messages;

public abstract class Message {

    private Object _sender;

    public Message(
        final Object sender
    ) {
        _sender = sender;
    }

    public Object getSender() { return _sender; }

    public Message setSender(
        final Object object
    ) {
        _sender = object;
        return this;
    }
}
