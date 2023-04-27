/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages;

public interface IListener {

    void notify(final int senderIdentifier, final Message message);
}
