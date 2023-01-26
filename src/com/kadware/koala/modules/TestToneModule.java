/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;

public class TestToneModule extends Module {

    public static final int SIGNAL_OUTPUT_PORT = 0;
    public static final double DEFAULT_FREQUENCY = 440.0;

    private double _baseFrequency;       //  usable range from 0.{mumble} up to around 20kHz
    private int _cycleCounter;
    private int _transitionLimit;
    private int _resetLimit;

    TestToneModule() {
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort());
        setBaseFrequency(DEFAULT_FREQUENCY);
    }

    @Override
    public void advance() {
        double value = _cycleCounter >= _transitionLimit ? -0.7071 : 0.7071;
        ContinuousOutputPort port = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        port.setCurrentValue(value);
        _cycleCounter++;
        if (_cycleCounter >= _resetLimit) {
            _cycleCounter = 0;
        }
    }

    @Override
    public void close() {}

    public double getBaseFrequency() {
        return _baseFrequency;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.TestTone;
    }

    @Override
    public void reset() {
        _cycleCounter = 0;
    }

    /**
     * Input value is NOT a CV... it is an actual frequency, and should be > 0.0
     */
    public void setBaseFrequency(
        final double value
    ) {
        _baseFrequency = (value > 0) ? value : DEFAULT_FREQUENCY;
        int samplesPerCycle = (int)(Koala.SAMPLE_RATE / _baseFrequency);
        _resetLimit = samplesPerCycle - 1;
        _transitionLimit = samplesPerCycle >> 1;
        reset();
    }
}
