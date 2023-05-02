/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.modules.elements.ports.AnalogDestinationPort;
import com.bearsnake.koala.modules.elements.ports.AnalogSourcePort;

/**
 * Pans the input left or right according to the control input
 */
public class VariableControlledPanModule extends Module {

    public static final String DEFAULT_NAME = "VCPan";
    public static final int CONTROL_INPUT_PORT_ID = 0;
    public static final int SIGNAL_INPUT_PORT_ID = 1;
    public static final int LEFT_OUTPUT_PORT_ID = 2;
    public static final int RIGHT_OUTPUT_PORT_ID = 3;

    private final AnalogDestinationPort _controlInput;
    private final AnalogDestinationPort _signalInput;
    private final AnalogSourcePort _leftOutput;
    private final AnalogSourcePort _rightOutput;

    public VariableControlledPanModule(
        final String moduleName
    ) {
        super(2, moduleName);

        //  ports
        _controlInput = new AnalogDestinationPort(moduleName, "Control Input", "control");
        _signalInput = new AnalogDestinationPort(moduleName, "Signal Input", "signal");
        _leftOutput = new AnalogSourcePort(moduleName, "Left Output", "left");
        _rightOutput = new AnalogSourcePort(moduleName, "Right Output", "right");

        _ports.put(CONTROL_INPUT_PORT_ID, _controlInput);
        _ports.put(SIGNAL_INPUT_PORT_ID, _signalInput);
        _ports.put(LEFT_OUTPUT_PORT_ID, _leftOutput);
        _ports.put(RIGHT_OUTPUT_PORT_ID, _rightOutput);

        getPortsSection().setConnection(0, 0, _controlInput);
        getPortsSection().setConnection(1, 0, _signalInput);
        getPortsSection().setConnection(0, 1, _leftOutput);
        getPortsSection().setConnection(1, 1, _rightOutput);
    }

    @Override
    public synchronized void advance() {
        super.advance();
        var rightScalar = (_controlInput.getSignalValue() + 1.0) / 2.0;
        var leftScalar = 1.0 - rightScalar;
        _leftOutput.setSignalValue(leftScalar * _signalInput.getSignalValue());
        _rightOutput.setSignalValue(rightScalar * _signalInput.getSignalValue());
    }

    @Override
    public Configuration getConfiguration() {
        return new Configuration(getIdentifier(), getName());
    }

    @Override
    public void setConfiguration(
        final Configuration configuration
    ) {
        setIdentifier(configuration._identifier);
        setName(configuration._name);
    }
}
