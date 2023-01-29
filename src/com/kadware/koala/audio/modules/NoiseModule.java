/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;

public class NoiseModule extends Module {

    public static final int SIGNAL_OUTPUT_PORT = 0;

    NoiseModule() {
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort());
    }

    @Override
    public void advance() {
        ContinuousOutputPort outp = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        double value = Koala.getNextRandomCV();
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
