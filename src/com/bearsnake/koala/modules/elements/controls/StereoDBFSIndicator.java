/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.meters.Meter;
import com.bearsnake.koala.components.ui.meters.OrientationType;
import com.bearsnake.koala.components.ui.meters.StereoDBMeter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class StereoDBFSIndicator extends UIControl {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 3);
    private static final Color COLOR = Color.LIGHTBLUE;
    private static final OrientationType ORIENTATION = OrientationType.VERTICAL;

    public StereoDBFSIndicator(
        final String legend
    ) {
        super(CELL_DIMENSIONS, createMeterPane(), legend);
    }

    private static Pane createMeterPane() {
        var width = CELL_DIMENSIONS.getWidth() * UIControl.HORIZONTAL_PIXELS_PER_CELL;
        var height = (CELL_DIMENSIONS.getHeight() * UIControl.VERTICAL_PIXELS_PER_CELL) - UIControl.VERTICAL_PIXELS_FOR_LEGEND;
        var meter = new StereoDBMeter(new PixelDimensions(width, height), ORIENTATION, COLOR);

        var pane = new Pane();
        pane.getChildren().add(meter);
        return pane;
    }

    private Meter getMeter() {
        var pane = (Pane) getChildren().get(0);
        return (Meter) pane.getChildren().get(0);
    }

    //  Only invoked on the ApplicationThread
    public void setValues(
        final double leftValue,
        final double rightValue
    ) {
        ((StereoDBMeter) getMeter()).setValues(leftValue, rightValue);
    }
}
