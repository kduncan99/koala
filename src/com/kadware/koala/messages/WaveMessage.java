/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.messages;

import com.kadware.koala.waves.IWave;

public class WaveMessage extends Message {

    private final IWave _newWave;

    public WaveMessage(
        final Object sender,
        final IWave newWave
    ) {
        super(sender);
        _newWave = newWave;
    }

    public IWave getNewWaveValue() { return _newWave; }
}
