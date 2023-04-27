/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.meters;

import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.PixelDimensions;
import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class MultiColorBarMeter extends Meter {

    public MultiColorBarMeter(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final OrientationType orientation,
        final Color color,
        final double[] tickPoints,
        final double[] labelPoints,
        final String labelFormat,
        final double[] splitPoints,         //  the split points between the colors. These are values relative to the range.
        final Color[] colors                //  the colors to be used (length should be +1 than splitPoints length)
    ) {
        super(dimensions,
              orientation,
              GradientPane.createGradientPane(range, orientation, color, tickPoints, labelPoints, labelFormat),
              new MultiColorGraphPane(range, orientation, color, splitPoints, colors));
    }
}
