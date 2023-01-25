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
public class BarMeter extends Meter {

    private final Rectangle _rectangle;

    public BarMeter(
        final PixelDimensions dimensions,
        final Range range,
        final Orientation orientation,
        final Scalar scalar,
        final Color color,
        final GradientPane gradientPane
    ) {
        super(dimensions, range, orientation, scalar, color, gradientPane);

        _rectangle = new Rectangle();
        _rectangle.setFill(color.brighter());
        _rectangle.setWidth(dimensions.getWidth());
        _rectangle.setHeight(dimensions.getHeight());
        getGraphPane().getChildren().add(_rectangle);
    }

    @Override
    public void setValue(double value) {
        switch (getOrientation()) {
            case HORIZONTAL -> {
                var x = getOrientation().getGraphCenterPointX(getGraphPane(), getRange(), getScalar(), value);
//                System.out.printf("%f -> %f\n", value, x);//TODO remove
                _rectangle.setWidth(x);
            }
            case VERTICAL -> {
                var y = getOrientation().getGraphCenterPointY(getGraphPane(), getRange(), getScalar(), value);
                _rectangle.setLayoutY(y);
                _rectangle.setHeight(y);
            }
        }
    }
}
