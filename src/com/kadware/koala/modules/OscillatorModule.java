package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.InputPort;
import com.kadware.koala.ports.OutputPort;
import com.kadware.koala.waves.Wave;
import com.kadware.koala.waves.WaveManager;

public class OscillatorModule extends Module {

    public static enum Range {
        Full,
        Positive,
        Negative,
    }

    public static final int FREQUENCY_PORT_1 = 1;
    public static final int FREQUENCY_PORT_2 = 2;
    public static final int FREQUENCY_PORT_3 = 3;
    public static final int PULSE_WIDTH_PORT = 4;
    public static final int OUTPUT_PORT = 0;

    private double _baseFrequency;
    private double _basePulseWidth;
    private Range _range;
    private Wave _wave;
    private double _waveProgress;

    OscillatorModule() {
        _inputPorts.put(FREQUENCY_PORT_1, new InputPort("Frequency Control 1"));
        _inputPorts.put(FREQUENCY_PORT_2, new InputPort("Frequency Control 2"));
        _inputPorts.put(FREQUENCY_PORT_3, new InputPort("Frequency Control 3"));
        _inputPorts.put(PULSE_WIDTH_PORT, new InputPort("Pulse Width Control"));
        _outputPorts.put(OUTPUT_PORT, new OutputPort("Signal Output"));
        _wave = WaveManager.createWave(Wave.WaveType.Square);
        _baseFrequency = 440.0d;
        _basePulseWidth = 0.5;
        reset();
    }

    @Override
    public void advance() {
        double modulation = _inputPorts.get(FREQUENCY_PORT_1).getValue()
                          + _inputPorts.get(FREQUENCY_PORT_2).getValue()
                          + _inputPorts.get(FREQUENCY_PORT_3).getValue();
        double effectiveFrequency = _baseFrequency * Math.pow(2, modulation);
        double segmentsPerCycle = effectiveFrequency / Koala.SAMPLE_RATE;
        _waveProgress += segmentsPerCycle;
        if (_waveProgress >= 1.0) {
            _waveProgress -= 1.0;
        }

        double effectivePulseWidth = _basePulseWidth + (_inputPorts.get(PULSE_WIDTH_PORT).getValue() / 10.0);

        double value = _wave.getValue(_waveProgress, effectivePulseWidth);
        if (_range == Range.Negative) {
            value = (value - Koala.MAX_PORT_MAGNITUDE) / 2.0d;
        } else if (_range == Range.Positive) {
            value = (value + Koala.MAX_PORT_MAGNITUDE) / 2.0d;
        }
        _outputPorts.get(OUTPUT_PORT).setCurrentValue(value);
    }

    @Override
    public void close() {}

    public double getBaseFrequency() {
        return _baseFrequency;
    }

    @Override
    public String getModuleClass() {
        return "Oscillator";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.TestTone;
    }

    public Range getRange() {
        return _range;
    }

    public Wave getWave() {
        return _wave;
    }

    @Override
    public void reset() {
        _waveProgress = 0.0d;
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

    public void setRange(
        final Range value
    ) {
        _range = value;
    }

    public void setWave(
        final Wave wave
    ) {
        _wave = wave;
        reset();
    }

    public void setWave(
        final Wave.WaveType waveType
    ) {
        _wave = WaveManager.createWave(waveType);
        reset();
    }
}
