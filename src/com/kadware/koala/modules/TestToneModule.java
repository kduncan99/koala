/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.OutputPort;

public class TestToneModule extends Module {

    public static final int OUTPUT_PORT = 0;

    private double _baseFrequency;      //  usable range from 0.{mumble} up to around 20kHz
    private int _cycleCounter;
    private int _transitionLimit;
    private int _resetLimit;

    TestToneModule() {
        _outputPorts.put(OUTPUT_PORT, new OutputPort("Signal"));
        setBaseFrequency(440);
    }

    @Override
    public void advance(
    ) {
        double value = _cycleCounter >= _transitionLimit ? Koala.MIN_PORT_VALUE : Koala.MAX_PORT_VALUE;
        _outputPorts.get(OUTPUT_PORT).setCurrentValue(value);
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
    public String getModuleClass() {
        return "Test Tone";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.TestTone;
    }

    @Override
    public void reset() {
        _cycleCounter = 0;
    }

    public void setBaseFrequency(
        final double value
    ) {
        _baseFrequency = value;
        int samplesPerCycle = (int)(Koala.SAMPLE_RATE / _baseFrequency);
        _resetLimit = samplesPerCycle - 1;
        _transitionLimit = samplesPerCycle >> 1;
        reset();
    }
}
