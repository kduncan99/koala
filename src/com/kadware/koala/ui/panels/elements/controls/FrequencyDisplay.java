/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.NumericDisplay;
import com.kadware.koala.ui.panels.Panel;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class FrequencyDisplay extends ControlPane {

    public FrequencyDisplay(
        final CellDimensions cellDimensions,
        final String legend,
        final Color color
    ) {
        super(cellDimensions, createPane(cellDimensions, color), legend);
    }

    private static Pane createPane(
        final CellDimensions cellDimensions,
        final Color color
    ) {
        var pane = new Pane();

        //  we have to get pixel dimensions here, as we haven't yet invoked the base class c'tor
        //  and therefore cannot ask for prefWidth or prefHeight.
        var pd = toPixelDimensions(cellDimensions);
        var bgColor = Panel.PANEL_CELL_BACKGROUND_COLOR;
        var verticalInsets = 15;

        //  adjusted settings
        var x = 0;
        var y = verticalInsets;
        var w = pd.getWidth();
        var h = pd.getHeight() - 2 * verticalInsets;
        var adjustedPD = new PixelDimensions(w, h);
        var display = new NumericDisplay(adjustedPD, color, "%8.2fHz");
        display.setLayoutX(x);
        display.setLayoutY(y);
        display.setPrefSize(w, h);
        pane.getChildren().add(display);

        return pane;
    }

    //  Only to be invoked on the Application thread
    @Override
    public void setValue(
        final double value
    ) {
        var pane = (Pane) getChildren().iterator().next();
        var display = (NumericDisplay) pane.getChildren().iterator().next();
        display.setValue(value);
    }
}
