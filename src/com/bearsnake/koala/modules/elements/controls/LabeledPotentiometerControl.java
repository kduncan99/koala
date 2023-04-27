/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.knobs.LabeledPotentiometer;
import com.bearsnake.koala.messages.IListener;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.messages.controls.PotentiometerControlMessage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LabeledPotentiometerControl extends UIControl implements IListener {

    protected static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final PixelDimensions PIXEL_DIMENSIONS = UIControl.determinePixelDimensions(CELL_DIMENSIONS);

    public LabeledPotentiometerControl(
        final String legend,
        final DoubleRange range,
        final Color backgroundColor,
        final Color textColor,
        final String textFormat,
        final double initialValue
    ) {
        super(CELL_DIMENSIONS,
              createPotentiometerPane(backgroundColor, textColor, range, textFormat, initialValue),
              legend);
        getPotentiometer().registerListener(this);
    }

    private static Pane createPotentiometerPane(
        final Color backgroundColor,
        final Color textColor,
        final DoubleRange range,
        final String textFormat,
        final double initialValue
    ) {
        var min = Math.min(PIXEL_DIMENSIONS.getWidth(), PIXEL_DIMENSIONS.getHeight());
        var potDims = new PixelDimensions(min, min);
        var pot = new LabeledPotentiometer(potDims, backgroundColor, textColor, range, textFormat, initialValue);

        var pane = new Pane();
        pane.getChildren().add(pot);
        return pane;
    }

    protected LabeledPotentiometer getPotentiometer() {
        var pane = (Pane) getChildren().get(0);
        return (LabeledPotentiometer) pane.getChildren().get(0);
    }

    public double getValue() {
        return getPotentiometer().getPosition();
    }

    public void setValue(
        final double value
    ) {
        getPotentiometer().setPosition(value);
    }

    @Override
    public void notify(
        final int identifier,
        final Message message
    ) {
        notifyListeners(new PotentiometerControlMessage(getValue()));
    }
}
