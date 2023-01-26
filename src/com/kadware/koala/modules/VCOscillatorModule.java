/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.waves.IWave;
import com.kadware.koala.waves.WaveManager;
import com.kadware.koala.waves.WaveType;

public class VCOscillatorModule extends Module {

    public static final int FREQUENCY_INPUT_PORT = 0;
    public static final int PULSE_WIDTH_INPUT_PORT = 1;
    public static final int OUTPUT_PORT = 2;
    //  TODO sync input and sync output logic ports

    private static final WaveType DEFAULT_WAVE_TYPE = WaveType.SQUARE;
    private static final double DEFAULT_FREQUENCY = 440.0;
    private static final double DEFAULT_PULSE_WIDTH = 0.5;
    private static final DoubleRange FREQUENCY_RANGE = new DoubleRange(0.0, 20000.0);
    private static final DoubleRange PULSE_WIDTH_RANGE = new DoubleRange(0.0, 1.0);

    private final ContinuousInputPort _frequencyInput;
    private final ContinuousInputPort _pulseWidthInput;
    private final ContinuousOutputPort _output;

    private double _baseFrequency;  //  0.0 < n < 20000
    private double _basePulseWidth; //  0.0 < n < 1.0
    private IWave _wave;
    private double _waveProgress;   //  0.0 <= n < 1.0

    VCOscillatorModule() {
        _frequencyInput = new ContinuousInputPort();
        _pulseWidthInput = new ContinuousInputPort();
        _output = new ContinuousOutputPort();

        _inputPorts.put(FREQUENCY_INPUT_PORT, _frequencyInput);
        _inputPorts.put(PULSE_WIDTH_INPUT_PORT, _pulseWidthInput);
        _outputPorts.put(OUTPUT_PORT, _output);

        _wave = WaveManager.createWave(DEFAULT_WAVE_TYPE);
        _baseFrequency = DEFAULT_FREQUENCY;
        _basePulseWidth = DEFAULT_PULSE_WIDTH;
        reset();
    }

    @Override
    public void advance() {
        var frequencyMod = _frequencyInput.getValue();
        var pulseWidthMod = _pulseWidthInput.getValue();

        double frequency = FREQUENCY_RANGE.clipValue(_baseFrequency * Math.pow(2.0, frequencyMod * 5.0));
        double pulseWidth = PULSE_WIDTH_RANGE.clipValue(_basePulseWidth + pulseWidthMod);

        double segmentsPerCycle = frequency / Koala.SAMPLE_RATE;
        _waveProgress += segmentsPerCycle;
        if (_waveProgress >= 1.0f) {
            _waveProgress -= 1.0f;
        }
        _output.setCurrentValue(_wave.getValue(_waveProgress, pulseWidth));
    }

    @Override
    public void close() {}

    public double getBaseFrequency() {
        return _baseFrequency;
    }

    public double getBasePulseWidth() {
        return _basePulseWidth;
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

    public void setPulseWidth(
        final double value
    ) {
        _basePulseWidth = PULSE_WIDTH_RANGE.clipValue(value);
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
