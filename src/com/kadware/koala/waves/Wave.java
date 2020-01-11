/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

public interface Wave {

    public static enum WaveType {
        Ramp,
        Sawtooth,
        Sine,
        Square,
        Triangle,
    }

    /**
     * Retrieves the value of the wave, from MIN_VALUE to MAX_VALUE,
     * at the given position presuming the given pulsewidth.
     * @param position Position from the beginning of the wave (0.0) to the end (1.0)
     * @param pulseWidth Presumed width of the pulse, if this has any meaning, from none (0.0) to full (1.0)
     * @return value from MIN_VALUE to MAX_VALUE
     */
    public double getValue(
        final double position,      //  from 0.0 to 1.0
        final double pulseWidth     //  from 0.0 to 1.0
    );

    public String getWaveClass();
    public WaveType getWaveType();
}
