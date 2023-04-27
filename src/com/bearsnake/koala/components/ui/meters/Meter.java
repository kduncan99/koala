/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.meters;

import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.ActiveComponent;
import javafx.scene.layout.GridPane;

/**
 * A graphic indication of continuous values, ranging between a low value of 0.0 and a high value of 1.0.
 * Any uses which represent ranges outside of these values must scale those values accordingly.
 * <p>
 * We subclass ActiveComponent on the off chance that one of the meters might have some sort of averaging
 * or trailing behavior, which would require the poll() method. We no-op close() and reset().
 */
public abstract class Meter extends ActiveComponent {

    private final GridPane _meterPane;
    private final GradientPane _gradientPane;
    private final GraphPane _graphPane;

    public Meter(
        final PixelDimensions dimensions,
        final OrientationType orientation,
        final GradientPane gradientPane,
        final GraphPane graphPane
    ) {
        _meterPane = new GridPane();
        _gradientPane = gradientPane;
        _graphPane = graphPane;

        setPrefSize(dimensions.getWidth(), dimensions.getHeight());

        switch (orientation) {
            case HORIZONTAL -> setHorizontalLayout();
            case VERTICAL -> setVerticalLayout();
        }

        _gradientPane.drawGradient();
        getChildren().add(_meterPane);
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
        _meterPane.add(_graphPane, 0, 0, 1, 1);
        _meterPane.add(_gradientPane, 0, 1, 1, 1);
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
        _meterPane.add(_gradientPane, 0, 0, 1, 1);
        _meterPane.add(_graphPane, 1, 0, 1, 1);
    }

    public void setValue(
        final double value
    ) {
        _graphPane.setValue(value);
    }
}
