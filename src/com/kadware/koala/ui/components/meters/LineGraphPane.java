/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class LineGraphPane extends GraphPane {

    private final Line _line;

    public LineGraphPane(
        final GraphParams params
    ) {
        super(params);

        _line = new Line();
        _line.setStroke(params.getColor().brighter());
        _line.setStartX(0);
        _line.setEndX(params.getDimensions().getWidth());
        _line.setStartY(0);
        _line.setEndY(params.getDimensions().getHeight());

        getChildren().add(_line);
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
                _line.setStartX(x);
                _line.setEndX(x);
            }
            case VERTICAL -> {
                var y = ot.getGraphCenterPointY(dim,
                                                getParams().getRange(),
                                                getParams().getScalar(),
                                                value);
                _line.setStartY(y);
                _line.setEndY(y);
            }
        }
    }
}
