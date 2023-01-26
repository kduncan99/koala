/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import javafx.scene.shape.Rectangle;

public class SplitBarGraphPane extends GraphPane {

    private final Rectangle _rectangle;
    private final double _splitPoint;
    private final double _graphSplitPoint;

    public SplitBarGraphPane(
        final SplitBarGraphParams params
    ) {
        super(params);

        _splitPoint = params.getSplitPoint();

        var ot = params.getOrientationType();
        _graphSplitPoint = switch(ot) {
            case HORIZONTAL -> ot.getGraphCenterPointX(params.getDimensions(),
                                                       params.getRange(),
                                                       params.getScalar(),
                                                       _splitPoint);
            case VERTICAL -> ot.getGraphCenterPointY(params.getDimensions(),
                                                     params.getRange(),
                                                     params.getScalar(),
                                                     _splitPoint);
        };

        _rectangle = new Rectangle();
        _rectangle.setWidth(params.getDimensions().getWidth());
        _rectangle.setHeight(params.getDimensions().getHeight());
        _rectangle.setFill(params.getColor());

        getChildren().add(_rectangle);
    }

    @Override
    public void setValue(double value) {
        var ot = getParams().getOrientationType();
        var dim = new PixelDimensions((int)getPrefWidth(), (int)getPrefHeight());
        switch (ot) {
            case HORIZONTAL -> {
                var x = ot.getGraphCenterPointX(dim,
                                                getParams().getRange(),
                                                getParams().getScalar(),
                                                value);
                if (value < _splitPoint) {
                    _rectangle.setLayoutX(x);
                    _rectangle.setWidth(_graphSplitPoint - x - 1);
                } else if (value > _splitPoint) {
                    _rectangle.setLayoutX(_graphSplitPoint);
                    _rectangle.setWidth(x - _graphSplitPoint);
                }
            }
            case VERTICAL -> {
                var y = ot.getGraphCenterPointY(dim,
                                                getParams().getRange(),
                                                getParams().getScalar(),
                                                value);
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
