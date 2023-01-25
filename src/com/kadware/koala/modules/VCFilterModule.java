/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.DiscreteInputPort;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

public class VCFilterModule extends Module {

    public enum Type {
        Two_Pole,
        Four_Pole,
    }

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int FREQUENCY_INPUT_PORT = 1;
    public static final int FREQUENCY_MOD_INPUT_PORT_1 = 2;
    public static final int FREQUENCY_MOD_INPUT_PORT_2 = 3;
    public static final int RESONANCE_MOD_INPUT_PORT = 4;
    public static final int LOWPASS_SIGNAL_OUTPUT_PORT = 5;
    public static final int BANDPASS_SIGNAL_OUTPUT_PORT = 6;
    public static final int HIGHPASS_SIGNAL_OUTPUT_PORT = 7;

    private final ContinuousInputPort _signalInputPort;
    private final DiscreteInputPort _frequencyInputPort;
    private final ContinuousInputPort _frequencyModInputPort1;
    private final ContinuousInputPort _frequencyModInputPort2;
    private final ContinuousInputPort _resonanceModInputPort;
    private final ContinuousOutputPort _lowpassOutputPort;
    private final ContinuousOutputPort _bandpassOutputPort;
    private final ContinuousOutputPort _highpassOutputPort;

    private double _baseFrequency = 0.0;        //  limit value from 0.0 to 20000.0
    private double _baseResonance = 0.0;        //  limit value from 0.0 to 0.1
    private double[] _stateValues = { 0.0, 0.0, 0.0, 0.0 };
    private Type _type = Type.Two_Pole;

    public VCFilterModule() {
        _signalInputPort = new ContinuousInputPort();
        _frequencyInputPort = new DiscreteInputPort();
        _frequencyModInputPort1 = new ContinuousInputPort();
        _frequencyModInputPort2 = new ContinuousInputPort();
        _resonanceModInputPort = new ContinuousInputPort();
        _lowpassOutputPort = new ContinuousOutputPort();
        _bandpassOutputPort = new ContinuousOutputPort();
        _highpassOutputPort = new ContinuousOutputPort();

        _inputPorts.put(SIGNAL_INPUT_PORT, _signalInputPort);
        _inputPorts.put(FREQUENCY_INPUT_PORT, _frequencyInputPort);
        _inputPorts.put(FREQUENCY_MOD_INPUT_PORT_1, _frequencyModInputPort1);
        _inputPorts.put(FREQUENCY_MOD_INPUT_PORT_2, _frequencyModInputPort2);
        _inputPorts.put(RESONANCE_MOD_INPUT_PORT, _resonanceModInputPort);
        _outputPorts.put(LOWPASS_SIGNAL_OUTPUT_PORT, _lowpassOutputPort);
        _outputPorts.put(BANDPASS_SIGNAL_OUTPUT_PORT, _bandpassOutputPort);
        _outputPorts.put(HIGHPASS_SIGNAL_OUTPUT_PORT, _highpassOutputPort);
    }

    @Override
    public void advance() {
        //  TODO also implement https://www.musicdsp.org/en/latest/Filters/259-simple-biquad-filter-from-apple-com.html
        //  TODO also implement https://www.musicdsp.org/en/latest/Filters/26-moog-vcf-variation-2.html

        //  An IIR implementation of a 2-pole or 4-pole filter
        //  See https://www.musicdsp.org/en/latest/Filters/29-resonant-filter.html
        //  Calculate frequency using the base frequency as a discrete value,
        //      modified by the sum of the freq mod inputs, such that an increase of 1.0 corresponds to
        //      double the frequency, while a decrease of 1.0 corresponds to halving.
        //  Calculate resonance as the sum of the base value with the mod value.
        //      Don't let it be < 0.0, nor >= 1.0.
        double inputSignal = _signalInputPort.getValue();
        double frequencyMod = _frequencyModInputPort1.getValue() + _frequencyModInputPort2.getValue();
        double frequency = (float)(_baseFrequency * Math.pow(2.0, frequencyMod));
        double resonance = _baseResonance + _resonanceModInputPort.getValue();
        if (resonance < 0.0f) {
            resonance = 0.0f;
        } else if (resonance >= 1.0f) {
            resonance = 0.999f;
        }

        double cutoff = 2.0f * Math.sin(Math.PI * frequency / Koala.SAMPLE_RATE);
        double feedback = resonance + (resonance / (1.0f - cutoff));

        if (_type == Type.Two_Pole) {
            double hp = inputSignal - _stateValues[0];
            double bp = _stateValues[0] - _stateValues[1];
            _stateValues[0] += cutoff * (hp + (feedback * bp));
            _stateValues[1] += cutoff * (_stateValues[0] - _stateValues[1]);

            _lowpassOutputPort.setCurrentValue(_stateValues[1]);
            _bandpassOutputPort.setCurrentValue((float) bp);
            _highpassOutputPort.setCurrentValue((float) hp);
        } else {
            double hp = inputSignal - _stateValues[3];
            double bp = _stateValues[0] - _stateValues[3];
            _stateValues[0] += cutoff * (inputSignal - _stateValues[0]);
            _stateValues[1] += cutoff * (_stateValues[0] - _stateValues[1]);
            _stateValues[2] += cutoff * (_stateValues[1] - _stateValues[2]);
            _stateValues[3] += cutoff * (_stateValues[2] - _stateValues[3]);

            _lowpassOutputPort.setCurrentValue(_stateValues[3]);
            _bandpassOutputPort.setCurrentValue((float) bp);
            _highpassOutputPort.setCurrentValue((float) hp);
        }
    }

    @Override
    public void close() {}

    public double getBaseFrequency() {
        return _baseFrequency;
    }

    public double getBaseResonance() {
        return _baseResonance;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.VCFilter;
    }

    @Override
    public void reset() {}

    public void setBaseFrequency(
        final double frequency
    ) {
        _baseFrequency = frequency;
    }

    public void setBaseResonance(
        final double resonance
    ) {
        _baseResonance = resonance;
    }
}
