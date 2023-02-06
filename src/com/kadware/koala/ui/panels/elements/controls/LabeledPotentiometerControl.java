/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.knobs.LabeledPotentiometer;
import com.kadware.koala.ui.panels.Panel;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LabeledPotentiometerControl extends PotentiometerControl {

    public LabeledPotentiometerControl(
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
        var encoder = new LabeledPotentiometer(identifier, dimensions, color, Panel.PANEL_LEGEND_COLOR);
        pane.getChildren().add(encoder);
        return pane;
    }

    @Override
    public void setValue(
        final double value
    ) {
        super.setValue(value);
        ((LabeledPotentiometer) getPotentiometer()).setLabelText(String.format("%3.2f", value));
    }
}
