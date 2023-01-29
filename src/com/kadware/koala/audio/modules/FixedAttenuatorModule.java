/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

/**
 * Implements an attenuator where the attenuation can be set, but not modulated.
 * Not likely to be used much as a main module, but useful as a submodule.
 */
public class FixedAttenuatorModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int SIGNAL_OUTPUT_PORT = 1;

    private final ContinuousInputPort _signalIn;
    private final ContinuousOutputPort _signalOut;

    //  _baseValue is a linear scalar, such that 0 gives us infinite attenuation, 0.5 gives us half-volume,
    //  and 1.0 gives us unity.
    private double _baseValue = Koala.POSITIVE_RANGE.getHighValue();

    FixedAttenuatorModule() {
        _signalIn = new ContinuousInputPort();
        _signalOut = new ContinuousOutputPort();
        _inputPorts.put(SIGNAL_INPUT_PORT, _signalIn);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _signalOut);
    }

    @Override
    public void advance() {
        _signalOut.setCurrentValue(_baseValue * _signalIn.getValue());
    }

    @Override
    public void close() {}

    public double getBaseValue() {
        return _baseValue;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.FixedAttenuator;
    }

    @Override
    public void reset() {}

    /**
     * Sets the raw base value
     * @param value ranging from 0.0 for infinite attenuation, to 1.0 for unity gain.
     */
    public void setBaseValue(
        final double value
    ) {
        _baseValue = Koala.POSITIVE_RANGE.clipValue(value);
    }

    /**
     * Sets the base value scalar according to desired attenuation or amplification given in (db / 96).
     * @param dbScalar ranges from -1.0 for 96db attenuation, to 0.0 for unity gain.
     */
    public void setBaseValueDB(
        final double dbScalar
    ) {
        _baseValue = Koala.POSITIVE_RANGE.clipValue(Koala.dbScalarToMultiplier(dbScalar));
    }
}
