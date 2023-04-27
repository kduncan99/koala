/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.Koala;
import com.bearsnake.koala.modules.elements.ports.AnalogOutputPort;

public class SignalModifierModule extends Module {

    //  TODO performs the following:
    //      scales the input signal (multiplies by some value 0.0 to 1.0)
    //      inverts the input signal (multiplies by -1.0)
    //      shifts the input signal (adds or subtracts 0.0 to 1.0)
    //      Needs a dual meter showing input vs output
    //      Needs input port and output port
    public static final int SIGNAL_INPUT_PORT_ID = 0;
    public static final int SIGNAL_OUTPUT_PORT_ID = 1;

    private final AnalogOutputPort _signalOutput;

    public SignalModifierModule() {
        super(1, "Noise");

        //  ports
        _signalOutput = new AnalogOutputPort("signal");
        _ports.put(SIGNAL_OUTPUT_PORT_ID, _signalOutput);
        getPortsSection().setConnection(0, 1, _signalOutput);

        register(this);
    }

    @Override
    public synchronized void advance() {
        super.advance();
        _signalOutput.setSignalValue((Koala.RANDOM.nextDouble() * 2.0) - 1.0);
    }

    @Override
    public void close() {}

    @Override
    public void repaint() {}

    @Override
    public void reset() {}
}
