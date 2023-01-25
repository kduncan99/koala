/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.buttons.Button;
import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

/**
 * A button control is a button of some sort, above a legend.
 * Note that we are talking about a Koala button, not a JavaFX button.
 */
public class ButtonControl extends ControlPane {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final PixelDimensions PIXEL_DIMENSIONS = toPixelDimensions(CELL_DIMENSIONS);

    private static final BackgroundFill PANE_BACKGROUND_FILL = new BackgroundFill(Panel.PANEL_CELL_BACKGROUND_COLOR,
                                                                                  CornerRadii.EMPTY,
                                                                                  Insets.EMPTY);
    private static final Background PANE_BACKGROUND = new Background(PANE_BACKGROUND_FILL);

    public ButtonControl(
        final String legend,
        final Button button
    ) {
        super(CELL_DIMENSIONS, createPane(button), legend);
    }

    private static Pane createPane(
        final Button button
    ) {
        var pane = new Pane();
        pane.setBackground(PANE_BACKGROUND);

        var xDelta = PIXEL_DIMENSIONS.getWidth() - button.getPrefWidth();
        var yDelta = PIXEL_DIMENSIONS.getWidth() - button.getPrefHeight();
        var xOffset = xDelta / 2.0;
        var yOffset = yDelta / 2.0;
        pane.getChildren().add(button);
        button.setLayoutX(xOffset);
        button.setLayoutY(yOffset);

        return pane;
    }
}
