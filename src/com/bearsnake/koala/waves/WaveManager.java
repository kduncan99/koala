/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.waves;

public class WaveManager {

    public static IWave createWave(
        final WaveType waveType
    ) {
        return switch (waveType) {
            case RAMP -> new RampWave();
            case SAWTOOTH -> new SawtoothWave();
            case SINE -> new SineWave();
            case SQUARE -> new SquareWave();
            case TRIANGLE -> new TriangleWave();
        };
    }
}
