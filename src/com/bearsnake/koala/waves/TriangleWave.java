/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.waves;

import com.bearsnake.koala.Koala;

public class TriangleWave implements IWave {

    @Override
    public double getValue(
        final double position,
        final double pulseWidth
    ) {
        var pos = Koala.POSITIVE_RANGE.clipValue(position);
        if (pos == 0.5) {
            return 0.0;
        } else if (pos < 0.5) {
            return (pos * 2.0 * Koala.BIPOLAR_RANGE.getDelta()) + Koala.BIPOLAR_RANGE.getLowValue();
        } else {
            return ((1.0 - pos) * 2.0 * Koala.BIPOLAR_RANGE.getDelta()) + Koala.BIPOLAR_RANGE.getLowValue();
        }
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.TRIANGLE;
    }
}
