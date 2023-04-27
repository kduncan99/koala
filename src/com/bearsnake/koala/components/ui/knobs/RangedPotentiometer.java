/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.knobs;

import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.PixelDimensions;
import javafx.scene.paint.Color;

/**
 * A subclass of Potentiometer which allows the invoker to use a range other than 0.0:1.0.
 */
public class RangedPotentiometer extends Potentiometer {

    private final DoubleRange _range;

    /**
     * Creates a RangedPotentiometer control
     * @param dimensions Pixel dimensions within which this knob must fit
     * @param color Base color of the knob - the face will this color while the edge and any other markings will be a
     *              darker version of the color.
     * @param range Range to use for setting and reporting position
     * @param initialPosition Initial position of the knob from 0.0 to 1.0
     */
    public RangedPotentiometer(
        final PixelDimensions dimensions,
        final Color color,
        final DoubleRange range,
        final double initialPosition
    ) {
        super(dimensions, color, range.normalizeValue(initialPosition));
        _range = range;
    }

    /**
     * Reports the current position of the RangedPotentiometer, relative to the provided range.
     * This is invoked by messaging, so that the messages sent by the superclass will have the
     * range-adjusted values.
     */
    public double getPosition() {
        return _range.scaleValue(super.getPosition());
    }

    /**
     * Sets the position of the potentiometer
     */
    public void setPosition(
        final double position
    ) {
        super.setPosition(_range.normalizeValue(position));
    }
}
