/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.Koala;

/**
 * Describes an input or output port with continuous values ranging from a pre-set
 * minimum to pre-set maximum value.
 * Continuous ports have built-in scalar (multiplier) values to automatically
 * adjust any input or output value.
 * The reported output value will never be less than the pre-set minimum,
 * nor greater than the pre-set maximum, but if adjustment is necessary, the overload flag will be set.
 */
public abstract class ContinuousPort extends Port {

    float _maximumValue;
    float _minimumValue;
    float _multiplier;
    boolean _overload = false;

    public ContinuousPort(
        final String name,
        final String abbreviation,
        final float minimumValue,
        final float maximumValue,
        final float multiplier
    ) {
        super(name, abbreviation);
        _maximumValue = maximumValue;
        _minimumValue = minimumValue;
        _multiplier = multiplier;
    }

    public ContinuousPort(
        final String name,
        final String abbreviation
    ) {
        super(name, abbreviation);
        _maximumValue = Koala.MAX_CVPORT_VALUE;
        _minimumValue = Koala.MIN_CVPORT_VALUE;
        _multiplier = 1.0f;
    }

    public final void clearOverload() {
        _overload = false;
    }

    public final float getMaximumValue() {
        return _minimumValue;
    }

    public final float getMinimumValue() {
        return _minimumValue;
    }

    public final float getMultiplier() {
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
        final float value
    ) {
        _multiplier = value;
    }
}
