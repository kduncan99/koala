/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Message {

    //  we apply a unique identifier to each message mainly for future debugging purposes.
    private static final AtomicInteger _nextIdentifier = new AtomicInteger(0);

    private final int _identifier = _nextIdentifier.getAndIncrement();

    public final int getIdentifier() { return _identifier; }
}
