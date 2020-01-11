/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.ports.InputPort;
import com.kadware.koala.ports.OutputPort;

public class InverterModule extends Module {

    public static final int INPUT_PORT = 1;
    public static final int OUTPUT_PORT = 1;

    InverterModule() {
        _inputPorts.put(INPUT_PORT, new InputPort("Input"));
        _outputPorts.put(OUTPUT_PORT, new OutputPort("Output"));
    }

    @Override
    public void advance(
    ) {
        double value = 0 - _inputPorts.get(INPUT_PORT).getValue();
        _outputPorts.get(OUTPUT_PORT).setCurrentValue(value);
    }

    @Override
    public void close() {}

    @Override
    public String getModuleClass() {
        return "Inverter";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.Inverter;
    }

    @Override
    public void reset() {}
}
