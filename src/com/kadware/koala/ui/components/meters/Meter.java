/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public abstract class Meter extends GridPane {

    private final GradientPane _gradientPane;
    private final GraphPane _graphPane;
    private final Orientation _orientation;
    private final Scalar _scalar;
    private final Color _color;
    private final Range _range;

    public Meter(
        final PixelDimensions dimensions,
        final Range range,
        final Orientation orientation,
        final Scalar scalar,
        final Color color,
        final GradientPane gradientPane
    ) {
        setPrefWidth(dimensions.getWidth());
        setPrefHeight(dimensions.getHeight());

        _orientation = orientation;
        _scalar = scalar;
        _color = color;
        _range = range;

        _gradientPane = gradientPane;
        _graphPane = new GraphPane(color);
        _orientation.applyOrientation(this, _graphPane, _gradientPane);
        _orientation.drawGradient(_gradientPane, scalar);
    }

    protected Color getColor() { return _color; }
    protected GradientPane getGradientPane() { return _gradientPane; }
    protected GraphPane getGraphPane() { return _graphPane; }
    protected Orientation getOrientation() { return _orientation; }
    protected Range getRange() { return _range; }
    protected Scalar getScalar() { return _scalar; }

    //  This should be called only on the Application thread
    public abstract void setValue(final double value);
}
