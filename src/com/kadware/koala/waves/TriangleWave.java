/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

import com.kadware.koala.Koala;

public class TriangleWave implements IWave {

    TriangleWave() {}

    /**
     * Retrieves the value of the wave, from MIN_VALUE to MAX_VALUE,
     * at the given position presuming the given pulsewidth.
     * @param position Position from the beginning of the wave (0.0) to the end ( < 1.0)
     * @param pulseWidth Presumed width of the pulse, if this has any meaning, from none (0.0) to full (1.0)
     * @return value from MIN_VALUE to MAX_VALUE
     */
    @Override
    public float getValue(
        final float position,
        final float pulseWidth
    ) {
        float effectivePosition = position;

        if (position < 0.0f) {
            effectivePosition = 0.0f;
        } else if (position > 1.0f) {
            effectivePosition = 1.0f;
        }

        if (effectivePosition == 0.5f) {
            return 0.0f;
        } else if (effectivePosition < 0.5f) {
            return (effectivePosition * 2.0f * Koala.CVPORT_VALUE_RANGE) + Koala.MIN_CVPORT_VALUE;
        } else {
            return ((1.0f - effectivePosition) * 2.0f * Koala.CVPORT_VALUE_RANGE) + Koala.MIN_CVPORT_VALUE;
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
