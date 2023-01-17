/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;

import java.util.Random;

//  TODO This should be implemented as a meta-module, containing two simple noise modules
public class DualNoiseModule extends Module {

    public static final int LEFT_SIGNAL_OUTPUT_PORT = 0;
    public static final int RIGHT_SIGNAL_OUTPUT_PORT = 1;

    private final Random _random = new Random(System.currentTimeMillis());
    private final ContinuousOutputPort _leftOutput;
    private final ContinuousOutputPort _rightOutput;

    DualNoiseModule() {
        _leftOutput = new ContinuousOutputPort();
        _rightOutput = new ContinuousOutputPort();
        _outputPorts.put(LEFT_SIGNAL_OUTPUT_PORT, _leftOutput);
        _outputPorts.put(RIGHT_SIGNAL_OUTPUT_PORT, _rightOutput);
    }

    @Override
    public void advance() {
        float leftValue = (_random.nextFloat() * Koala.CVPORT_VALUE_RANGE) + Koala.MIN_CVPORT_VALUE;
        float rightValue = (_random.nextFloat() * Koala.CVPORT_VALUE_RANGE) + Koala.MIN_CVPORT_VALUE;
        _leftOutput.setCurrentValue(leftValue);
        _rightOutput.setCurrentValue(rightValue);
    }

    @Override
    public void close() {}

    @Override
    public ModuleType getModuleType() {
        return ModuleType.DualNoise;
    }

    @Override
    public void reset() {}
}
