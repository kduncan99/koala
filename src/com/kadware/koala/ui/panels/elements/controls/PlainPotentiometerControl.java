/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.messages.IListener;
import com.kadware.koala.ui.components.knobs.Potentiometer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PlainPotentiometerControl extends PotentiometerControl implements IListener {

    public PlainPotentiometerControl(
        final int identifier,
        final String legend,
        final Color color,
        final DoubleRange range
    ) {
        super(identifier,
              legend,
              color,
              range,
              createPane(identifier, KNOB_DIMENSIONS, color));
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
}
