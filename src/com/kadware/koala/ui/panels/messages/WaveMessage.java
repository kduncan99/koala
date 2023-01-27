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
public class WaveMessage extends Message {

    private final IWave _wave;

    public WaveMessage(
        final Object sender,
        final IWave newWave
    ) {
        super(sender);
        _wave = newWave;
    }

    public IWave getNewWaveValue() { return _wave; }
}
