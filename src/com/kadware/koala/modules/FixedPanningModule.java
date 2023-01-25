/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

/**
 * Implements a fixed (non controllable) pan module.
 * Intended primarily as a submodule, but possibly useful elsewhere.
 */
public class FixedPanningModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int LEFT_OUTPUT_PORT = 1;
    public static final int RIGHT_OUTPUT_PORT = 2;

    private final ContinuousInputPort _signalIn;
    private final ContinuousOutputPort _leftOut;
    private final ContinuousOutputPort _rightOut;

    private double _baseValue;    //  ranges from 1.0 (hard left) to 10.0 (hard right)
    private double _leftScalar;
    private double _rightScalar;

    FixedPanningModule() {
        _signalIn = new ContinuousInputPort();
        _leftOut = new ContinuousOutputPort();
        _rightOut = new ContinuousOutputPort();

        _inputPorts.put(SIGNAL_INPUT_PORT, _signalIn);
        _outputPorts.put(LEFT_OUTPUT_PORT, _leftOut);
        _outputPorts.put(RIGHT_OUTPUT_PORT, _rightOut);

        setBaseValue(0.0f);
    }

    @Override
    public void advance() {
        double signal = _signalIn.getValue();
        _leftOut.setCurrentValue(_leftScalar * signal);
        _rightOut.setCurrentValue(_rightScalar * signal);
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
        return ModuleType.FixedPanner;
    }

    /**
     * Rescales the input value with the standard koala range,
     * to the domain of log10() which produces a range of 0.0 to 1.0 (i.e., 1.0 to 10.0).
     */
    private double rescale(
        final double value
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
        final double value
    ) {
        if (value > Koala.MAX_CVPORT_VALUE) {
            _baseValue = Koala.MAX_CVPORT_VALUE;
        } else {
            _baseValue = Math.max(value, Koala.MIN_CVPORT_VALUE);
        }

        double rightTemp = rescale(_baseValue);
        double leftTemp = 11.0f - rightTemp;
        _leftScalar = Math.log10(leftTemp);
        _rightScalar = Math.log10(rightTemp);
    }
}
