/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is a bar graph, but the bar is segregated into multiple differently-colored segments.
 * It is useful for indicators which need to show green for safe values, yellow for caution,
 * and red for danger (as an example).
 */
public class MultiColorGraphPane extends GraphPane {

    private static class Region {
        public double _lowRangedValue;
        public double _highRangedValue;
        public int _lowCoordinate;      //  x- or y- coordinate matching _lowRangedValue
        public int _highCoordinate;     //  x- or y- coordinate matching _highRangedValue
        public int _fullWidth;
        public int _fullHeight;
        public Rectangle _rectangle;
    }

    private final Region[] _regions;

    public MultiColorGraphPane(
        final DoubleRange range,
        final OrientationType orientation,
        final Color color,                  //  used for gradient color, and graph background
        final double[] splitPoints,         //  the split points between the colors. These are values relative to the range.
        final Color[] colors                //  the colors to be used (length should be +1 than splitPoints length)
    ) {
        super(range, orientation, color);

        if (colors.length != splitPoints.length + 1)
            throw new RuntimeException("splitPoints/colors array length contradiction");

        _regions = new Region[colors.length];
        for (int rx = 0; rx < _regions.length; ++rx)
            _regions[rx] = new Region();
        _regions[0]._lowRangedValue = range.getLowValue();
        _regions[_regions.length - 1]._highRangedValue = range.getHighValue();

        var xLowRegion = 0;
        var xHighRegion = 1;
        for (var splitPoint : splitPoints) {
            _regions[xLowRegion]._highRangedValue = splitPoint;
            _regions[xHighRegion]._lowRangedValue = splitPoint;
            xLowRegion++;
            xHighRegion++;
        }

        for (var rx = 0; rx < _regions.length; ++rx) {
            var rect = new Rectangle();
            rect.setFill(colors[rx]);
            getChildren().add(rect);
            _regions[rx]._rectangle = rect;
        }
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        for (var r : _regions) {
            switch (getOrientation()) {
                case HORIZONTAL -> {
                    r._lowCoordinate = (int)getXCoordinateFor(r._lowRangedValue);
                    r._highCoordinate = (int)getXCoordinateFor(r._highRangedValue);
                    r._fullWidth = r._highCoordinate - r._lowCoordinate;
                    r._fullHeight = (int)height;

                    r._rectangle.setLayoutX(r._lowCoordinate);
                    r._rectangle.setLayoutY(0);
                    r._rectangle.setWidth(0);
                    r._rectangle.setHeight(height);
                }
                case VERTICAL -> {
                    r._lowCoordinate = (int)getYCoordinateFor(r._lowRangedValue);
                    r._highCoordinate = (int)getYCoordinateFor(r._highRangedValue);
                    r._fullWidth = (int)width;
                    r._fullHeight = r._lowCoordinate - r._highCoordinate;

                    r._rectangle.setLayoutX(0);
                    r._rectangle.setLayoutY(0);
                    r._rectangle.setWidth(width);
                    r._rectangle.setHeight(0);
                }
            }
        }
    }

    @Override
    public void setValue(double value) {
        switch (getOrientation()) {
            case HORIZONTAL -> {
                var xPos = getXCoordinateFor(value);
                for (var r : _regions) {
                    if (value < r._lowRangedValue) {
                        //  value does not reach this region - zero it out
                        r._rectangle.setWidth(0);
                    } else if (value <= r._highRangedValue) {
                        //  value is somewhere inside this region
                        r._rectangle.setWidth(xPos - r._rectangle.getLayoutX());
                    } else {
                        //  value is beyond this region - make it full
                        r._rectangle.setWidth(r._fullWidth);
                    }
                }
            }
            case VERTICAL -> {
                //  don't forget - we're upside down
                var yPos = getYCoordinateFor(value);
                for (var r : _regions) {
                    if (value < r._lowRangedValue) {
                        //  value does not reach this region - zero it out
                        r._rectangle.setHeight(0);
                    } else if (value <= r._highRangedValue) {
                        //  value is somewhere inside this region
                        r._rectangle.setLayoutY(yPos);
                        r._rectangle.setHeight(r._lowCoordinate - yPos);
                    } else {
                        //  value is beyond this region - make it full
                        r._rectangle.setLayoutY(r._highCoordinate);
                        r._rectangle.setHeight(r._fullHeight);
                    }
                }
            }
        }
    }
}
