/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

/**
 * Implements a fixed (non CV-controllable) pan module.
 * Intended primarily as a submodule, but possibly useful elsewhere.
 */
public class FixedPanningModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int LEFT_OUTPUT_PORT = 1;
    public static final int RIGHT_OUTPUT_PORT = 2;

    private final ContinuousInputPort _signalIn;
    private final ContinuousOutputPort _leftOut;
    private final ContinuousOutputPort _rightOut;

    private double _baseValue;    //  ranges from -1.0 (hard left) to 1.0 (hard right)
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

    @Override
    public void reset() {}

    /**
     * Sets the base value for panning - -1.0 is hard left, 0.0 is center, +1.0 is hard right.
     * For scalar conversions, we use -96db for full attenuation.
     */
    public void setBaseValue(
        final double value
    ) {
        //  Set the base value, keeping it within accepted range.
        //  Apply complimentary linear multipliers such that the values vary between 0.0 and 1.0, as necessary.
        //  This will put them both at 0.5 in the 'middle' which will effectively attenuate both left and right
        //  signals by 3db, which is what we'd like from a panning control.
        _baseValue = Koala.BIPOLAR_RANGE.clipValue(value);
        _rightScalar = (_baseValue + 1.0) / 2.0;
        _leftScalar = 1.0 - _rightScalar;
    }
}
