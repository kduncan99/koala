/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

import com.kadware.koala.Koala;

public class SquareWave implements IWave {

    SquareWave() {}

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
        return (position >= pulseWidth) ? Koala.MAX_CVPORT_VALUE : Koala.MIN_CVPORT_VALUE;
    }

    @Override
    public String getWaveClass() {
        return "Square";
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.SQUARE;
    }
}
