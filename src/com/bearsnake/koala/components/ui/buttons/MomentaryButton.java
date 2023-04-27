/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.buttons;

import com.bearsnake.koala.PixelDimensions;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * A momentary button is pressed only so long as the mouse button is pressed.
 */
public abstract class MomentaryButton extends LegendButton {

    public MomentaryButton(
        final PixelDimensions dimensions,
        final Pane legend
    ) {
        super(dimensions, legend);
    }

    public MomentaryButton(
        final PixelDimensions dimensions,
        final String legend,
        final Color color
    ) {
        super(dimensions, legend, color);
    }

    @Override
    protected void mousePressed(MouseEvent event) {
        super.setButtonPressed(true);
    }

    @Override
    protected void mouseReleased(MouseEvent event) {
        super.setButtonPressed(false);
    }
}
