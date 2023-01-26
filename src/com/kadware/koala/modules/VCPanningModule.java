/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

public class VCPanningModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int CONTROL_INPUT_PORT = 1;
    public static final int LEFT_OUTPUT_PORT = 2;
    public static final int RIGHT_OUTPUT_PORT = 3;

    private final ContinuousInputPort _signalIn;
    private final ContinuousInputPort _controlIn;
    private final ContinuousOutputPort _leftOut;
    private final ContinuousOutputPort _rightOut;

    private double _baseValue = 0.0f;    //  ranges from -1.0 (hard left) to 1.0 (hard right)

    VCPanningModule() {
        _signalIn = new ContinuousInputPort();
        _controlIn = new ContinuousInputPort();
        _leftOut = new ContinuousOutputPort();
        _rightOut = new ContinuousOutputPort();

        _inputPorts.put(SIGNAL_INPUT_PORT, _signalIn);
        _inputPorts.put(CONTROL_INPUT_PORT, _controlIn);
        _outputPorts.put(LEFT_OUTPUT_PORT, _leftOut);
        _outputPorts.put(RIGHT_OUTPUT_PORT, _rightOut);
    }

    @Override
    public void advance() {
        double signal = _signalIn.getValue();
        double control = Koala.BIPOLAR_RANGE.clipValue(_baseValue + _controlIn.getValue());

        var rightScalar = (control + 1.0) / 2.0;
        var leftScalar = 1.0 - rightScalar;

        _leftOut.setCurrentValue(leftScalar * signal);
        _rightOut.setCurrentValue(rightScalar * signal);
    }

    @Override
    public void close() {}

    /**
     * Getter
     * @return base value ranging from -5.0 to 5.0
     */
    public double getBaseValue() {
        return _baseValue;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.VCPanner;
    }

    @Override
    public void reset() {}

    /**
     * Sets the base value for pan.
     * Input range is from -5.0 (hard left) to +5.0 (hard right).
     * We rescale this internally to left and right scalar values.
     */
    public void setBaseValue(
        final double value
    ) {
        _baseValue = Koala.BIPOLAR_RANGE.clipValue(value);
    }
}
