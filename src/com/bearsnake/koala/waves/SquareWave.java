/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.waves;

import com.bearsnake.koala.Koala;

public class SquareWave extends Wave {

    @Override
    public double getValue(
        final double position,
        final double pulseWidth
    ) {
        var pos = Koala.POSITIVE_RANGE.clipValue(position);
        var pw = Koala.POSITIVE_RANGE.clipValue(pulseWidth);
        return (pos < pw) ? Koala.BIPOLAR_RANGE.getHighValue() : Koala.BIPOLAR_RANGE.getLowValue();
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.SQUARE;
    }
}
