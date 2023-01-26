/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

import com.kadware.koala.Koala;

public class SawtoothWave implements IWave {

    /**
     * Retrieves the value of the wave, from -1.0 to +1.0 at the given position,
     * optionally with respect to the given pulse-width (depending on the subclass).
     * @param position Position from the beginning of the wave (0.0) to the end (1.0)
     * @param pulseWidth Presumed width of the pulse, if this has any meaning, from none (0.0) to full (1.0)
     * @return value from -1.0 to +1.0
     */
    @Override
    public double getValue(
        final double position,
        final double pulseWidth
    ) {
        return ((1.0 - Koala.POSITIVE_RANGE.clipValue(position)) * Koala.BIPOLAR_RANGE.getDelta())
            + Koala.BIPOLAR_RANGE.getLowValue();
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.SAWTOOTH;
    }
}
