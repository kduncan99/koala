/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.waves;

public class SineWave extends Wave {

    @Override
    public double getValue(
        final double position,
        final double pulseWidth
    ) {
        return Math.sin(position * (2 * Math.PI));
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.SINE;
    }
}
