/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.OutputPort;
import java.util.Random;

public class NoiseModule extends Module {

    public static final int OUTPUT_PORT = 0;
    public final Random _random = new Random(System.currentTimeMillis());

    NoiseModule() {
        _outputPorts.put(OUTPUT_PORT, new OutputPort("Signal"));
    }

    @Override
    public void advance(
    ) {
        double value = (_random.nextDouble() * (2 * Koala.MAX_PORT_MAGNITUDE)) - Koala.MAX_PORT_MAGNITUDE;
        _outputPorts.get(OUTPUT_PORT).setCurrentValue(value);
    }

    @Override
    public void close() {}

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
