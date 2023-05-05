/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.signals;

import com.bearsnake.koala.AnalogRangeType;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.waves.Wave;
import com.bearsnake.koala.waves.WaveType;

public class Oscillator {

    private Wave _wave;
    private double _frequency;      //  0.0 through 44000.0 (or even higher, I suppose)
    private double _pulseWidth;     //  0.0 through 1.0
    private double _position;       //  0.0 through 1.0
    private AnalogRangeType _rangeType = AnalogRangeType.BIPOLAR;

    public Oscillator() {
        _wave = Wave.createWave(WaveType.SINE);
        _frequency = 440.0;
        _pulseWidth = 0.5;
        _position = 0.0;
    }

    public void advance() {
        double segmentsPerCycle = _frequency / Koala.SAMPLE_RATE;
        _position += segmentsPerCycle;
        if (_position > 1.0) {
            _position -= 1.0;
        }
    }

    public double getFrequency() { return _frequency; }
    public AnalogRangeType getRangeType() { return _rangeType; }

    public double getValue() {
        double raw = _wave.getValue(_position, _pulseWidth);
        if (_rangeType == AnalogRangeType.POSITIVE) {
            raw = (raw + 1.0) / 2.0;
        }
        return raw;
    }

    public void reset() {
        _position = 0.0;
    }

    public void setFrequency(
        final double value
    ) {
        _frequency = Koala.getBounded(0.0, value, 48000.0);
    }

    public void setPulseWidth(
        final double value
    ) {
        _pulseWidth = Koala.getBounded(0.0, value, 1.0);
    }

    public void setRangeType(
        final AnalogRangeType range
    ) {
        _rangeType = range;
    }

    public void setWaveForm(
        final WaveType waveType
    ) {
        _wave = Wave.createWave(waveType);
        _position = 0.0;
    }
}
