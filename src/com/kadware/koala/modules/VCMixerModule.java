/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.Port;

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

        ContinuousInputPort signalIn1 = (ContinuousInputPort) _ampModule1.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        signalIn1.changeAbbreviation("IN1");
        signalIn1.changeName("Signal 1 Input");
        ContinuousInputPort levelIn1 = (ContinuousInputPort) _ampModule1.getInputPort(VCAmplifierModule.CONTROL_INPUT_PORT_1);
        levelIn1.changeAbbreviation("LV1");
        levelIn1.changeName("Level 1 Modulation");
        ContinuousInputPort panIn1 = (ContinuousInputPort) _panModule1.getInputPort(VCPanningModule.CONTROL_INPUT_PORT_1);
        panIn1.changeAbbreviation("PN1");
        panIn1.changeName("Pan 1 Modulation");

        ContinuousInputPort signalIn2 = (ContinuousInputPort) _ampModule2.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        signalIn2.changeAbbreviation("IN2");
        signalIn2.changeName("Signal 2 Input");
        ContinuousInputPort levelIn2 = (ContinuousInputPort) _ampModule2.getInputPort(VCAmplifierModule.CONTROL_INPUT_PORT_1);
        levelIn2.changeAbbreviation("LV2");
        levelIn2.changeName("Level 2 Modulation");
        ContinuousInputPort panIn2 = (ContinuousInputPort) _panModule2.getInputPort(VCPanningModule.CONTROL_INPUT_PORT_1);
        panIn2.changeAbbreviation("PN2");
        panIn2.changeName("Pan 2 Modulation");

        ContinuousInputPort signalIn3 = (ContinuousInputPort) _ampModule3.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        signalIn3.changeAbbreviation("IN3");
        signalIn3.changeName("Signal 3 Input");
        ContinuousInputPort levelIn3 = (ContinuousInputPort) _ampModule3.getInputPort(VCAmplifierModule.CONTROL_INPUT_PORT_1);
        levelIn3.changeAbbreviation("LV3");
        levelIn3.changeName("Level 3 Modulation");
        ContinuousInputPort panIn3 = (ContinuousInputPort) _panModule3.getInputPort(VCPanningModule.CONTROL_INPUT_PORT_1);
        panIn3.changeAbbreviation("PN3");
        panIn3.changeName("Pan 3 Modulation");

        ContinuousInputPort signalIn4 = (ContinuousInputPort) _ampModule4.getInputPort(VCAmplifierModule.SIGNAL_INPUT_PORT);
        signalIn4.changeAbbreviation("IN4");
        signalIn4.changeName("Signal 4 Input");
        ContinuousInputPort levelIn4 = (ContinuousInputPort) _ampModule2.getInputPort(VCAmplifierModule.CONTROL_INPUT_PORT_1);
        levelIn4.changeAbbreviation("LV4");
        levelIn4.changeName("Level 4 Modulation");
        ContinuousInputPort panIn4 = (ContinuousInputPort) _panModule2.getInputPort(VCPanningModule.CONTROL_INPUT_PORT_1);
        panIn4.changeAbbreviation("PN4");
        panIn4.changeName("Pan 4 Modulation");

        Port.connectPorts(_ampModule1.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panModule1.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampModule2.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panModule2.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampModule3.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panModule3.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));
        Port.connectPorts(_ampModule4.getOutputPort(VCAmplifierModule.SIGNAL_OUTPUT_PORT),
                          _panModule4.getInputPort(VCPanningModule.SIGNAL_INPUT_PORT));

        _monoOut = new ContinuousOutputPort("Mono Output", "OUT");
        _leftOut = new ContinuousOutputPort("Left Output", "LFT");
        _rightOut = new ContinuousOutputPort("Right Output", "RGT");

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
        return "VCMIX";
    }

    @Override
    public String getModuleClass() {
        return "Value Controlled Mixer";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.VCMixer;
    }

    @Override
    public void reset() {}
}
