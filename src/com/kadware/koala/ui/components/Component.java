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

    private final Sender _sender = new Sender();
    private final int _identifier;

    protected Component(
        final int identifier
    ) {
        _identifier = identifier;
    }

    public int getIdentifier() { return _identifier; }

    protected void notifyListeners(
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
