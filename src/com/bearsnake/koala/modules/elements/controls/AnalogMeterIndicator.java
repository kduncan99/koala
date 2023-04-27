/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.meters.Meter;
import com.bearsnake.koala.components.ui.meters.OrientationType;
import com.bearsnake.koala.components.ui.meters.SelectableMeter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 * It is designed to be fit into a one-by-one cell.
 */
public class AnalogMeterIndicator extends UIControl {

    public AnalogMeterIndicator(
        final CellDimensions cellDimensions,
        final String legend,
        final Color color
    ) {
        super(cellDimensions, createMeterPane(cellDimensions, color), legend);
    }

    private static Pane createMeterPane(
        final CellDimensions cellDimensions,
        final Color color
    ) {
        var range = Koala.BIPOLAR_RANGE;
        var orientation = cellDimensions.getWidth() >= cellDimensions.getHeight() ? OrientationType.HORIZONTAL : OrientationType.VERTICAL;
        var tickPoints = new double[]{-1.0, -0.75, -0.5, -0.25, 0.0, 0.25, 0.5, 0.75, 1.0};
        var labelPoints = new double[]{-1.0, 0.0, 1.0};
        var labelFormat = "%4.1f";
        var meterDim = new PixelDimensions(UIControl.determinePixelWidth(cellDimensions.getWidth()),
                                           UIControl.determineEntityHeight(cellDimensions.getHeight()));
        var meter = new SelectableMeter(meterDim, range, orientation, color, tickPoints, labelPoints, labelFormat);

        var pane = new Pane();
        pane.getChildren().add(meter);
        return pane;
    }

    private Meter getMeter() {
        var pane = (Pane) getChildren().get(0);
        return (Meter) pane.getChildren().get(0);
    }

    //  only to be invoked on the Application thread
    public void setValue(
        final double value
    ) {
        getMeter().setValue(value);
    }
}
