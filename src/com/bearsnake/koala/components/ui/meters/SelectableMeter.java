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
 * This meter has four different graphs, displaying any one at a time.
 */
public class SelectableMeter extends Meter {

    public SelectableMeter(
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
              new SelectableGraphPane(range, orientation, color));
    }
}
