/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.modules.elements.ports.AnalogInputPort;
import com.bearsnake.koala.modules.elements.ports.AnalogOutputPort;

/**
 * Our virtual equivalent of a simple VCA
 */
public class VariableControlledAmplifierModule extends Module {

    public static final int SIGNAL_INPUT_PORT_ID = 0;
    public static final int SIGNAL_OUTPUT_PORT_ID = 1;
    public static final int CONTROL_INPUT_PORT_ID = 2;

    private final AnalogInputPort _controlInput;
    private final AnalogInputPort _signalInput;
    private final AnalogOutputPort _signalOutput;

    public VariableControlledAmplifierModule() {
        super(2, "VCAmp");

        //  ports
        _controlInput = new AnalogInputPort("control");
        _signalInput = new AnalogInputPort("signal");
        _signalOutput = new AnalogOutputPort("signal");

        _ports.put(CONTROL_INPUT_PORT_ID, _controlInput);
        _ports.put(SIGNAL_INPUT_PORT_ID, _signalInput);
        _ports.put(SIGNAL_OUTPUT_PORT_ID, _signalOutput);

        getPortsSection().setConnection(0, 1, _controlInput);
        getPortsSection().setConnection(1, 1, _signalInput);
        getPortsSection().setConnection(1, 0, _signalOutput);
    }

    @Override
    public synchronized void advance() {
        super.advance();
        _signalOutput.setSignalValue(_controlInput.getSignalValue() * _signalInput.getSignalValue());
    }

    @Override
    public void close() {}

    @Override
    public Configuration getConfiguration() {
        return new Configuration();
    }

    @Override
    public void repaint() {}

    @Override
    public void reset() {}

    @Override
    public void setConfiguration(final Configuration configuration) {}
}
