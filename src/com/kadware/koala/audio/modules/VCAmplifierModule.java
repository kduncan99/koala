/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

/**
 * Basic value-controlled amplifier / attenuator.
 * If the control input is set linear, then the value ranges from -1.0 == unity inversion to 1.0 == unity gain.
 * Otherwise, the value ranges from -1.0 == 96db attenuation, to 1.0 == 96db amplification.
 */
public class VCAmplifierModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int CONTROL_INPUT_PORT = 1;
    public static final int SIGNAL_OUTPUT_PORT = 3;

    private final ContinuousInputPort _signalIn;
    private final ContinuousInputPort _controlIn;
    private final ContinuousOutputPort _signalOut;

    private boolean _isControlInputLinear = true;
    private double _baseValue = Koala.POSITIVE_RANGE.getHighValue();

    VCAmplifierModule() {
        _signalIn = new ContinuousInputPort();
        _controlIn = new ContinuousInputPort();
        _signalOut = new ContinuousOutputPort();
        _inputPorts.put(SIGNAL_INPUT_PORT, _signalIn);
        _inputPorts.put(CONTROL_INPUT_PORT, _controlIn);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _signalOut);
    }

    @Override
    public void advance() {
        double ctlIn = _controlIn.getValue();
        double multiplier = _isControlInputLinear ? ctlIn : Koala.dbScalarToMultiplier(ctlIn);
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

    public boolean isControlInputLinear() { return _isControlInputLinear; }

    /**
     * Sets the raw base value
     * @param value ranging from 0.0 for infinite attenuation, to 1.0 for unity gain.
     */
    public void setBaseValue(
        final double value
    ) {
        _baseValue = value;
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

    public void setIsControlInputLinear(
        final boolean value
    ) {
        _isControlInputLinear = value;
    }
}
