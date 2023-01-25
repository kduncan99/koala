/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

public class WaveManager {

    public static IWave createWave(
        final WaveType waveType
    ) {
        switch (waveType) {
            case RAMP:      return new RampWave();
            case SAWTOOTH:  return new SawtoothWave();
            case SINE:      return new SineWave();
            case SQUARE:    return new SquareWave();
            case TRIANGLE:  return new TriangleWave();
        }

        throw new RuntimeException("Bad Juju");
    }
}
