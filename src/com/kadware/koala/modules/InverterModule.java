/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

public class InverterModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int SIGNAL_OUTPUT_PORT = 1;

    InverterModule() {
        _inputPorts.put(SIGNAL_INPUT_PORT, new ContinuousInputPort("Signal Input", "IN"));
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort("Signal Output", "OUT"));
    }

    @Override
    public void advance(
    ) {
        ContinuousInputPort inp = (ContinuousInputPort) _inputPorts.get(SIGNAL_INPUT_PORT);
        ContinuousOutputPort outp = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        outp.setCurrentValue(0 - inp.getValue());
    }

    @Override
    public void close() {}

    @Override
    public String getModuleAbbreviation() {
        return "INV";
    }

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
