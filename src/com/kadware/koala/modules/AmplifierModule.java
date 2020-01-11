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
public class AmplifierModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int CONTROL_INPUT_PORT_1 = 1;
    public static final int CONTROL_INPUT_PORT_2 = 2;
    public static final int SIGNAL_OUTPUT_PORT = 3;

    private float _baseValue = 5.0f;

    AmplifierModule() {
        _inputPorts.put(SIGNAL_INPUT_PORT, new ContinuousInputPort("Signal Input", "IN"));
        _inputPorts.put(CONTROL_INPUT_PORT_1, new ContinuousInputPort("Control Input 1", "CV1"));
        _inputPorts.put(CONTROL_INPUT_PORT_2, new ContinuousInputPort("Control Input 2", "CV2"));
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort("Signal Output", "OUT"));
    }

    @Override
    public void advance(
    ) {
        //  sum of control value is expected to vary from _minimum to _maximum.
        //  rescale and rebias this such that we get a multiplier from 0.0 to 1.0,
        //  then apply it to the signal input to produce the signal output.
        ContinuousInputPort cin1 = (ContinuousInputPort) _inputPorts.get(CONTROL_INPUT_PORT_1);
        ContinuousInputPort cin2 = (ContinuousInputPort) _inputPorts.get(CONTROL_INPUT_PORT_2);
        float controlValue = _baseValue + cin1.getValue() + cin2.getValue();
        float multiplier = (controlValue - Koala.MIN_CVPORT_VALUE) / Koala.CVPORT_VALUE_RANGE;

        ContinuousInputPort sigin = (ContinuousInputPort) _inputPorts.get(SIGNAL_INPUT_PORT);
        float signalInValue = sigin.getValue();
        float signalOutValue = multiplier * signalInValue;

        ContinuousOutputPort sigout = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        sigout.setCurrentValue(signalOutValue);
    }

    @Override
    public void close() {}

    public double getBaseValue() {
        return _baseValue;
    }

    @Override
    public String getModuleAbbreviation() {
        return "VCA";
    }

    @Override
    public String getModuleClass() {
        return "CV Controlled Amplifier";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.Amplifier;
    }

    @Override
    public void reset() {}

    public void setBaseValue(
        final float value
    ) {
        _baseValue = value;
    }
}
