/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

import com.kadware.koala.Koala;

public class TriangleWave implements Wave {

    TriangleWave() {}

    /**
     * Retrieves the value of the wave, from MIN_VALUE to MAX_VALUE,
     * at the given position presuming the given pulsewidth.
     * @param position Position from the beginning of the wave (0.0) to the end ( < 1.0)
     * @param pulseWidth Presumed width of the pulse, if this has any meaning, from none (0.0) to full (1.0)
     * @return value from MIN_VALUE to MAX_VALUE
     */
    @Override
    public double getValue(
        final double position,
        final double pulseWidth
    ) {
        double effectivePosition = position;
        double effectivePulseWidth = pulseWidth;

        if (position < 0.0) {
            effectivePosition = 0.0;
        } else if (position > 1.0) {
            effectivePosition = 1.0;
        }

        if (effectivePosition == 0.5) {
            return 0.0;
        } else if (effectivePosition < 0.5) {
            return (effectivePosition * 4 * Koala.MAX_PORT_MAGNITUDE) + Koala.MIN_PORT_VALUE;
        } else {
            return ((1.0 - effectivePosition) * 4 * Koala.MAX_PORT_MAGNITUDE) + Koala.MIN_PORT_VALUE;
        }
    }

    @Override
    public String getWaveClass() {
        return "Triangle";
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.Triangle;
    }
}
