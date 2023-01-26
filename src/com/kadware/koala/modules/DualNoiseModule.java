/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.ports.ContinuousOutputPort;

public class DualNoiseModule extends Module {

    public static final int LEFT_SIGNAL_OUTPUT_PORT = 0;
    public static final int RIGHT_SIGNAL_OUTPUT_PORT = 1;

    private final NoiseModule _leftNoise;
    private final NoiseModule _rightNoise;

    DualNoiseModule() {
        _leftNoise = new NoiseModule();
        _rightNoise = new NoiseModule();

        var _leftOutput = (ContinuousOutputPort) _leftNoise.getOutputPort(NoiseModule.SIGNAL_OUTPUT_PORT);
        var _rightOutput = (ContinuousOutputPort) _rightNoise.getOutputPort(NoiseModule.SIGNAL_OUTPUT_PORT);
        _outputPorts.put(LEFT_SIGNAL_OUTPUT_PORT, _leftOutput);
        _outputPorts.put(RIGHT_SIGNAL_OUTPUT_PORT, _rightOutput);
    }

    @Override
    public void advance() {
        _leftNoise.advance();
        _rightNoise.advance();
    }

    @Override
    public void close() {}

    @Override
    public ModuleType getModuleType() {
        return ModuleType.DualNoise;
    }

    @Override
    public void reset() {
        _leftNoise.reset();
        _rightNoise.reset();
    }
}
