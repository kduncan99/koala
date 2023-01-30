/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.DoubleRange;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.messages.IListener;
import com.kadware.koala.messages.Message;
import com.kadware.koala.ui.components.knobs.Potentiometer;
import com.kadware.koala.ui.components.messages.PotentiometerKnobMessage;
import com.kadware.koala.ui.panels.messages.PulseWidthMessage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PotentiometerControl extends ControlPane implements IListener {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final PixelDimensions PIXEL_DIMENSIONS = toPixelDimensions(CELL_DIMENSIONS);
    private static final int PIXEL_EDGE_SIZE = Math.min(PIXEL_DIMENSIONS.getWidth(), PIXEL_DIMENSIONS.getHeight());
    private static final PixelDimensions KNOB_DIMENSIONS = new PixelDimensions(PIXEL_EDGE_SIZE, PIXEL_EDGE_SIZE);

    private final Potentiometer _potentiometer;
    private final DoubleRange _range;

    public PotentiometerControl(
        final int identifier,
        final String legend,
        final Color color,
        final DoubleRange range
    ) {
        super(identifier,
              CELL_DIMENSIONS,
              createPane(identifier, KNOB_DIMENSIONS, color),
              legend);
        _range = range;
        var pane = (Pane) getChildren().get(0);
        _potentiometer = (Potentiometer) pane.getChildren().get(0);
        _potentiometer.registerListener(this);
    }

    private static Pane createPane(
        final int identifier,
        final PixelDimensions dimensions,
        final Color color
    ) {
        var pane = new Pane();
        var encoder = new Potentiometer(identifier, dimensions, color);
        pane.getChildren().add(encoder);
        return pane;
    }

    @Override
    public void notify(
        final Message message
    ) {
        var position = ((PotentiometerKnobMessage) message).getPosition();
        var value = _range.scaleValue(position);
        notifyListeners(new PulseWidthMessage(this, getIdentifier(), value));
    }

    @Override
    public void setValue(
        final double value
    ) {
        _potentiometer.setPosition(_range.normalizeValue(value));
    }
}
