/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import javafx.scene.shape.Circle;

public class DotGraphPane extends GraphPane {

    private final Circle _circle;

    public DotGraphPane(
        final GraphParams params
    ) {
        super(params);

        _circle = new Circle();
        _circle.setFill(params.getColor().brighter());

        getChildren().add(_circle);
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        _circle.setRadius(Math.min(width, height) / 4);
    }

    @Override
    public void setValue(double value) {
        var ot = getParams().getOrientationType();
        var dim = new PixelDimensions((int)getPrefWidth(), (int)getPrefHeight());
        var x = ot.getGraphCenterPointX(dim,
                                        getParams().getRange(),
                                        getParams().getScalar(),
                                        value);
        var y = ot.getGraphCenterPointY(dim,
                                        getParams().getRange(),
                                        getParams().getScalar(),
                                        value);
        _circle.setCenterX(x);
        _circle.setCenterY(y);
    }
}
