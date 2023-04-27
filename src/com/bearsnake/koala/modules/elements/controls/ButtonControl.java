/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.buttons.Button;
import com.bearsnake.koala.messages.IListener;
import javafx.scene.layout.Pane;

/**
 * Base class for all button-type controls
 */
public abstract class ButtonControl extends UIControl implements IListener {

    protected static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    protected static final int BUTTON_MARGIN = 5;
    protected static final int BUTTON_WIDTH = UIControl.determinePixelWidth(CELL_DIMENSIONS.getWidth()) - (2 * BUTTON_MARGIN);
    protected static final int BUTTON_HEIGHT = UIControl.determineEntityHeight(CELL_DIMENSIONS.getHeight()) - (2 * BUTTON_MARGIN);
    protected static final PixelDimensions BUTTON_DIMENSIONS = new PixelDimensions(BUTTON_WIDTH, BUTTON_HEIGHT);

    public ButtonControl(
        final CellDimensions cellDimensions,
        final Button entity,
        final String legend
    ) {
        super(cellDimensions, createPane(entity), legend);
    }

    private static Pane createPane(
        final Button button
    ) {
        var pane = new Pane();
        pane.getChildren().add(button);
        button.setLayoutX(BUTTON_MARGIN);
        button.setLayoutY(BUTTON_MARGIN);
        return pane;
    }

    protected Button getButton() {
        var pane = (Pane) getChildren().get(0);
        return (Button) pane.getChildren().get(0);
    }
}
