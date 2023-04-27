/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.knobs;

import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.messages.components.RangedPotentiometerKnobComponentMessage;
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
     * @param range Range to use for setting and reporting scaledValue
     * @param initialSetting Initial setting of the knob, relative to the range
     */
    public RangedPotentiometer(
        final PixelDimensions dimensions,
        final Color color,
        final DoubleRange range,
        final double initialSetting
    ) {
        super(dimensions, color, range.normalizeValue(initialSetting));
        _range = range;
    }

    /**
     * Reports the current scaledValue of the RangedPotentiometer, relative to the provided range.
     * This is invoked by messaging, so that the messages sent by the superclass will have the
     * range-adjusted values.
     */
    public double getScaledValue() {
        return _range.scaleValue(getPosition());
    }

    /**
     * Sets the scaledValue of the potentiometer based on the given scaled value,
     * and sends a notification.
     * @param scaledValue value relative to the established range, by which the potentiometer is set
     */
    public void setScaledValue(
        final double scaledValue
    ) {
        setPositionSilently(_range.normalizeValue(scaledValue));
        notifyListeners(new RangedPotentiometerKnobComponentMessage(scaledValue));
    }
}
