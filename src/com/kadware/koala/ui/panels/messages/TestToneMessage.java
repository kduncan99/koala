/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.messages;

/**
 * Used to communicate something about a wave from one entity to another
 */
public class TestToneMessage extends PanelMessage {

    private final double _frequency;

    public TestToneMessage(
        final Object sender,
        final int identifier,
        final double frequency
    ) {
        super(sender, identifier);
        _frequency = frequency;
    }

    public double getFrequency() { return _frequency; }
}
