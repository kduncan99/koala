/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.knobs.Encoder;
import com.bearsnake.koala.messages.IListener;
import com.bearsnake.koala.messages.Message;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class EncoderControl extends UIControl implements IListener {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final PixelDimensions PIXEL_DIMENSIONS = UIControl.determinePixelDimensions(CELL_DIMENSIONS);

    /**
     * A Control which wraps an Encoder Component.
     * It is always sized to fit in one cell.
     * @param legend The small bit of text which is displayed below the Encoder
     * @param color The base color for the underlying Encoder Component
     */
    public EncoderControl(
        final String legend,
        final Color color
    ) {
        super(CELL_DIMENSIONS, createEncoderPane(color), legend);
        getEncoder().registerListener(this);
    }

    private static Pane createEncoderPane(
        final Color color
    ) {
        var min = Math.min(PIXEL_DIMENSIONS.getWidth(), PIXEL_DIMENSIONS.getHeight());
        var encoderDims = new PixelDimensions(min, min);
        var encoder = new Encoder(encoderDims, color);

        var pane = new Pane();
        pane.getChildren().add(encoder);
        return pane;
    }

    protected Encoder getEncoder() {
        var pane = (Pane) getChildren().get(0);
        return (Encoder) pane.getChildren().get(0);
    }

    @Override
    public void notify(
        final int identifier,
        final Message message
    ) {
        //  we do not need to translate the message.
        notifyListeners(message);
    }
}
