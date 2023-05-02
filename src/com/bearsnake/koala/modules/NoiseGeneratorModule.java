/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.Koala;
import com.bearsnake.koala.modules.elements.ports.AnalogSourcePort;

public class NoiseGeneratorModule extends Module {

    public static final String DEFAULT_NAME = "Noise";
    public static final int SIGNAL_OUTPUT_PORT_ID = 0;

    private final AnalogSourcePort _signalOutput;

    public NoiseGeneratorModule(
        final String moduleName
    ) {
        super(1, moduleName);

        //  ports
        _signalOutput = new AnalogSourcePort(moduleName, "Signal Output", "signal");
        _ports.put(SIGNAL_OUTPUT_PORT_ID, _signalOutput);
        getPortsSection().setConnection(0, 1, _signalOutput);
    }

    @Override
    public synchronized void advance() {
        super.advance();
        _signalOutput.setSignalValue((Koala.RANDOM.nextDouble() * 2.0) - 1.0);
    }

    @Override
    public void close() {}

    @Override
    public Configuration getConfiguration() {
        return new Configuration(getIdentifier(), getName());
    }

    @Override
    public void repaint() {}

    @Override
    public void reset() {}

    @Override
    public void setConfiguration(final Configuration configuration) {
        setIdentifier(configuration._identifier);
        setName(configuration._name);
    }
}
