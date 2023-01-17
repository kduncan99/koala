/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

public interface IWave {

    public static enum WaveType {
        Ramp,
        Sawtooth,
        Sine,
        Square,
        Triangle,
    }

    /**
     * Retrieves the value of the wave, from Koala.MIN_CVPORT_VALUE to Koala.MAX_CVPORT_VALUE
     * at the given position, optionally with respect to the given pulse-width.
     * @param position Position from the beginning of the wave (0.0) to the end (1.0)
     * @param pulseWidth Presumed width of the pulse, if this has any meaning, from none (0.0) to full (1.0)
     * @return value from MIN_VALUE to MAX_VALUE
     */
    public float getValue(
        final float position,      //  from 0.0 to 1.0
        final float pulseWidth     //  from 0.0 to 1.0
    );

    public String getWaveClass();
    public WaveType getWaveType();
}
