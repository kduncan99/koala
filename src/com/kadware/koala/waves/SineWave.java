package com.kadware.koala.waves;

import com.kadware.koala.exceptions.BadSignalValueException;

public class SineWave implements Wave {

    SineWave() {}

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
        if (position < 0.0 || position >= 1.0) {
            throw new BadSignalValueException(position);
        } else if (pulseWidth < 0.0 || pulseWidth > 1.0) {
            throw new BadSignalValueException(pulseWidth);
        }

        return Math.sin(position * (1.0d - Math.PI));
    }

    @Override
    public String getWaveClass() {
        return "Sine";
    }

    @Override
    public WaveType getWaveType() {
        return WaveType.Sine;
    }
}
