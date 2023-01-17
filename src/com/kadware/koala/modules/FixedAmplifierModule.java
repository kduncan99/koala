/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

/**
 * Implements an amplifier (actually an attenuator) where the attenuation can be set, but not modulated.
 * Not likely to be used much as a main module, but useful as a submodule.
 */
public class FixedAmplifierModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int SIGNAL_OUTPUT_PORT = 1;

    private final ContinuousInputPort _signalIn;
    private final ContinuousOutputPort _signalOut;

    private float _baseValue = 5.0f;

    FixedAmplifierModule() {
        _signalIn = new ContinuousInputPort();
        _signalOut = new ContinuousOutputPort();
        _inputPorts.put(SIGNAL_INPUT_PORT, _signalIn);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _signalOut);
    }

    @Override
    public void advance() {
        float multiplier = (_baseValue - Koala.MIN_CVPORT_VALUE) / Koala.CVPORT_VALUE_RANGE;
        float signalOutValue = multiplier * _signalIn.getValue();
        _signalOut.setCurrentValue(signalOutValue);
    }

    @Override
    public void close() {}

    public double getBaseValue() {
        return _baseValue;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.FixedAmplifier;
    }

    @Override
    public void reset() {}

    public void setBaseValue(
        final float value
    ) {
        _baseValue = value;
    }
}
