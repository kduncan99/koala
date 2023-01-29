/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.modules;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.waves.IWave;
import com.kadware.koala.waves.WaveManager;
import com.kadware.koala.waves.WaveType;

public class SimpleOscillatorModule extends Module {

    public static final int OUTPUT_PORT = 0;

    private static final WaveType DEFAULT_WAVE_TYPE = WaveType.SQUARE;
    private static final double DEFAULT_FREQUENCY = 440.0;
    private static final double DEFAULT_PULSE_WIDTH = 0.5;
    private static final DoubleRange FREQUENCY_RANGE = new DoubleRange(0.0, 20000.0);
    private static final DoubleRange PULSE_WIDTH_RANGE = new DoubleRange(0.0, 1.0);

    private final ContinuousOutputPort _output;

    private double _baseFrequency;  //  0.0 < n < 20000
    private IWave _wave;
    private double _waveProgress;   //  0.0 <= n < 1.0

    SimpleOscillatorModule() {
        _output = new ContinuousOutputPort();
        _outputPorts.put(OUTPUT_PORT, _output);
        _wave = WaveManager.createWave(DEFAULT_WAVE_TYPE);
        _baseFrequency = DEFAULT_FREQUENCY;
        reset();
    }

    @Override
    public void advance() {
        double segmentsPerCycle = _baseFrequency / Koala.SAMPLE_RATE;
        _waveProgress += segmentsPerCycle;
        if (_waveProgress >= 1.0f) {
            _waveProgress -= 1.0f;
        }
        _output.setCurrentValue(_wave.getValue(_waveProgress, DEFAULT_PULSE_WIDTH));
    }

    @Override
    public void close() {}

    public double getBaseFrequency() {
        return _baseFrequency;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.VCOscillator;
    }

    public IWave getWave() {
        return _wave;
    }

    @Override
    public void reset() {
        _waveProgress = 0.0f;
    }

    public void setBaseFrequency(
        final double value
    ) {
        _baseFrequency = FREQUENCY_RANGE.clipValue(value);
    }

    public void setWave(
        final IWave wave
    ) {
        _wave = wave;
        reset();
    }

    public void setWave(
        final WaveType waveType
    ) {
        _wave = WaveManager.createWave(waveType);
        reset();
    }
}
