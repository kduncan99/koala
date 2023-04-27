/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.Koala;
import com.bearsnake.koala.modules.elements.ports.AnalogOutputPort;

public class NoiseGeneratorModule extends Module {

    public static final int SIGNAL_OUTPUT_PORT_ID = 0;

    private final AnalogOutputPort _signalOutput;

    public NoiseGeneratorModule() {
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
