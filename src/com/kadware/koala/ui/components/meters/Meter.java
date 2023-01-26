/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import javafx.scene.layout.*;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public abstract class Meter extends GridPane {

    private final MeterParams _params;
    private final GradientPane _gradientPane;
    private final GraphPane _graphPane;

    public Meter(
        final MeterParams meterParams,
        final GradientPane gradientPane,
        final GraphPane graphPane
    ) {
        _params = meterParams;

        setPrefWidth(_params.getDimensions().getWidth());
        setPrefHeight(_params.getDimensions().getHeight());
        _gradientPane = gradientPane;
        _graphPane = graphPane;

        _params.getOrientation().applyOrientation(this, _graphPane, _gradientPane);
        _params.getOrientation().drawGradient(_gradientPane, _params.getScalar());
    }

    protected MeterParams getParams() { return _params; }
    protected GradientPane getGradientPane() { return _gradientPane; }
    protected GraphPane getGraphPane() { return _graphPane; }

    public void setValue(
        final double value
    ) {
        _graphPane.setValue(value);
    }
}
