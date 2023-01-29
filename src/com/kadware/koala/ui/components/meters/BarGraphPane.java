/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BarGraphPane extends GraphPane {

    private final Rectangle _rectangle;

    public BarGraphPane(
        final DoubleRange range,
        final OrientationType orientation,
        final Color color
    ) {
        super(range, orientation, color);

        _rectangle = new Rectangle();
        _rectangle.setFill(color.brighter());
        getChildren().add(_rectangle);
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        switch (getOrientation()) {
            case HORIZONTAL -> {
                _rectangle.setLayoutX(0.0);
                _rectangle.setLayoutY(0.0);
                _rectangle.setHeight(height);
                _rectangle.setWidth(0.0);
            }
            case VERTICAL -> {
                _rectangle.setLayoutX(0.0);
                _rectangle.setLayoutY(0.0);
                _rectangle.setHeight(0.0);
                _rectangle.setWidth(width);
            }
        }
    }

    @Override
    public void setValue(double value) {
        switch (getOrientation()) {
            case HORIZONTAL -> {
                _rectangle.setWidth(getXCoordinateFor(value));
            }
            case VERTICAL -> {
                var y = getYCoordinateFor(value);
                _rectangle.setLayoutY(y);
                _rectangle.setHeight(getPrefHeight() - y);
            }
        }
    }
}
