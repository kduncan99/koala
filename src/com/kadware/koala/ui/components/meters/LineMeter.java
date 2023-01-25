/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class LineMeter extends Meter {

    private final Line _line;

    public LineMeter(
        final PixelDimensions dimensions,
        final Range range,
        final Orientation orientation,
        final Scalar scalar,
        final Color color,
        final GradientPane gradientPane
    ) {
        super(dimensions, range, orientation, scalar, color, gradientPane);

        _line = new Line();
        _line.setStroke(color.brighter());
        _line.setStartX(0);
        _line.setEndX(getGraphPane().getPrefWidth());
        _line.setStartY(0);
        _line.setEndY(getGraphPane().getPrefHeight());
        getGraphPane().getChildren().add(_line);
    }

    @Override
    public void setValue(double value) {
        switch (getOrientation()) {
            case HORIZONTAL -> {
                var x = getOrientation().getGraphCenterPointX(getGraphPane(), getRange(), getScalar(), value);
                _line.setStartX(x);
                _line.setEndX(x);
            }
            case VERTICAL -> {
                var y = getOrientation().getGraphCenterPointY(getGraphPane(), getRange(), getScalar(), value);
                _line.setStartY(y);
                _line.setEndY(y);
            }
        }
    }
}
