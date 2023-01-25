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

    private static final int BUTTON_INSET = 5;
    private static final int BUTTON_EDGE_PIXELS =
        Math.min(PIXEL_DIMENSIONS.getWidth(), PIXEL_DIMENSIONS.getHeight()) - BUTTON_INSET;
    protected static final PixelDimensions BUTTON_DIMENSIONS =
        new PixelDimensions(BUTTON_EDGE_PIXELS, BUTTON_EDGE_PIXELS);

    public ButtonControl(
        final Button button,
        final String legend
    ) {
        super(CELL_DIMENSIONS, createPane(button), legend);
    }

    private static Pane createPane(
        final Button button
    ) {
        var pane = new Pane();
        pane.setBackground(PANE_BACKGROUND);

        button.setLayoutX(BUTTON_INSET);
        button.setLayoutY(BUTTON_INSET);
        button.setPrefWidth(BUTTON_DIMENSIONS.getWidth());
        button.setPrefHeight(BUTTON_DIMENSIONS.getHeight());
        pane.getChildren().add(button);

        return pane;
    }
}
