/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.waves;

public class WaveManager {

    public static Wave createWave(
        final Wave.WaveType waveType
    ) {
        switch (waveType) {
            case Ramp:      return new RampWave();
            case Sawtooth:  return new SawtoothWave();
            case Sine:      return new SineWave();
            case Square:    return new SquareWave();
            case Triangle:  return new TriangleWave();
        }

        throw new RuntimeException("Bad Juju");
    }
}
