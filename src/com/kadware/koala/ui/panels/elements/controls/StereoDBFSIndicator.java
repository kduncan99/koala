/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.meters.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class StereoDBFSIndicator extends MeterIndicator {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 3);
    private static final Color COLOR = Color.LIGHTBLUE;
    private static final OrientationType ORIENTATION = OrientationType.VERTICAL;

    public StereoDBFSIndicator(
        final String legend
    ) {
        super(CELL_DIMENSIONS, createPane(), legend);
    }

    private static Pane createPane() {
        var pane = new Pane();
        var width = CELL_DIMENSIONS.getWidth() * ControlPane.HORIZONTAL_PIXELS_PER_CELL;
        var height = (CELL_DIMENSIONS.getHeight() * ControlPane.VERTICAL_PIXELS_PER_CELL) - VERTICAL_PIXELS_PER_LABEL;
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);

        var meter = new StereoDBMeter(new PixelDimensions(width, height), ORIENTATION, COLOR);

        pane.getChildren().add(meter);
        return pane;
    }

    //  TODO when we invoke the indicator's setValues() method, we need to convert the wave value to a dbfs value
}
