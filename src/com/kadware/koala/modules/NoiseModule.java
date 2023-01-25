/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;
import java.util.Random;

public class NoiseModule extends Module {

    public static final int SIGNAL_OUTPUT_PORT = 0;
    public final Random _random = new Random(System.currentTimeMillis());

    NoiseModule() {
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort());
    }

    @Override
    public void advance() {
        ContinuousOutputPort outp = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        double value = (_random.nextDouble() * Koala.CVPORT_VALUE_RANGE) + Koala.MIN_CVPORT_VALUE;
        outp.setCurrentValue(value);
    }

    @Override
    public void close() {}

    @Override
    public ModuleType getModuleType() {
        return ModuleType.Noise;
    }

    @Override
    public void reset() {}
}
