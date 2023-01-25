/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Range;
import javafx.scene.layout.GridPane;

public enum Orientation {
    HORIZONTAL(new HorizontalOrientation()),
    VERTICAL(new VerticalOrientation());

    private final BaseOrientation _orientationInstance;

    Orientation(
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
        final GraphPane graphPane,
        final Range range,
        final Scalar scalar,
        final double value
    ) {
        return _orientationInstance.getGraphCenterPointX(graphPane, range, scalar, value);
    }

    public double getGraphCenterPointY(
        final GraphPane graphPane,
        final Range range,
        final Scalar scalar,
        final double value
    ) {
        return _orientationInstance.getGraphCenterPointY(graphPane, range, scalar, value);
    }
}
