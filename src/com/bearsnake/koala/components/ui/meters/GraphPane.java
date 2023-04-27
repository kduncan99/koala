/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.meters;

import com.bearsnake.koala.DoubleRange;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class GraphPane extends RangedPane {

    private final OrientationType _orientation;

    public GraphPane(
        final DoubleRange range,
        final OrientationType orientation,
        final Color color
    ) {
        super(range, color);
        _orientation = orientation;
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        setClip(new Rectangle(width, height));
    }

    public OrientationType getOrientation() { return _orientation; }

    public abstract void setValue(double value);
}
