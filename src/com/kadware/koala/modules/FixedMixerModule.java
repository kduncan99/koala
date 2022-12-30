/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.Port;

/**
 * Implements a 4x2x1 mixer where the levels and pan amounts can be set, but not modulated.
 * This is a meta-module, containing several amplifier and panning sub-modules.
 */
public class FixedMixerModule extends Module {

    public static final int SIGNAL_INPUT_PORT_1 = 0;
    public static final int SIGNAL_INPUT_PORT_2 = 1;
    public static final int SIGNAL_INPUT_PORT_3 = 2;
    public static final int SIGNAL_INPUT_PORT_4 = 3;
    public static final int SIGNAL_MONO_OUTPUT_PORT = 4;
    public static final int SIGNAL_LEFT_OUTPUT_PORT = 5;
    public static final int SIGNAL_RIGHT_OUTPUT_PORT = 6;

    private final ContinuousOutputPort _monoOut;
    private final ContinuousOutputPort _leftOut;
    private final ContinuousOutputPort _rightOut;

    private final FixedAmplifierModule _ampMod1;
    private final FixedAmplifierModule _ampMod2;
    private final FixedAmplifierModule _ampMod3;
    private final FixedAmplifierModule _ampMod4;

    private final FixedPanningModule _panMod1;
    private final FixedPanningModule _panMod2;
    private final FixedPanningModule _panMod3;
    private final FixedPanningModule _panMod4;

    private final ContinuousOutputPort _pan1LeftOut;
    private final ContinuousOutputPort _pan1RightOut;
    private final ContinuousOutputPort _pan2LeftOut;
    private final ContinuousOutputPort _pan2RightOut;
    private final ContinuousOutputPort _pan3LeftOut;
    private final ContinuousOutputPort _pan3RightOut;
    private final ContinuousOutputPort _pan4LeftOut;
    private final ContinuousOutputPort _pan4RightOut;

    FixedMixerModule() {
        _ampMod1 = new FixedAmplifierModule();
        _ampMod2 = new FixedAmplifierModule();
        _ampMod3 = new FixedAmplifierModule();
        _ampMod4 = new FixedAmplifierModule();

        _panMod1 = new FixedPanningModule();
        _panMod2 = new FixedPanningModule();
        _panMod3 = new FixedPanningModule();
        _panMod4 = new FixedPanningModule();

        _pan1LeftOut = (ContinuousOutputPort) _panMod1.getOutputPort(FixedPanningModule.LEFT_OUTPUT_PORT);
        _pan1RightOut = (ContinuousOutputPort) _panMod1.getOutputPort(FixedPanningModule.RIGHT_OUTPUT_PORT);
        _pan2LeftOut = (ContinuousOutputPort) _panMod2.getOutputPort(FixedPanningModule.LEFT_OUTPUT_PORT);
        _pan2RightOut = (ContinuousOutputPort) _panMod2.getOutputPort(FixedPanningModule.RIGHT_OUTPUT_PORT);
        _pan3LeftOut = (ContinuousOutputPort) _panMod3.getOutputPort(FixedPanningModule.LEFT_OUTPUT_PORT);
        _pan3RightOut = (ContinuousOutputPort) _panMod3.getOutputPort(FixedPanningModule.RIGHT_OUTPUT_PORT);
        _pan4LeftOut = (ContinuousOutputPort) _panMod4.getOutputPort(FixedPanningModule.LEFT_OUTPUT_PORT);
        _pan4RightOut = (ContinuousOutputPort) _panMod4.getOutputPort(FixedPanningModule.RIGHT_OUTPUT_PORT);

        ContinuousInputPort signalIn1 = (ContinuousInputPort) _ampMod1.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        signalIn1.changeAbbreviation("IN1");
        signalIn1.changeName("Signal 1 Input");

        ContinuousInputPort signalIn2 = (ContinuousInputPort) _ampMod2.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        signalIn2.changeAbbreviation("IN2");
        signalIn2.changeName("Signal 2 Input");

        ContinuousInputPort signalIn3 = (ContinuousInputPort) _ampMod3.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        signalIn3.changeAbbreviation("IN3");
        signalIn3.changeName("Signal 3 Input");

        ContinuousInputPort signalIn4 = (ContinuousInputPort) _ampMod4.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        signalIn4.changeAbbreviation("IN4");
        signalIn4.changeName("Signal 4 Input");

        Port.connectPorts(_ampMod1.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panMod1.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampMod2.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panMod2.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampMod3.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panMod3.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampMod4.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panMod4.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));

        _monoOut = new ContinuousOutputPort("Mono Output", "OUT");
        _leftOut = new ContinuousOutputPort("Left Output", "LFT");
        _rightOut = new ContinuousOutputPort("Right Output", "RGT");

        _inputPorts.put(SIGNAL_INPUT_PORT_1, signalIn1);
        _inputPorts.put(SIGNAL_INPUT_PORT_2, signalIn2);
        _inputPorts.put(SIGNAL_INPUT_PORT_3, signalIn3);
        _inputPorts.put(SIGNAL_INPUT_PORT_4, signalIn4);
        _outputPorts.put(SIGNAL_MONO_OUTPUT_PORT, _monoOut);
        _outputPorts.put(SIGNAL_LEFT_OUTPUT_PORT, _leftOut);
        _outputPorts.put(SIGNAL_RIGHT_OUTPUT_PORT, _rightOut);
    }

    @Override
    public void advance() {
        _ampMod1.advance();
        _ampMod2.advance();
        _ampMod3.advance();
        _ampMod4.advance();
        _panMod1.advance();
        _panMod2.advance();
        _panMod3.advance();
        _panMod4.advance();

        float left = _pan1LeftOut.getCurrentValue()
            + _pan2LeftOut.getCurrentValue()
            + _pan3LeftOut.getCurrentValue()
            + _pan4LeftOut.getCurrentValue();

        float right = _pan1RightOut.getCurrentValue()
            + _pan2RightOut.getCurrentValue()
            + _pan3RightOut.getCurrentValue()
            + _pan4RightOut.getCurrentValue();

        _monoOut.setCurrentValue(left + right);
        _leftOut.setCurrentValue(left);
        _rightOut.setCurrentValue(right);
    }

    @Override
    public void close() {}

    @Override
    public String getModuleAbbreviation() {
        return "MIX";
    }

    @Override
    public String getModuleClass() {
        return "Mixer";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.FixedMixer;
    }

    @Override
    public void reset() {}
}
