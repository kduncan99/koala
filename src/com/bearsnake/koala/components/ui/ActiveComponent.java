/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui;

import com.bearsnake.koala.messages.IListener;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.messages.Sender;

/**
 * An ActiveComponent is a component which changes state, monitors and reports input, or does anything at all
 * other than just sitting in a static state unless manually updated by some other code.
 */
public abstract class ActiveComponent extends Component {

    private final Sender _sender = new Sender();

    protected void notifyListeners(
        final Message message
    ) {
        _sender.notifyListeners(getIdentifier(), message);
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
