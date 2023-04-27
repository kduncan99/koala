/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.waves;

import com.bearsnake.koala.Koala;

public class SawtoothWave implements IWave {

    @Override
    public double getValue(
        final double position,
        final double pulseWidth
    ) {
        return ((1.0 - position) * Koala.BIPOLAR_RANGE.getDelta()) + Koala.BIPOLAR_RANGE.getLowValue();
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.SAWTOOTH;
    }
}
