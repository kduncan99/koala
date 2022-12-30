/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;

public class TestToneModule extends Module {

    public static final int SIGNAL_OUTPUT_PORT = 0;

    private float _baseFrequency;       //  usable range from 0.{mumble} up to around 20kHz
    private int _cycleCounter;
    private int _transitionLimit;
    private int _resetLimit;

    TestToneModule() {
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort("Signal Output", "OUT"));
        setBaseFrequency(440);
    }

    @Override
    public void advance() {
        float value = _cycleCounter >= _transitionLimit ? Koala.MIN_CVPORT_VALUE : Koala.MAX_CVPORT_VALUE;
        ContinuousOutputPort port = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        port.setCurrentValue(value);
        _cycleCounter++;
        if (_cycleCounter >= _resetLimit) {
            _cycleCounter = 0;
        }
    }

    @Override
    public void close() {}

    public float getBaseFrequency() {
        return _baseFrequency;
    }

    @Override
    public String getModuleAbbreviation() {
        return "TST";
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
        final float value
    ) {
        _baseFrequency = value;
        int samplesPerCycle = (int)(Koala.SAMPLE_RATE / _baseFrequency);
        _resetLimit = samplesPerCycle - 1;
        _transitionLimit = samplesPerCycle >> 1;
        reset();
    }
}
