/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.controls;

import com.bearsnake.koala.messages.ControlMessage;

/**
 * Used to communicate something about a wave from one entity to another
 */
public class TestToneSelectorControlMessage extends ControlMessage {

    private final double _frequency;

    public TestToneSelectorControlMessage(
        final double frequency
    ) {
        _frequency = frequency;
    }

    public double getFrequency() { return _frequency; }
}
