/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala;

public class Range {

    private final double _delta;
    private final double _lowValue;
    private final double _highValue;

    public Range(
        final double lowValue,
        final double highValue
    ) {
        _lowValue = lowValue;
        _highValue = highValue;
        _delta = highValue - lowValue;
    }

    public double clipValue(
        final double value
    ) {
        return Math.max(Math.min(value, _highValue), _lowValue);
    }

    public double normalizeValue(
        final double value
    ) {
        return (value - _lowValue) / _delta;
    }

    public double getLowValue() { return _lowValue; }
    public double getHighValue() { return _highValue; }
    public double getDelta() { return _delta; }

    public boolean isInRange(
        final double value
    ) {
        return (value >= _lowValue) && (value <= _highValue);
    }

    @Override
    public String toString() {
        return "[" + _lowValue + ":" + _highValue + "::" + _delta + "]";
    }
}
