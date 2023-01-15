package com.kadware.koala.messages;

import java.util.LinkedList;
import java.util.List;

public abstract class Sender {

    private final List<Listener> _listeners = new LinkedList<>();

    public void sendMessage(
        final Message message
    ) {
        _listeners.forEach(l -> l.notify(this, message));
    }

    public void register(
        final Listener listener
    ) {
        _listeners.add(listener);
    }

    public void unregister(
        final Listener listener
    ) {
        _listeners.remove(listener);
    }
}
