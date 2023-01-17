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

    public final ContinuousInputPort _input;
    public final ContinuousOutputPort _output;

    InverterModule() {
        _input = new ContinuousInputPort();
        _output = new ContinuousOutputPort();
        _inputPorts.put(SIGNAL_INPUT_PORT, _input);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _output);
    }

    @Override
    public void advance() {
        _output.setCurrentValue(0 - _input.getValue());
    }

    @Override
    public void close() {}

    @Override
    public ModuleType getModuleType() {
        return ModuleType.Inverter;
    }

    @Override
    public void reset() {}
}
