/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.messages;

import com.kadware.koala.messages.Message;
import com.kadware.koala.waves.IWave;

/**
 * Used to communicate something about a wave from one entity to another
 */
public class TestToneMessage extends Message {

    private final double _frequency;

    public TestToneMessage(
        final Object sender,
        final double frequency
    ) {
        super(sender);
        _frequency = frequency;
    }

    public double getFrequency() { return _frequency; }
}
