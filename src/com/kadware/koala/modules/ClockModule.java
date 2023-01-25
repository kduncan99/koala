/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.LogicOutputPort;

public class ClockModule extends Module {

    public static final int SIGNAL_OUTPUT_PORT = 0;

    private double _baseFrequency;       //  usable range from 0.{mumble} up to around 20kHz
    private int _cycleCounter;
    private int _transitionLimit;
    private int _resetLimit;

    ClockModule() {
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new LogicOutputPort());
        setBaseFrequency(440);
    }

    @Override
    public void advance() {
        boolean value = _cycleCounter >= _transitionLimit;
        LogicOutputPort port = (LogicOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
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
        return ModuleType.Clock;
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
