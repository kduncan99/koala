/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.components;

import com.bearsnake.koala.messages.Message;

/**
 * A message sent by a Component, either directly or through a Control.
 */
public abstract class ComponentMessage extends Message {

    public ComponentMessage() {}
}
