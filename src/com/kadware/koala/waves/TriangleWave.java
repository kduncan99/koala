/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

import com.kadware.koala.Koala;

public class TriangleWave implements IWave {

    TriangleWave() {}

    /**
     * Retrieves the value of the wave, from MIN_VALUE to MAX_VALUE,
     * at the given position presuming the given pulse width.
     * @param position Position from the beginning of the wave (0.0) to the end ( < 1.0)
     * @param pulseWidth Presumed width of the pulse, if this has any meaning, from none (0.0) to full (1.0)
     * @return value from MIN_VALUE to MAX_VALUE
     */
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
