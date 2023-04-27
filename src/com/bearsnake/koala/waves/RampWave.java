/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.waves;

import com.bearsnake.koala.Koala;

public class RampWave implements IWave {

    @Override
    public double getValue(
        final double position,
        final double pulseWidth
    ) {
        return (position * Koala.BIPOLAR_RANGE.getDelta()) + Koala.BIPOLAR_RANGE.getLowValue();
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.RAMP;
    }
}
