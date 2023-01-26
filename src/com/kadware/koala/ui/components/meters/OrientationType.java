/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.DoubleRange;
import javafx.scene.layout.GridPane;

public enum OrientationType {
    HORIZONTAL(new HorizontalOrientation()),
    VERTICAL(new VerticalOrientation());

    private final BaseOrientation _orientationInstance;

    OrientationType(
        final BaseOrientation orientationInstance
    ) {
        _orientationInstance = orientationInstance;
    }

    //  convenience methods
    public void applyOrientation(
        final GridPane parent,
        final GraphPane graphPane,
        final GradientPane gradientPane
    ) {
        _orientationInstance.applyOrientation(parent, graphPane, gradientPane);
    }

    public void drawGradient(
        final GradientPane gradientPane,
        final Scalar scalar
    ) {
        _orientationInstance.drawGradient(gradientPane, scalar);
    }

    public double getGraphCenterPointX(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final Scalar scalar,
        final double value
    ) {
        return _orientationInstance.getGraphCenterPointX(dimensions, range, scalar, value);
    }

    public double getGraphCenterPointY(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final Scalar scalar,
        final double value
    ) {
        return _orientationInstance.getGraphCenterPointY(dimensions, range, scalar, value);
    }
}
