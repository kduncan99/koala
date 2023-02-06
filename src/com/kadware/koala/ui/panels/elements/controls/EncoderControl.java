/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.messages.IListener;
import com.kadware.koala.messages.Message;
import com.kadware.koala.ui.components.knobs.Encoder;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class EncoderControl extends ControlPane implements IListener {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final PixelDimensions PIXEL_DIMENSIONS = toPixelDimensions(CELL_DIMENSIONS);
    private static final int PIXEL_EDGE_SIZE = Math.min(PIXEL_DIMENSIONS.getWidth(), PIXEL_DIMENSIONS.getHeight());
    private static final PixelDimensions KNOB_DIMENSIONS = new PixelDimensions(PIXEL_EDGE_SIZE, PIXEL_EDGE_SIZE);

    public EncoderControl(
        final int identifier,
        final String legend,
        final Color color
    ) {
        super(identifier,
              CELL_DIMENSIONS,
              createPane(identifier, KNOB_DIMENSIONS, color),
              legend);
        var pane = (Pane) getChildren().get(0);
        var encoder = (Encoder) pane.getChildren().get(0);
        encoder.registerListener(this);
    }

    private static Pane createPane(
        final int identifier,
        final PixelDimensions dimensions,
        final Color color
    ) {
        var pane = new Pane();
        var encoder = new Encoder(identifier, dimensions, color);
        pane.getChildren().add(encoder);
        return pane;
    }

    @Override
    public void notify(
        final Message message
    ) {
        //  we do not need to translate the message.
        notifyListeners(message.setSender(this));
    }

    @Override
    public void setValue(double value) {
        //  nothing to do
    }
}
