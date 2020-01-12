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

public class VCOscillatorModule extends Module {

    public static final int FREQUENCY_INPUT_PORT = 0;
    public static final int FREQUENCY_MOD_INPUT_PORT_1 = 1;
    public static final int FREQUENCY_MOD_INPUT_PORT_2 = 2;
    public static final int PULSE_WIDTH_MOD_INPUT_PORT = 3;
    public static final int OUTPUT_PORT = 0;

    private float _baseFrequency;
    private float _basePulseWidth;
    private IWave _wave;
    private float _waveProgress;

    VCOscillatorModule() {
        _inputPorts.put(FREQUENCY_INPUT_PORT, new DiscreteInputPort("Frequency x 1000", "FC"));
        _inputPorts.put(FREQUENCY_MOD_INPUT_PORT_1, new ContinuousInputPort("Frequency Modulation 1", "FM"));
        _inputPorts.put(FREQUENCY_MOD_INPUT_PORT_2, new ContinuousInputPort("Frequency Modulatoin 2", "FM"));
        _inputPorts.put(PULSE_WIDTH_MOD_INPUT_PORT, new ContinuousInputPort("Pulse Width Modulation", "PWM"));
        //  TODO sync input and sync output logic ports
        _outputPorts.put(OUTPUT_PORT, new ContinuousOutputPort("Signal Output", "OUT"));
        _wave = WaveManager.createWave(IWave.WaveType.Square);
        _baseFrequency = 440.0f;
        _basePulseWidth = 0.5f;
        reset();
    }

    @Override
    public void advance() {
        DiscreteInputPort fcIn = (DiscreteInputPort) _inputPorts.get(FREQUENCY_INPUT_PORT);
        float frequency = _baseFrequency + ((float)fcIn.getValue() / 1000);

        ContinuousInputPort fmIn1 = (ContinuousInputPort) _inputPorts.get(FREQUENCY_MOD_INPUT_PORT_1);
        ContinuousInputPort fmIn2 = (ContinuousInputPort) _inputPorts.get(FREQUENCY_MOD_INPUT_PORT_2);
        float modulation = fmIn1.getValue() + fmIn2.getValue();

        float effectiveFrequency = (float)(frequency * Math.pow(2, modulation));
        float segmentsPerCycle = effectiveFrequency / Koala.SAMPLE_RATE;
        _waveProgress += segmentsPerCycle;
        if (_waveProgress >= 1.0f) {
            _waveProgress -= 1.0f;
        }

        ContinuousInputPort pwIn = (ContinuousInputPort) _inputPorts.get(PULSE_WIDTH_MOD_INPUT_PORT);
        float effectivePulseWidth = _basePulseWidth + (pwIn.getValue() / 10.0f);
        float value = _wave.getValue(_waveProgress, effectivePulseWidth);

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
    public String getModuleAbbreviation() {
        return "VCO";
    }

    @Override
    public String getModuleClass() {
        return "Oscillator";
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
        final float value
    ) {
        _baseFrequency = value;
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
