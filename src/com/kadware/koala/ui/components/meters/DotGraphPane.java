/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.Koala;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DotGraphPane extends GraphPane {

    private final Circle _circle;

    public DotGraphPane(
        final DoubleRange range,
        final OrientationType orientation,
        final Color color
    ) {
        super(range, orientation, color);

        _circle = new Circle();
        _circle.setFill(color.brighter());
        getChildren().add(_circle);
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        _circle.setRadius(Math.min(width, height) / 4);
        _circle.setCenterX(width / 2);
        _circle.setCenterY(height / 2);
    }

    @Override
    public void setValue(double value) {
        switch (getOrientation()) {
            case HORIZONTAL -> _circle.setCenterX(getXCoordinateFor(value));
            case VERTICAL -> _circle.setCenterY(getYCoordinateFor(value));
        }
    }
}
