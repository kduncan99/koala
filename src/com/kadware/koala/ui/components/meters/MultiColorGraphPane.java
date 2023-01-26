/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Pair;
import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class MultiColorGraphPane extends GraphPane {

    private static class GraphRegion {

        public final Rectangle _rectangle;
        public final DoubleRange _valueRange;
        public final DoubleRange _graphRange;

        public GraphRegion(
            final Rectangle rectangle,
            final DoubleRange valueRange,
            final DoubleRange graphRange
        ) {
            _rectangle = rectangle;
            _valueRange = valueRange;
            _graphRange = graphRange;
        }
    }

    private final GraphRegion[] _regions;
    private final Pair<Double, Color>[] _splitPoints;

    public MultiColorGraphPane(
        final MultiColorGraphParams params
    ) {
        super(params);

        _splitPoints = params.getSplitPoints();

        //  build regions from split points.
        //  we ignore the value member of the first split point, as it *should* (and must) be range.lowValue
        var gpWidth = params.getDimensions().getWidth();
        var gpHeight = params.getDimensions().getHeight();

        _regions = new GraphRegion[_splitPoints.length];
        for (var spx = 0; spx < _splitPoints.length; ++spx) {
            var sp = _splitPoints[spx];

            var rect = new Rectangle();
            rect.setFill(sp.getRightValue());

            var lowValue = sp.getLeftValue();
            var highValue =
                (spx == _splitPoints.length - 1)
                    ? params.getRange().getHighValue()
                    : _splitPoints[spx + 1].getLeftValue();
            var valueRange = new DoubleRange(lowValue, highValue);

            var graphRange = (DoubleRange)null;
            var ot = params.getOrientationType();
            switch (ot) {
                case HORIZONTAL -> {
                    var low = ot.getGraphCenterPointX(params.getDimensions(),
                                                      params.getRange(),
                                                      params.getScalar(),
                                                      lowValue);
                    var high = ot.getGraphCenterPointX(params.getDimensions(),
                                                       params.getRange(),
                                                       params.getScalar(),
                                                       highValue);
                    graphRange = new DoubleRange(low, high);
                    rect.setHeight(gpHeight);
                    rect.setLayoutX(low);
                    rect.setLayoutY(0);
                }
                case VERTICAL -> {
                    var low = ot.getGraphCenterPointY(params.getDimensions(),
                                                      params.getRange(),
                                                      params.getScalar(),
                                                      lowValue);
                    var high = ot.getGraphCenterPointY(params.getDimensions(),
                                                       params.getRange(),
                                                       params.getScalar(),
                                                       highValue);
                    graphRange = new DoubleRange(low, high);
                    rect.setWidth(gpWidth);
                    rect.setLayoutX(0);
                    rect.setLayoutY(gpHeight - low);
                }
            }

            _regions[spx] = new GraphRegion(rect, valueRange, graphRange);
        }

        Arrays.stream(_regions).forEach(r -> getChildren().add(r._rectangle));
    }

    @Override
    public void setValue(double value) {
        var ot = getParams().getOrientationType();
        switch (ot) {
            case HORIZONTAL -> {
                for (var r : _regions) {
                    if (value < r._valueRange.getLowValue()) {
                        //  the value is lower than this region's range - make the rectangle empty
                        r._rectangle.setWidth(0);
                    } else if (value <= r._valueRange.getHighValue()) {
                        //  the value is within this region's range - make a partial rectangle
                        var x = ot.getGraphCenterPointX(getParams().getDimensions(),
                                                        getParams().getRange(),
                                                        getParams().getScalar(),
                                                        value);
                        r._rectangle.setWidth(x - r._graphRange.getLowValue() + 1);
                    } else {
                        //  the value is greater than this region's range - make the rectangle full size
                        r._rectangle.setWidth(r._graphRange.getDelta());
                    }
                }
            }
            case VERTICAL -> {
                for (var r : _regions) {
                    if (value < r._valueRange.getLowValue()) {
                        //  the value is lower than this region's range - make the rectangle empty
                        r._rectangle.setHeight(0);
                    } else if (value <= r._valueRange.getHighValue()) {
                        //  the value is within this region's range - make a partial rectangle
                        var y = ot.getGraphCenterPointY(getParams().getDimensions(),
                                                        getParams().getRange(),
                                                        getParams().getScalar(),
                                                        value);
                        r._rectangle.setHeight(y - r._graphRange.getLowValue() + 1);
                    } else {
                        //  the value is greater than this region's range - make the rectangle full size
                        r._rectangle.setHeight(r._graphRange.getDelta());
                    }
                }
            }
        }
    }
}
