/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.*;
import com.kadware.koala.ui.components.meters.*;
import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 * It is designed to be fit into a one-by-one cell.
 */
public class ControlValueIndicator extends MeterIndicator {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(2, 1);

    public ControlValueIndicator(
        final String legend,
        final Color color
    ) {
        super(CELL_DIMENSIONS, createMeter(color), legend);
    }

    private static Meter createMeter(
        final Color color
    ) {
        var width = CELL_DIMENSIONS.getWidth() * ControlPane.HORIZONTAL_PIXELS_PER_CELL;
        var height = CELL_DIMENSIONS.getHeight() * ControlPane.VERTICAL_PIXELS_PER_CONTROL;

        var range = Koala.BIPOLAR_RANGE;
        var orientation = OrientationType.HORIZONTAL;
        var tickPoints = new double[]{-5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        var labelPoints = new double[]{-5.0, 0.0, 5.0};
        var labelFormat = "%4.1f";
        var meterDim = new PixelDimensions(width, height);

        return new SelectableMeter(meterDim, range, orientation, color, tickPoints, labelPoints, labelFormat);
    }

    //  only to be invoked on the Application thread
    @Override
    public void setValue(
        final double value
    ) {
        getMeter().setValue(value);
    }
}
