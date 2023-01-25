/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.*;
import com.kadware.koala.ui.components.meters.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 * It is designed to be fit into a one-by-one cell.
 */
public class ControlValueIndicator extends MeterIndicator implements IIndicator {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(2, 1);

    public ControlValueIndicator(
        final String legend,
        final Color color
    ) {
        super(CELL_DIMENSIONS, createPane(color), legend);
    }

    private static Pane createPane(
        final Color color
    ) {
        var pane = new Pane();
        pane.setPrefWidth(CELL_DIMENSIONS.getWidth() * ControlPane.HORIZONTAL_PIXELS_PER_CELL);
        pane.setPrefHeight(ControlPane.VERTICAL_PIXELS_PER_CONTROL);

        var range = new Range(Koala.MIN_CVPORT_VALUE, Koala.MAX_CVPORT_VALUE);
        var orientation = Orientation.HORIZONTAL;
        var scalar = new LinearScalar();
        var ticks = new double[]{-5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        var labels = new double[]{-5.0, 0.0, 5.0};
        var gradient = new GradientPane(color, range, ticks, labels, "%3.1f");

        var meterW = (int)pane.getPrefWidth();
        var meterH = (int)pane.getPrefHeight();
        var meterDim = new PixelDimensions(meterW, meterH);
        var meter = new SplitBarMeter(meterDim, range, orientation, scalar, color, 0.0, gradient);

        pane.getChildren().add(meter);
        return pane;
    }

    //  only to be invoked on the Application thread
    @Override
    public void setValue(
        final double value
    ) {
        Pane pane = (Pane) getChildren().iterator().next();
        Meter meter = (Meter) pane.getChildren().iterator().next();
        meter.setValue(value);
    }
}
