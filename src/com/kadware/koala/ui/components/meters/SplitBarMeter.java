/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class SplitBarMeter extends Meter {

    private final Rectangle _rectangle;
    private final double _splitPoint;
    private final double _graphSplitPoint;

    public SplitBarMeter(
        final PixelDimensions dimensions,
        final Range range,
        final Orientation orientation,
        final Scalar scalar,
        final Color color,
        final double splitPoint,
        final GradientPane gradientPane
    ) {
        super(dimensions, range, orientation, scalar, color, gradientPane);
        _splitPoint = splitPoint;
        _graphSplitPoint = switch(orientation) {
            case HORIZONTAL -> getOrientation().getGraphCenterPointX(getGraphPane(), range, scalar, splitPoint);
            case VERTICAL -> getOrientation().getGraphCenterPointY(getGraphPane(), range, scalar, splitPoint);
        };

        _rectangle = new Rectangle();
        _rectangle.setWidth(getGraphPane().getPrefWidth());
        _rectangle.setHeight(getGraphPane().getPrefHeight());
        _rectangle.setFill(color);
        getGraphPane().getChildren().add(_rectangle);
    }

    @Override
    public void setValue(double value) {
        switch (getOrientation()) {
            case HORIZONTAL -> {
                var x = getOrientation().getGraphCenterPointX(getGraphPane(), getRange(), getScalar(), value);
                if (value < _splitPoint) {
                    _rectangle.setLayoutX(x);
                    _rectangle.setWidth(_graphSplitPoint - x - 1);
                } else if (value > _splitPoint) {
                    _rectangle.setLayoutX(_graphSplitPoint);
                    _rectangle.setWidth(x - _graphSplitPoint);
                }
            }
            case VERTICAL -> {
                var y = getOrientation().getGraphCenterPointY(getGraphPane(), getRange(), getScalar(), value);
                if (value < _splitPoint) {
                    _rectangle.setLayoutY(_graphSplitPoint);
                    _rectangle.setHeight(y - _graphSplitPoint);
                } else if (value > _splitPoint) {
                    _rectangle.setLayoutY(y);
                    _rectangle.setHeight(_graphSplitPoint - y);
                }
            }
        }
    }
}
