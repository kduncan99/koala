/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import javafx.scene.layout.*;

/**
 * A graphic indication of continuous values, ranging between a low value of 0.0 and a high value of 1.0.
 * Any uses which represent ranges outside of these values must scale those values accordingly.
 */
public abstract class Meter extends GridPane {

    private final GradientPane _gradientPane;
    private final GraphPane _graphPane;

    public Meter(
        final PixelDimensions dimensions,
        final OrientationType orientation,
        final GradientPane gradientPane,
        final GraphPane graphPane
    ) {
        _gradientPane = gradientPane;
        _graphPane = graphPane;

        setPrefSize(dimensions.getWidth(), dimensions.getHeight());

        switch (orientation) {
            case HORIZONTAL -> setHorizontalLayout();
            case VERTICAL -> setVerticalLayout();
        }

        _gradientPane.drawGradient();
    }

    protected GradientPane getGradientPane() { return _gradientPane; }
    protected GraphPane getGraphPane() { return _graphPane; }

    /*
     * Horizontal:  ---------------
     *              |    graph    |
     *              ---------------
     *              |   gradient  |
     *              ---------------
     */
    private void setHorizontalLayout() {
        var w = getPrefWidth();
        var h = getPrefHeight() / 2;
        _graphPane.setPrefSize(w, h);
        _gradientPane.setPrefSize(w, h);
        add(_graphPane, 0, 0, 1, 1);
        add(_gradientPane, 0, 1, 1, 1);
    }

    /*
     * Vertical: -----
     *           | | |  a -> gradient
     *           |a|b|  b -> graph
     *           | | |
     *           -----
     */
    private void setVerticalLayout() {
        var w = getPrefWidth() / 2;
        var h = getPrefHeight();
        _graphPane.setPrefSize(w, h);
        _gradientPane.setPrefSize(w, h);
        add(_gradientPane, 0, 0, 1, 1);
        add(_graphPane, 1, 0, 1, 1);
    }

    public void setValue(
        final double value
    ) {
        _graphPane.setValue(value);
    }
}
