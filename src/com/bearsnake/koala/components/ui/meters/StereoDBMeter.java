/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.meters;

import com.bearsnake.koala.Koala;
import com.bearsnake.koala.PixelDimensions;
import javafx.scene.paint.Color;

public class StereoDBMeter extends Meter {

    private static final String LABEL_FORMAT = "%3.0f";
    private static final double[] LABEL_POINTS = { 0.0, -3.0, -6.0, -12.0, -18.0, -24.0, -30.0 };
    private static final double[] TICK_POINTS = { -30.0, -27.0, -24.0, -21.0, -18.0, -15.0, -12.0, -9.0, -6.0, -3.0, -0.0 };

    public StereoDBMeter(
        final PixelDimensions dimensions,
        final OrientationType orientation,
        final Color color
    ) {
        super(dimensions,
              orientation,
              GradientPane.createGradientPane(Koala.DBFS_RANGE, orientation, color, TICK_POINTS, LABEL_POINTS, LABEL_FORMAT),
              new StereoDBGraphPane(orientation, color));
    }

    public void setValues(
        final double left,
        final double right
    ) {
        ((StereoDBGraphPane) getGraphPane()).setValues(left, right);
    }
}
