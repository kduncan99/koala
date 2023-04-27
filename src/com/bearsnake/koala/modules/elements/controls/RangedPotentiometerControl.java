/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.knobs.RangedPotentiometer;
import com.bearsnake.koala.messages.IListener;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.messages.controls.PotentiometerControlMessage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Represents a ranged potentiometer as a UIControl.
 * All subclasses should defer to our implementation of state handling (wrt RangedPotentiometerState)
 */
public class RangedPotentiometerControl extends UIControl implements IListener {

    public class RangedPotentiometerState extends State {

        public final double _value;

        public RangedPotentiometerState(
            final double value
        ) {
            _value = value;
        }
    }

    protected static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final PixelDimensions PIXEL_DIMENSIONS = UIControl.determinePixelDimensions(CELL_DIMENSIONS);

    private final DoubleRange _range;

    public RangedPotentiometerControl(
        final String legend,
        final DoubleRange range,
        final Color color,
        final double initialValue
    ) {
        super(CELL_DIMENSIONS, createPotentiometerPane(color, range, initialValue), legend);
        _range = range;
        setScaledValue(initialValue);
        getRangedPotentiometer().registerListener(this);
    }

    protected RangedPotentiometerControl(
        final String legend,
        final DoubleRange range,
        final Pane pane,
        final double initialValue
    ) {
        super(CELL_DIMENSIONS, pane, legend);
        _range = range;
        setScaledValue(initialValue);
        getRangedPotentiometer().registerListener(this);
    }

    private static Pane createPotentiometerPane(
        final Color color,
        final DoubleRange range,
        final double initialValue
    ) {
        var min = Math.min(PIXEL_DIMENSIONS.getWidth(), PIXEL_DIMENSIONS.getHeight());
        var potDims = new PixelDimensions(min, min);
        var pot = new RangedPotentiometer(potDims, color, range, initialValue);

        var pane = new Pane();
        pane.getChildren().add(pot);
        return pane;
    }

    protected RangedPotentiometer getRangedPotentiometer() {
        var pane = (Pane) getChildren().get(0);
        return (RangedPotentiometer) pane.getChildren().get(0);
    }

    protected final DoubleRange getRange() { return _range; }

    public RangedPotentiometerState getState() {
        return new RangedPotentiometerState(getScaledValue());
    }

    public double getScaledValue() {
        return getRange().scaleValue(getRangedPotentiometer().getPosition());
    }

    public void setState(
        final State state
    ) {
        if (state instanceof RangedPotentiometerState st) {
            setScaledValue(st._value);
        }
    }

    public void setScaledValue(
        final double value
    ) {
        var scaled = getRange().normalizeValue(value);
        getRangedPotentiometer().setScaledValue(scaled);
    }

    @Override
    public void notify(
        final int identifier,
        final Message message
    ) {
        notifyListeners(new PotentiometerControlMessage(getScaledValue()));
    }
}
