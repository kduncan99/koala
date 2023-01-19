/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.TriggerInputPort;
import com.kadware.koala.waves.IWave;
import com.kadware.koala.waves.WaveManager;

public class SimpleLFOModule extends Module {

    public static final int RESET_INPUT_PORT = 0;
    public static final int OUTPUT_PORT = 1;

    public static final float DEFAULT_FREQUENCY = 1.0f;
    public static final float LOW_FREQUENCY_LIMIT = 0.001f;
    public static final float HIGH_FREQUENCY_LIMIT = 100.0f;

    public static final float DEFAULT_PULSE_WIDTH = 0.5f;
    public static final float LOW_PULSE_WIDTH_LIMIT = 0.05f;
    public static final float HIGH_PULSE_WIDTH_LIMIT = 0.95f;

    public final TriggerInputPort _trigger;
    public final ContinuousOutputPort _output;

    private float _baseFrequency;
    private float _basePulseWidth;
    private boolean _isBiased;      //  true -> min value == MIN_CVPORT_VALUE; false -> min value == 0.0
    private boolean _isInverted;
    private IWave _wave;
    private float _waveProgress;

    SimpleLFOModule() {
        _trigger = new TriggerInputPort();
        _output = new ContinuousOutputPort();

        _inputPorts.put(RESET_INPUT_PORT, _trigger);
        _outputPorts.put(OUTPUT_PORT, _output);
        _wave = WaveManager.createWave(IWave.WaveType.Square);
        _baseFrequency = DEFAULT_FREQUENCY;
        _basePulseWidth = 0.5f;
        _isBiased = false;
        reset();
    }

    @Override
    public void advance() {
        if (_trigger.getValue())
            reset();

        float segmentsPerCycle = _baseFrequency / Koala.SAMPLE_RATE;
        _waveProgress += segmentsPerCycle;
        if (_waveProgress >= 1.0f) {
            _waveProgress -= 1.0f;
        }

        //  wave objects return values in the range of MIN_CVPORT_VALUE to MAX_CVPORT_VALUE.
        //  We take MIN to be a negative value, with MAX as positive, although we don't assume magnitudes of 5.
        //  We need to get the raw value, then adjust it if necessary given the bias selection.
        float value = _wave.getValue(_waveProgress, _basePulseWidth);
        if (_isBiased) {
            value = (value - Koala.MIN_CVPORT_VALUE) / Koala.CVPORT_VALUE_RANGE * value;
        }

        _output.setCurrentValue(value);
    }

    @Override
    public void close() {}

    public float getBaseFrequency() {
        return _baseFrequency;
    }

    public float getBasePulseWidth() {
        return _basePulseWidth;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.SimpleLFO;
    }

    public IWave getWave() {
        return _wave;
    }

    public boolean isBiased() {
        return _isBiased;
    }

    @Override
    public void reset() {
        _waveProgress = 0.0f;
    }

    public void setBaseFrequency(
        final float value
    ) {
        _baseFrequency = value;
    }

    public void setIsBiased(
        final boolean value
    ) {
        _isBiased = value;
    }

    public void setIsInverted(
        final boolean value
    ) {
        _isInverted = value;
    }

    public void setPulseWidth(
        final float value
    ) {
        _basePulseWidth = value;
    }

    public void setWave(
        final IWave wave
    ) {
        _wave = wave;
        reset();
    }

    public void setWave(
        final IWave.WaveType waveType
    ) {
        _wave = WaveManager.createWave(waveType);
        reset();
    }

    public boolean toggleIsBiased() {
        _isBiased = !_isBiased;
        return _isBiased;
    }

    public boolean toggleIsInverted() {
        _isInverted = !_isInverted;
        return _isInverted;
    }
}
