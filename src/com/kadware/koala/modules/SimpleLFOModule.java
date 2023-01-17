/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.waves.IWave;
import com.kadware.koala.waves.WaveManager;

public class SimpleLFOModule extends Module {

    public static final int OUTPUT_PORT = 0;

    private float _baseFrequency;
    private float _basePulseWidth;
    private boolean _isBiased;      //  true -> min value == MIN_CVPORT_VALUE; false -> min value == 0.0
    private IWave _wave;
    private float _waveProgress;

    SimpleLFOModule() {
        _outputPorts.put(OUTPUT_PORT, new ContinuousOutputPort());
        _wave = WaveManager.createWave(IWave.WaveType.Square);
        _baseFrequency = 440.0f;
        _basePulseWidth = 0.5f;
        _isBiased = false;
        reset();
    }

    @Override
    public void advance() {
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

        ContinuousOutputPort out = (ContinuousOutputPort) _outputPorts.get(OUTPUT_PORT);
        out.setCurrentValue(value);
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
}
