/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.knobs.Potentiometer;
import com.bearsnake.koala.messages.IListener;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.messages.controls.PotentiometerControlMessage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PotentiometerControl extends UIControl implements IListener {

    protected static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final PixelDimensions PIXEL_DIMENSIONS = UIControl.determinePixelDimensions(CELL_DIMENSIONS);

    private final DoubleRange _range;

    public PotentiometerControl(
        final String legend,
        final DoubleRange range,
        final Color color,
        final double initialValue
    ) {
        super(CELL_DIMENSIONS, createPotentiometerPane(color), legend);
        _range = range;
        setValue(initialValue);
        getPotentiometer().registerListener(this);
    }

    private static Pane createPotentiometerPane(
        final Color color
    ) {
        var min = Math.min(PIXEL_DIMENSIONS.getWidth(), PIXEL_DIMENSIONS.getHeight());
        var potDims = new PixelDimensions(min, min);
        var pot = new Potentiometer(potDims, color);

        var pane = new Pane();
        pane.getChildren().add(pot);
        return pane;
    }

    protected Potentiometer getPotentiometer() {
        var pane = (Pane) getChildren().get(0);
        return (Potentiometer) pane.getChildren().get(0);
    }

    protected final DoubleRange getRange() { return _range; }

    public double getValue() {
        return getRange().scaleValue(getPotentiometer().getPosition());
    }

    public void setValue(
        final double value
    ) {
        var pos = getRange().normalizeValue(value);
        getPotentiometer().setPosition(pos);
    }

    @Override
    public void notify(
        final int identifier,
        final Message message
    ) {
        notifyListeners(new PotentiometerControlMessage(getValue()));
    }
}
