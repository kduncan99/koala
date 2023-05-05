/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.waves;

import javafx.scene.canvas.Canvas;

public abstract class Wave {

    /**
     * Retrieves the value of the wave, from -1.0 to +1.0 at the given position,
     * optionally with respect to the given pulse-width (depending on the subclass).
     * @param position Position from the beginning of the wave (0.0) to the end (1.0)
     * @param pulseWidth Presumed width of the pulse, if this has any meaning, from none (0.0) to full (1.0)
     * @return value from -1.0 to +1.0
     */
    public abstract double getValue(
        final double position,      //  from 0.0 to 1.0
        final double pulseWidth     //  from 0.0 to 1.0
    );

    abstract WaveType getWaveType();

    /**
     * Convenience method to create the proper wave given the requested wave type
     */
    public static Wave createWave(
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

    /**
     * Draws a representation of the wave onto the given canvas
     */
    public final void drawWave(
        final Canvas canvas
    ) {
        var gc = canvas.getGraphicsContext2D();

        gc.beginPath();
        for (int x = 0; x < canvas.getWidth(); ++x) {
            double position = x / canvas.getWidth();
            var raw = getValue(position, 0.5);
            var y = (int)((1.0 - ((raw + 1.0) / 2.0)) * canvas.getHeight());

            if (x == 0)
                gc.moveTo(x, y);
            else
                gc.lineTo(x, y);
        }
        gc.stroke();
        gc.closePath();
    }
}
