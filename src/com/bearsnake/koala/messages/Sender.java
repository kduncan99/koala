/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages;

import java.util.HashSet;
import java.util.Set;

public class Sender {

    private final Set<IListener> _listeners = new HashSet<>();

    public void notifyListeners(
        final int sourceIdentifier,
        final Message message
    ) {
        _listeners.forEach(l -> l.notify(sourceIdentifier, message));
    }

    public void registerListener(
        final IListener listener
    ) {
        _listeners.add(listener);
    }

    public void unregisterListener(
        final IListener listener
    ) {
        _listeners.remove(listener);
    }
}
