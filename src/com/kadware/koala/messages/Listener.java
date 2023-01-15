package com.kadware.koala.messages;

public interface Listener {

    void notify(
        final Sender sender,
        final Message message
    );
}
