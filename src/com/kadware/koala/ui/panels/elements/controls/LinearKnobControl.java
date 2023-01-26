/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.DoubleRange;
import com.kadware.koala.ui.components.knobs.Knob;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LinearKnobControl extends ControlPane {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final int KNOB_EDGE_SIZE = toPixelDimensions(CELL_DIMENSIONS).getWidth();
    private static final PixelDimensions KNOB_DIMENSIONS = new PixelDimensions(KNOB_EDGE_SIZE, KNOB_EDGE_SIZE);

    public LinearKnobControl(
        final String legend,
        final Color color,
        final DoubleRange range
    ) {
        super(CELL_DIMENSIONS, createPane(color, range), legend);
    }

    private static Pane createPane(
        final Color color,
        final DoubleRange range
    ) {
        var pane = new Pane();
        //TODO
//        var knob = new Knob(KNOB_DIMENSIONS, color);//TODO need ranging information somewhere/somehow
//        pane.getChildren().add(knob);
        return pane;
    }
}
