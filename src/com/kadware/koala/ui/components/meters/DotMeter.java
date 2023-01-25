/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class DotMeter extends Meter {

    private final Circle _circle;

    public DotMeter(
        final PixelDimensions dimensions,
        final Range range,
        final Orientation orientation,
        final Scalar scalar,
        final Color color,
        final GradientPane gradientPane
    ) {
        super(dimensions, range, orientation, scalar, color, gradientPane);

        var maxRadius = Math.min(getGraphPane().getPrefWidth(), getGradientPane().getPrefHeight()) / 2.0;
        _circle = new Circle(maxRadius * 0.5);
        _circle.setFill(color.brighter());
        getGraphPane().getChildren().add(_circle);
    }

    @Override
    public void setValue(double value) {
        var x = getOrientation().getGraphCenterPointX(getGraphPane(), getRange(), getScalar(), value);
        var y = getOrientation().getGraphCenterPointY(getGraphPane(), getRange(), getScalar(), value);
        _circle.setCenterX(x);
        _circle.setCenterY(y);
    }
}
