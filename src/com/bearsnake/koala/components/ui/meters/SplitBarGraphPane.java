/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.meters;

import com.bearsnake.koala.DoubleRange;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A bar graph which is split at 0.0 (midpoint).
 * The bar rises from mid-point for values >= 0.5, and descends below mid-point for values < 0.5.
 * For horizontal meters, left is for values < 0.5, right is for values >= 0.5
 */
public class SplitBarGraphPane extends GraphPane {

    private final Rectangle _rectangle;

    public SplitBarGraphPane(
        final DoubleRange range,
        final OrientationType orientation,
        final Color color
    ) {
        super(range, orientation, color);

        _rectangle = new Rectangle();
        _rectangle.setFill(color);
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
                _rectangle.setLayoutY(0.0);
                _rectangle.setHeight(height);
            }
            case VERTICAL -> {
                _rectangle.setLayoutX(0.0);
                _rectangle.setWidth(width);
            }
        }
    }

    @Override
    public void setValue(
        final double value
    ) {
        switch (getOrientation()) {
            case HORIZONTAL -> {
                var xPoint = getXCoordinateFor(value);
                var xMid = getPrefWidth() / 2;
                if (xPoint < xMid) {
                    _rectangle.setLayoutX(xPoint);
                    _rectangle.setWidth(xMid - xPoint);
                } else {
                    _rectangle.setLayoutX(xMid);
                    _rectangle.setWidth(xPoint - xMid);
                }
            }
            case VERTICAL -> {
                var yPoint = getYCoordinateFor(value);
                var yMid = getPrefHeight() / 2;
                if (yPoint < yMid) {
                    _rectangle.setLayoutY(yPoint);
                    _rectangle.setHeight(yMid - yPoint);
                } else {
                    _rectangle.setLayoutY(yMid);
                    _rectangle.setHeight(yPoint - yMid);
                }
            }
        }
    }
}
