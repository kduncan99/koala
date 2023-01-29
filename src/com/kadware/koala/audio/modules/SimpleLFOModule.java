/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.DoubleRange;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.TriggerInputPort;
import com.kadware.koala.waves.IWave;
import com.kadware.koala.waves.WaveManager;
import com.kadware.koala.waves.WaveType;

public class SimpleLFOModule extends Module {

    public static final int RESET_INPUT_PORT = 0;
    public static final int OUTPUT_PORT = 1;

    public static final double DEFAULT_FREQUENCY = 1.0f;
    public static final DoubleRange FREQUENCY_RANGE = new DoubleRange(0.001, 100.0);

    public static final double DEFAULT_PULSE_WIDTH = 0.5f;
    public static final DoubleRange PULSE_WIDTH_RANGE = new DoubleRange(0.05, 0.95);

    public final TriggerInputPort _trigger;
    public final ContinuousOutputPort _output;

    private double _frequency;
    private double _pulseWidth;
    private IWave _wave;
    private double _waveProgress;
    private double _waveProgressIncrement;

    SimpleLFOModule() {
        _trigger = new TriggerInputPort();
        _output = new ContinuousOutputPort();

        _inputPorts.put(RESET_INPUT_PORT, _trigger);
        _outputPorts.put(OUTPUT_PORT, _output);
        _wave = WaveManager.createWave(WaveType.SINE);
        setFrequency(DEFAULT_FREQUENCY);
        setPulseWidth(DEFAULT_PULSE_WIDTH);
        reset();
    }

    @Override
    public void advance() {
        if (_trigger.getValue()) {
            reset();
        }

        _waveProgress += _waveProgressIncrement;
        if (_waveProgress >= 1.0) {
            _waveProgress = _waveProgress % 1;
        }

        //  wave objects return values in the range of MIN_CVPORT_VALUE to MAX_CVPORT_VALUE.
        //  We take MIN to be a negative value, with MAX as positive, although we don't assume magnitudes of 5.
        //  We need to get the raw value, then adjust it if necessary given the bias selection.
        double value = _wave.getValue(_waveProgress, _pulseWidth);
        _output.setCurrentValue(value);
    }

    @Override
    public void close() {}

    public double getFrequency() {
        return _frequency;
    }

    public double getBasePulseWidth() {
        return _pulseWidth;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.SimpleLFO;
    }

    public IWave getWave() {
        return _wave;
    }

    @Override
    public void reset() {
        _waveProgress = 0.0;
    }

    public void setFrequency(
        final double value
    ) {
        _frequency = FREQUENCY_RANGE.clipValue(value);
        _waveProgressIncrement = _frequency / Koala.SAMPLE_RATE;
    }

    public void setPulseWidth(
        final double value
    ) {
        _pulseWidth = PULSE_WIDTH_RANGE.clipValue(value);
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
