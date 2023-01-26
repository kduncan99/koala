/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components;

import com.kadware.koala.messages.IListener;
import com.kadware.koala.messages.Message;
import com.kadware.koala.messages.Sender;
import javafx.scene.layout.Pane;

public class Component extends Pane {

    public static final Sender _sender = new Sender();

    public void notifyListeners(
        final Message message
    ) {
        message.setSender(this);
        _sender.notifyListeners(message);
    }

    public void registerListener(
        final IListener listener
    ) {
        _sender.registerListener(listener);
    }

    public void unregisterListener(
        final IListener listener
    ) {
        _sender.unregisterListener(listener);
    }
}
