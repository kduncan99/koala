/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

/**
 * Basic value-controlled amplifier.
 * Something of a misnomer, since we don't amplify; we attenuate.
 * The input voltage is compared to the range, and converted to a scaled value of 0.0 to 1.0,
 * then is used as a multiplier against the input signal to product the output signal.
 */
public class VCAmplifierModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int CONTROL_INPUT_PORT_1 = 1;
    public static final int CONTROL_INPUT_PORT_2 = 2;
    public static final int SIGNAL_OUTPUT_PORT = 3;

    private final ContinuousInputPort _signalIn;
    private final ContinuousInputPort _controlIn1;
    private final ContinuousInputPort _controlIn2;
    private final ContinuousOutputPort _signalOut;

    private double _baseValue = 5.0f;

    VCAmplifierModule() {
        _signalIn = new ContinuousInputPort();
        _controlIn1 = new ContinuousInputPort();
        _controlIn2 = new ContinuousInputPort();
        _signalOut = new ContinuousOutputPort();
        _inputPorts.put(SIGNAL_INPUT_PORT, _signalIn);
        _inputPorts.put(CONTROL_INPUT_PORT_1, _controlIn1);
        _inputPorts.put(CONTROL_INPUT_PORT_2, _controlIn2);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _signalOut);
    }

    @Override
    public void advance() {
        //  sum of control value is expected to vary from _minimum to _maximum.
        //  re-scale and re-bias this such that we get a multiplier from 0.0 to 1.0,
        //  then apply it to the signal input to produce the signal output.
        double controlValue = _baseValue + _controlIn1.getValue() + _controlIn2.getValue();
        double multiplier = (controlValue - Koala.MIN_CVPORT_VALUE) / Koala.CVPORT_VALUE_RANGE;
        double signalOutValue = multiplier * _signalIn.getValue();
        _signalOut.setCurrentValue(signalOutValue);
    }

    @Override
    public void close() {}

    public double getBaseValue() {
        return _baseValue;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.VCAmplifier;
    }

    @Override
    public void reset() {}

    public void setBaseValue(
        final double value
    ) {
        _baseValue = value;
    }
}
