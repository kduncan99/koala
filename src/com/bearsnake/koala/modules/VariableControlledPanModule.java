/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.modules.elements.ports.AnalogInputPort;
import com.bearsnake.koala.modules.elements.ports.AnalogOutputPort;

/**
 * Pans the input left or right according to the control input
 */
public class VariableControlledPanModule extends Module {

    public static final int CONTROL_INPUT_PORT_ID = 0;
    public static final int SIGNAL_INPUT_PORT_ID = 1;
    public static final int LEFT_OUTPUT_PORT_ID = 2;
    public static final int RIGHT_OUTPUT_PORT_ID = 3;

    private final AnalogInputPort _controlInput;
    private final AnalogInputPort _signalInput;
    private final AnalogOutputPort _leftOutput;
    private final AnalogOutputPort _rightOutput;

    public VariableControlledPanModule() {
        super(2, "VCAmp");

        //  ports
        _controlInput = new AnalogInputPort("control");
        _signalInput = new AnalogInputPort("signal");
        _leftOutput = new AnalogOutputPort("left");
        _rightOutput = new AnalogOutputPort("right");

        _ports.put(CONTROL_INPUT_PORT_ID, _controlInput);
        _ports.put(SIGNAL_INPUT_PORT_ID, _signalInput);
        _ports.put(LEFT_OUTPUT_PORT_ID, _leftOutput);
        _ports.put(RIGHT_OUTPUT_PORT_ID, _rightOutput);

        getPortsSection().setConnection(0, 1, _controlInput);
        getPortsSection().setConnection(1, 1, _signalInput);
        getPortsSection().setConnection(0, 0, _leftOutput);
        getPortsSection().setConnection(1, 0, _rightOutput);
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
