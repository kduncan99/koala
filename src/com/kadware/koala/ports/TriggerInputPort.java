/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.exceptions.CannotConnectPortException;

/**
 * Special implementation of LogicInputPort which manages trigger inputs
 */
public final class TriggerInputPort extends LogicInputPort {

    private boolean _state;

    public TriggerInputPort(
        final String name,
        final String abbreviation
    ) {
        super(name, abbreviation);
    }

    public void clearTrigger() {
        _state = false;
    }

    /**
     * Special debouncing code - ensure that we report a trigger only once,
     * regardless of how long the trigger input stays open.
     */
    @Override
    public boolean getValue() {
        if (!_state) {
            boolean inp = super.getValue();
            if (inp) {
                _state = true;
            }
        }

        return _state;
    }

    @Override
    public void reset() {
        super.reset();
        _state = false;
    }
}
