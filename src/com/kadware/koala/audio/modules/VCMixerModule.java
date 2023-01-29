/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.modules;

import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.Port;

/**
 * A 4x2 mixer with voltage-controlled level and pan per input channel
 */
public class VCMixerModule extends Module {

    public static final int SIGNAL_INPUT_PORT_1 = 0;
    public static final int SIGNAL_INPUT_PORT_2 = 1;
    public static final int SIGNAL_INPUT_PORT_3 = 2;
    public static final int SIGNAL_INPUT_PORT_4 = 3;
    public static final int LEVEL_MOD_INPUT_PORT_1 = 4;
    public static final int LEVEL_MOD_INPUT_PORT_2 = 5;
    public static final int LEVEL_MOD_INPUT_PORT_3 = 6;
    public static final int LEVEL_MOD_INPUT_PORT_4 = 7;
    public static final int PAN_MOD_INPUT_PORT_1 = 8;
    public static final int PAN_MOD_INPUT_PORT_2 = 9;
    public static final int PAN_MOD_INPUT_PORT_3 = 10;
    public static final int PAN_MOD_INPUT_PORT_4 = 11;
    public static final int SIGNAL_MONO_OUTPUT_PORT = 12;
    public static final int SIGNAL_LEFT_OUTPUT_PORT = 13;
    public static final int SIGNAL_RIGHT_OUTPUT_PORT = 14;

    private final ContinuousOutputPort _monoOut;
    private final ContinuousOutputPort _leftOut;
    private final ContinuousOutputPort _rightOut;

    private final VCAmplifierModule _ampModule1;
    private final VCAmplifierModule _ampModule2;
    private final VCAmplifierModule _ampModule3;
    private final VCAmplifierModule _ampModule4;

    private final VCPanningModule _panModule1;
    private final VCPanningModule _panModule2;
    private final VCPanningModule _panModule3;
    private final VCPanningModule _panModule4;

    private final ContinuousOutputPort _pan1LeftOut;
    private final ContinuousOutputPort _pan1RightOut;
    private final ContinuousOutputPort _pan2LeftOut;
    private final ContinuousOutputPort _pan2RightOut;
    private final ContinuousOutputPort _pan3LeftOut;
    private final ContinuousOutputPort _pan3RightOut;
    private final ContinuousOutputPort _pan4LeftOut;
    private final ContinuousOutputPort _pan4RightOut;

