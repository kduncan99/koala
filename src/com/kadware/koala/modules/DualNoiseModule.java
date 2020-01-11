/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;

import java.util.Random;

public class DualNoiseModule extends Module {

    public static final int LEFT_SIGNAL_OUTPUT_PORT = 0;
    public static final int RIGHT_SIGNAL_OUTPUT_PORT = 1;
    public final Random _random = new Random(System.currentTimeMillis());

    DualNoiseModule() {
        _outputPorts.put(LEFT_SIGNAL_OUTPUT_PORT, new ContinuousOutputPort("Left Output", "LFT"));
        _outputPorts.put(RIGHT_SIGNAL_OUTPUT_PORT, new ContinuousOutputPort("Right Output", "RGT"));
    }

    @Override
    public void advance(
    ) {
        float leftValue = (_random.nextFloat() * Koala.CVPORT_VALUE_RANGE) + Koala.MIN_CVPORT_VALUE;
        float rightValue = (_random.nextFloat() * Koala.CVPORT_VALUE_RANGE) + Koala.MIN_CVPORT_VALUE;
        ContinuousOutputPort leftOut = (ContinuousOutputPort) _outputPorts.get(LEFT_SIGNAL_OUTPUT_PORT);
        ContinuousOutputPort rightOut = (ContinuousOutputPort) _outputPorts.get(RIGHT_SIGNAL_OUTPUT_PORT);
        leftOut.setCurrentValue(leftValue);
        rightOut.setCurrentValue(rightValue);
    }

    @Override
    public void close() {}

    @Override
    public String getModuleAbbreviation() {
        return "NS";
    }

    @Override
    public String getModuleClass() {
        return "Dual Noise Source";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.DualNoise;
    }

    @Override
    public void reset() {}
}
