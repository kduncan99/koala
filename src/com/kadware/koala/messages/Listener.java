/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.messages;

public interface Listener {

    void notify(
        final Sender sender,
        final Message message
    );
}
