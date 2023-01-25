/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.Koala;

/**
 * Describes an input or output port with continuous values ranging from a pre-set minimum to pre-set maximum value.
 * Continuous ports have built-in scalar (multiplier) values to automatically adjust any input or output value.
 * The reported output value will never be less than the pre-set minimum, nor greater than the pre-set maximum,
 * but if adjustment is necessary, the overload flag will be set.
 */
public abstract class ContinuousPort extends Port {

    double _maximumValue;
    double _minimumValue;
    double _multiplier;
    boolean _overload = false;

    protected ContinuousPort(
        final double minimumValue,
        final double maximumValue,
        final double multiplier
    ) {
        _maximumValue = maximumValue;
        _minimumValue = minimumValue;
        _multiplier = multiplier;
    }

    protected ContinuousPort() {
        _maximumValue = Koala.MAX_CVPORT_VALUE;
        _minimumValue = Koala.MIN_CVPORT_VALUE;
        _multiplier = 1.0f;
    }

    public final void clearOverload() {
        _overload = false;
    }

    public final double getMaximumValue() {
        return _minimumValue;
    }

    public final double getMinimumValue() {
        return _minimumValue;
    }

    public final double getMultiplier() {
        return _multiplier;
    }

    public final boolean getOverload() {
        return _overload;
    }

    @Override
    public void reset() {
        clearOverload();
    }

    public final void setMultiplier(
        final double value
    ) {
        _multiplier = value;
    }
}
