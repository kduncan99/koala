/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineGraphPane extends GraphPane {

    private final Line _line;

    public LineGraphPane(
        final DoubleRange range,
        final OrientationType orientation,
        final Color color
    ) {
        super(range, orientation, color);

        _line = new Line();
        _line.setStroke(color.brighter());
        getChildren().add(_line);
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        _line.setStartX(0);
        _line.setStartY(0);
        _line.setEndX(width);
        _line.setEndY(height);
    }

    @Override
    public void setValue(double value) {
        switch (getOrientation()) {
            case HORIZONTAL -> {
                var x = getXCoordinateFor(value);
                _line.setStartX(x);
                _line.setEndX(x);
            }
            case VERTICAL -> {
                var y = getYCoordinateFor(value);
                _line.setStartY(y);
                _line.setEndY(y);
            }
        }
    }
}