    VCMixerModule() {
        _ampModule1 = new VCAmplifierModule();
        _ampModule2 = new VCAmplifierModule();
        _ampModule3 = new VCAmplifierModule();
        _ampModule4 = new VCAmplifierModule();

        _panModule1 = new VCPanningModule();
        _panModule2 = new VCPanningModule();
        _panModule3 = new VCPanningModule();
        _panModule4 = new VCPanningModule();

        _pan1LeftOut = (ContinuousOutputPort) _panModule1.getOutputPort(FixedPanningModule.LEFT_OUTPUT_PORT);
        _pan1RightOut = (ContinuousOutputPort) _panModule1.getOutputPort(FixedPanningModule.RIGHT_OUTPUT_PORT);
        _pan2LeftOut = (ContinuousOutputPort) _panModule2.getOutputPort(FixedPanningModule.LEFT_OUTPUT_PORT);
        _pan2RightOut = (ContinuousOutputPort) _panModule2.getOutputPort(FixedPanningModule.RIGHT_OUTPUT_PORT);
        _pan3LeftOut = (ContinuousOutputPort) _panModule3.getOutputPort(FixedPanningModule.LEFT_OUTPUT_PORT);
        _pan3RightOut = (ContinuousOutputPort) _panModule3.getOutputPort(FixedPanningModule.RIGHT_OUTPUT_PORT);
        _pan4LeftOut = (ContinuousOutputPort) _panModule4.getOutputPort(FixedPanningModule.LEFT_OUTPUT_PORT);
        _pan4RightOut = (ContinuousOutputPort) _panModule4.getOutputPort(FixedPanningModule.RIGHT_OUTPUT_PORT);

        var signalIn1 = (ContinuousInputPort) _ampModule1.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        var levelIn1 = (ContinuousInputPort) _ampModule1.getInputPort(VCAmplifierModule.CONTROL_INPUT_PORT);
        var panIn1 = (ContinuousInputPort) _panModule1.getInputPort(VCPanningModule.CONTROL_INPUT_PORT);

        var signalIn2 = (ContinuousInputPort) _ampModule2.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        var levelIn2 = (ContinuousInputPort) _ampModule2.getInputPort(VCAmplifierModule.CONTROL_INPUT_PORT);
        var panIn2 = (ContinuousInputPort) _panModule2.getInputPort(VCPanningModule.CONTROL_INPUT_PORT);

        var signalIn3 = (ContinuousInputPort) _ampModule3.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        var levelIn3 = (ContinuousInputPort) _ampModule3.getInputPort(VCAmplifierModule.CONTROL_INPUT_PORT);
        var panIn3 = (ContinuousInputPort) _panModule3.getInputPort(VCPanningModule.CONTROL_INPUT_PORT);

        var signalIn4 = (ContinuousInputPort) _ampModule4.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        var levelIn4 = (ContinuousInputPort) _ampModule2.getInputPort(VCAmplifierModule.CONTROL_INPUT_PORT);
        var panIn4 = (ContinuousInputPort) _panModule2.getInputPort(VCPanningModule.CONTROL_INPUT_PORT);

        Port.connectPorts(_ampModule1.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panModule1.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampModule2.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panModule2.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampModule3.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panModule3.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampModule4.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panModule4.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));

        _monoOut = new ContinuousOutputPort();
        _leftOut = new ContinuousOutputPort();
        _rightOut = new ContinuousOutputPort();

        _inputPorts.put(SIGNAL_INPUT_PORT_1, signalIn1);
        _inputPorts.put(SIGNAL_INPUT_PORT_2, signalIn2);
        _inputPorts.put(SIGNAL_INPUT_PORT_3, signalIn3);
        _inputPorts.put(SIGNAL_INPUT_PORT_4, signalIn4);
        _inputPorts.put(LEVEL_MOD_INPUT_PORT_1, levelIn1);
        _inputPorts.put(LEVEL_MOD_INPUT_PORT_2, levelIn2);
        _inputPorts.put(LEVEL_MOD_INPUT_PORT_3, levelIn3);
        _inputPorts.put(LEVEL_MOD_INPUT_PORT_4, levelIn4);
        _inputPorts.put(PAN_MOD_INPUT_PORT_1, panIn1);
        _inputPorts.put(PAN_MOD_INPUT_PORT_2, panIn2);
        _inputPorts.put(PAN_MOD_INPUT_PORT_3, panIn3);
        _inputPorts.put(PAN_MOD_INPUT_PORT_4, panIn4);
        _outputPorts.put(SIGNAL_MONO_OUTPUT_PORT, _monoOut);
        _outputPorts.put(SIGNAL_LEFT_OUTPUT_PORT, _leftOut);
        _outputPorts.put(SIGNAL_RIGHT_OUTPUT_PORT, _rightOut);
    }

    @Override
    public void advance() {
        _ampModule1.advance();
        _ampModule2.advance();
        _ampModule3.advance();
        _ampModule4.advance();
        _panModule1.advance();
        _panModule2.advance();
        _panModule3.advance();
        _panModule4.advance();

        double left = _pan1LeftOut.getCurrentValue()
            + _pan2LeftOut.getCurrentValue()
            + _pan3LeftOut.getCurrentValue()
            + _pan4LeftOut.getCurrentValue();

        double right = _pan1RightOut.getCurrentValue()
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
    public ModuleType getModuleType() {
        return ModuleType.VCMixer;
    }

    @Override
    public void reset() {}
}
