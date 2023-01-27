/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.messages;

import java.util.HashSet;
import java.util.Set;

public class Sender {

    private final Set<IListener> _listeners = new HashSet<>();

    public void notifyListeners(
        final Message message
    ) {
        _listeners.forEach(l -> l.notify(message));
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
