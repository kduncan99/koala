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
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort("Signal Output", "OUT"));
    }

    @Override
    public void advance(
    ) {
        ContinuousOutputPort outp = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        float value = (_random.nextFloat() * Koala.CVPORT_VALUE_RANGE) + Koala.MIN_CVPORT_VALUE;
        outp.setCurrentValue(value);
    }

    @Override
    public void close() {}

    @Override
    public String getModuleAbbreviation() {
        return "NS";
    }

    @Override
    public String getModuleClass() {
        return "Noise Source";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.Noise;
    }

    @Override
    public void reset() {}
}
