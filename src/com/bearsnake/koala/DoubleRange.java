/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

public class DoubleRange extends Range<Double> {

    private final double _delta;

    public DoubleRange(
        final Double lowValue,
        final Double highValue
    ) {
        super(lowValue, highValue);
        _delta = highValue - lowValue;
    }

    /**
     * Ensures that a given value is limited such that it falls within the defined low and high values
     * for this range.
     * @param value input value
     * @return resulting value adjusted if, and as, necessary
     */
    public double clipValue(
        final double value
    ) {
        return Math.max(Math.min(value, getHighValue()), getLowValue());
    }

    /**
     *  Converts an input value, scaled according to this range, to a value between 0.0 and 1.0
     */
    public double normalizeValue(
        final double value
    ) {
        return (value - getLowValue()) / _delta;
    }

    /**
     * Converts an input value linearly from 0.0 to 1.0 to a corresponding value within the range
     * such that an input of 0.0 produces an output of lowValue, while an input of 1.0 produces
     * highValue.
     */
    public double scaleValue(
        final double value
    ) {
        return (value * _delta) + getLowValue();
    }

    /**
     * Retrieves the difference of the high and low values.
     */
    public double getDelta() { return _delta; }

    public boolean isInRange(
        final double value
    ) {
        return (value >= getLowValue()) && (value <= getHighValue());
    }
}
