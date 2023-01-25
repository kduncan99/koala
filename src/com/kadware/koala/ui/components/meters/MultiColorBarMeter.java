/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Pair;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class MultiColorBarMeter extends Meter {

    private static class GraphRegion {

        public final Rectangle _rectangle;
        public final Range _valueRange;
        public final Range _graphRange;

        public GraphRegion(
            final Rectangle rectangle,
            final Range valueRange,
            final Range graphRange
        ) {
            _rectangle = rectangle;
            _valueRange = valueRange;
            _graphRange = graphRange;
        }

        @Override
        public String toString() {
            return _rectangle.toString() + " values:" + _valueRange + " graphValues:" + _graphRange;
        }
    }

    private final GraphRegion[] _regions;
    private final Pair<Double, Color>[] _splitPoints;

    public MultiColorBarMeter(
        final PixelDimensions dimensions,
        final Range range,
        final Orientation orientation,
        final Scalar scalar,
        final Color color,
        final Pair<Double, Color>[] splitPoints,
        final GradientPane gradientPane
    ) {
        super(dimensions, range, orientation, scalar, color, gradientPane);
        _splitPoints = splitPoints;

        //  build regions from split points.
        //  we ignore the value member of the first split point, as it *should* (and must) be range.lowValue
        var gpWidth = getGraphPane().getPrefWidth();
        var gpHeight = getGraphPane().getPrefHeight();
        _regions = new GraphRegion[splitPoints.length];
        for (var spx = 0; spx < splitPoints.length; ++spx) {
            var sp = splitPoints[spx];

            var rect = new Rectangle();
            rect.setFill(sp.getRightValue());

            var lowValue = sp.getLeftValue();
            var highValue =
                (spx == splitPoints.length - 1) ? range.getHighValue() : splitPoints[spx + 1].getLeftValue();
            var valueRange = new Range(lowValue, highValue);

            var graphRange = (Range)null;
            switch (orientation) {
                case HORIZONTAL -> {
                    var low = orientation.getGraphCenterPointX(getGraphPane(), range, scalar, lowValue);
                    var high = orientation.getGraphCenterPointX(getGraphPane(), range, scalar, highValue);
                    graphRange = new Range(low, high);
                    rect.setHeight(gpHeight);
                    rect.setLayoutX(low);
                    rect.setLayoutY(0);
                }
                case VERTICAL -> {
                    var low = orientation.getGraphCenterPointY(getGraphPane(), range, scalar, lowValue);
                    var high = orientation.getGraphCenterPointY(getGraphPane(), range, scalar, highValue);
                    graphRange = new Range(low, high);
                    rect.setWidth(gpWidth);
                    rect.setLayoutX(0);
                    rect.setLayoutY(gpHeight - low);
                }
            }

            _regions[spx] = new GraphRegion(rect, valueRange, graphRange);
            System.out.println(_regions[spx]);//TODO remove
        }

        Arrays.stream(_regions).forEach(r -> getGraphPane().getChildren().add(r._rectangle));
    }

    @Override
    public void setValue(double value) {
        switch (getOrientation()) {
            case HORIZONTAL -> {
                for (var r : _regions) {
                    if (value < r._valueRange.getLowValue()) {
                        //  the value is lower than this region's range - make the rectangle empty
                        r._rectangle.setWidth(0);
                    } else if (value <= r._valueRange.getHighValue()) {
                        //  the value is within this region's range - make a partial rectangle
                        var x = getOrientation().getGraphCenterPointX(getGraphPane(), getRange(), getScalar(), value);
                        r._rectangle.setWidth(x - r._graphRange.getLowValue() + 1);
                    } else {
                        //  the value is greater than this region's range - make the rectangle full size
                        r._rectangle.setWidth(r._graphRange.getDelta());
                    }
                }
            }
            case VERTICAL -> {
                var y = getOrientation().getGraphCenterPointY(getGraphPane(), getRange(), getScalar(), value);
                //TODO
            }
        }
    }
}
