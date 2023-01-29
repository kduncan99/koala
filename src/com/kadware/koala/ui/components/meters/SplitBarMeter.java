/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.PixelDimensions;
import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class SplitBarMeter extends Meter {

    public SplitBarMeter(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final OrientationType orientation,
        final Color color,
        final double[] tickPoints,
        final double[] labelPoints,
        final String labelFormat
    ) {
        super(dimensions,
              orientation,
              GradientPane.createGradientPane(range, orientation, color, tickPoints, labelPoints, labelFormat),
              new SplitBarGraphPane(range, orientation, color));
    }
}
