/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.Koala;

/**
 * Describes an input or output port with continuous values ranging from a pre-set minimum to pre-set maximum value.
 * Continuous ports have built-in scalar (multiplier) values to automatically adjust any input or output value.
 * The reported output value will never be less than the pre-set minimum, nor greater than the pre-set maximum,
 * but if adjustment is necessary, the overload flag will be set.
 */
public abstract class ContinuousPort extends Port {

    private final DoubleRange _range;
    private boolean _overload = false;

    protected ContinuousPort(
        final DoubleRange range
    ) {
        _range = range;
    }

    protected ContinuousPort() {
        this(Koala.BIPOLAR_RANGE);
    }

    public final void clearOverload() {
        _overload = false;
    }

    public final DoubleRange getRange() {
        return _range;
    }

    public final boolean getOverload() {
        return _overload;
    }

    protected final void setOverload() {
        _overload = true;
    }

    @Override
    public void reset() {
        clearOverload();
    }
}
