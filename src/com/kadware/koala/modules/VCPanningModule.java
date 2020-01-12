/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

@SuppressWarnings("Duplicates")
public class VCPanningModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int CONTROL_INPUT_PORT_1 = 1;
    public static final int CONTROL_INPUT_PORT_2 = 2;
    public static final int LEFT_OUTPUT_PORT = 3;
    public static final int RIGHT_OUTPUT_PORT = 4;

    private final ContinuousInputPort _signalIn;
    private final ContinuousInputPort _controlIn1;
    private final ContinuousInputPort _controlIn2;
    private final ContinuousOutputPort _leftOut;
    private final ContinuousOutputPort _rightOut;

    private float _baseValue = 0.0f;    //  ranges from -5.0 (hard left) to 5.0 (hard right)

    VCPanningModule() {
        _signalIn = new ContinuousInputPort("Signal Input", "IN");
        _controlIn1 = new ContinuousInputPort("Control Input 1", "CV1");
        _controlIn2 = new ContinuousInputPort("Control Input 2", "CV2");
        _leftOut = new ContinuousOutputPort("Left Output", "LFT");
        _rightOut = new ContinuousOutputPort("Right Output", "RGT");

        _inputPorts.put(SIGNAL_INPUT_PORT, _signalIn);
        _inputPorts.put(CONTROL_INPUT_PORT_1, _controlIn1);
        _inputPorts.put(CONTROL_INPUT_PORT_2, _controlIn2);
        _outputPorts.put(LEFT_OUTPUT_PORT, _leftOut);
        _outputPorts.put(RIGHT_OUTPUT_PORT, _rightOut);
    }

    @Override
    public void advance(
    ) {
        float signal = _signalIn.getValue();

        float control = _baseValue + _controlIn1.getValue() + _controlIn2.getValue();
        control = Math.max(Math.min(control, Koala.MAX_CVPORT_VALUE), Koala.MIN_CVPORT_VALUE);
        float leftScalar = rescale(control);
        float rightScalar = rescale(control);

        _leftOut.setCurrentValue(leftScalar * signal);
        _rightOut.setCurrentValue(rightScalar * signal);
    }

    @Override
    public void close() {}

    /**
     * Getter
     * @return base value ranging from -5.0 to 5.0
     */
    public float getBaseValue() {
        return _baseValue;
    }

    @Override
    public String getModuleAbbreviation() {
        return "VCPAN";
    }

    @Override
    public String getModuleClass() {
        return "Value Controlled Panner";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.VCPanner;
    }

    /**
     * Rescales the input value with the standard koala range,
     * to the domain of log10() which produces a range of 0.0 to 1.0 (i.e., 1.0 to 10.0).
     */
    private float rescale(
        final float value
    ) {
        return ((value - Koala.MIN_CVPORT_VALUE) * 9 / Koala.CVPORT_VALUE_RANGE) + 1.0f;
    }

    @Override
    public void reset() {}

    /**
     * Sets the base value for pan.
     * Input range is from -5.0 (hard left) to +5.0 (hard right).
     * We rescale this internally to left and right scalar values.
     */
    public void setBaseValue(
        final float value
    ) {
        if (value > Koala.MAX_CVPORT_VALUE) {
            _baseValue = Koala.MAX_CVPORT_VALUE;
        } else if (value < Koala.MIN_CVPORT_VALUE) {
            _baseValue = Koala.MIN_CVPORT_VALUE;
        } else {
            _baseValue = value;
        }
    }
}
