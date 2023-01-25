/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.DiscreteInputPort;
import com.kadware.koala.waves.IWave;
import com.kadware.koala.waves.WaveManager;
import com.kadware.koala.waves.WaveType;

public class VCOscillatorModule extends Module {

    public static final int FREQUENCY_INPUT_PORT = 0;
    public static final int FREQUENCY_MOD_INPUT_PORT_1 = 1;
    public static final int FREQUENCY_MOD_INPUT_PORT_2 = 2;
    public static final int PULSE_WIDTH_MOD_INPUT_PORT = 3;
    public static final int OUTPUT_PORT = 0;

    private double _baseFrequency;
    private double _basePulseWidth;
    private IWave _wave;
    private double _waveProgress;

    VCOscillatorModule() {
        _inputPorts.put(FREQUENCY_INPUT_PORT, new DiscreteInputPort());//Frequency x 1000
        _inputPorts.put(FREQUENCY_MOD_INPUT_PORT_1, new ContinuousInputPort());
        _inputPorts.put(FREQUENCY_MOD_INPUT_PORT_2, new ContinuousInputPort());
        _inputPorts.put(PULSE_WIDTH_MOD_INPUT_PORT, new ContinuousInputPort());
        //  TODO sync input and sync output logic ports
        _outputPorts.put(OUTPUT_PORT, new ContinuousOutputPort());
        _wave = WaveManager.createWave(WaveType.SQUARE);
        _baseFrequency = 440.0f;
        _basePulseWidth = 0.5f;
        reset();
    }

    @Override
    public void advance() {
        DiscreteInputPort fcIn = (DiscreteInputPort) _inputPorts.get(FREQUENCY_INPUT_PORT);
        double frequency = _baseFrequency + (fcIn.getValue() / 1000.0);

        ContinuousInputPort fmIn1 = (ContinuousInputPort) _inputPorts.get(FREQUENCY_MOD_INPUT_PORT_1);
        ContinuousInputPort fmIn2 = (ContinuousInputPort) _inputPorts.get(FREQUENCY_MOD_INPUT_PORT_2);
        double modulation = fmIn1.getValue() + fmIn2.getValue();

        double effectiveFrequency = frequency * Math.pow(2, modulation);
        double segmentsPerCycle = effectiveFrequency / Koala.SAMPLE_RATE;
        _waveProgress += segmentsPerCycle;
        if (_waveProgress >= 1.0f) {
            _waveProgress -= 1.0f;
        }

        ContinuousInputPort pwIn = (ContinuousInputPort) _inputPorts.get(PULSE_WIDTH_MOD_INPUT_PORT);
        double effectivePulseWidth = _basePulseWidth + (pwIn.getValue() / 10.0f);
        double value = _wave.getValue(_waveProgress, effectivePulseWidth);

        ContinuousOutputPort out = (ContinuousOutputPort) _outputPorts.get(OUTPUT_PORT);
        out.setCurrentValue(value);
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
        _baseFrequency = value;
    }

    public void setPulseWidth(
        final double value
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
        final WaveType waveType
    ) {
        _wave = WaveManager.createWave(waveType);
        reset();
    }
}
