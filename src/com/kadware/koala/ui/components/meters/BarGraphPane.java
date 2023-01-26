/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import javafx.scene.shape.Rectangle;

public class BarGraphPane extends GraphPane {

    private final Rectangle _rectangle;

    public BarGraphPane(
        final GraphParams params
    ) {
        super(params);

        _rectangle = new Rectangle();
        _rectangle.setWidth(params.getDimensions().getWidth());
        _rectangle.setHeight(params.getDimensions().getHeight());
        _rectangle.setFill(params.getColor().brighter());

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
                _rectangle.setWidth(x);
            }
            case VERTICAL -> {
                var y = ot.getGraphCenterPointY(dim,
                                                getParams().getRange(),
                                                getParams().getScalar(),
                                                value);
                _rectangle.setLayoutY(y);
                _rectangle.setHeight(y);
            }
        }
    }
}
