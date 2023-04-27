/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

public class IntegerRange extends Range<Integer> {

    private final int _delta;

    public IntegerRange(
        final Integer lowValue,
        final Integer highValue
    ) {
        super(lowValue, highValue);
        _delta = highValue - lowValue;
    }

    public int clipValue(
        final int value
    ) {
        return Math.max(Math.min(value, getHighValue()), getLowValue());
    }

    /**
     *  Converts an input value, scaled according to this range, to a value between 0.0 and 1.0
     */
    public int normalizeValue(
        final int value
    ) {
        return (value - getLowValue()) / _delta;
    }

    public int getDelta() { return _delta; }

    public boolean isInRange(
        final int value
    ) {
        return (value >= getLowValue()) && (value <= getHighValue());
    }
}
