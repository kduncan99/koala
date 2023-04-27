/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.knobs;

import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.messages.components.PotentiometerKnobComponentMessage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A subclass of Potentiometer which adds an indicator display in the knob,
 * and a range which we translate into the super-class's range of 0.0 to 1.0.
 */
public class LabeledPotentiometer extends RangedPotentiometer {

    private final Label _indicator;
    private final String _textFormat;

    public LabeledPotentiometer(
        final PixelDimensions dimensions,
        final Color bgColor,
        final Color textColor,
        final DoubleRange range,
        final String textFormat,
        final double initialValue
    ) {
        super(dimensions, bgColor, range, initialValue);
        _textFormat = textFormat;
        _indicator = new Label();
        _indicator.setPrefWidth(dimensions.getWidth());
        _indicator.setPrefHeight(dimensions.getHeight());
        _indicator.setTextFill(textColor.brighter());
        _indicator.setFont(new Font(10));
        _indicator.setAlignment(Pos.CENTER);
        getChildren().add(_indicator);
        setPosition(initialValue);
    }

    @Override
    protected void mouseDoubleClicked(
        final MouseEvent event
    ) {
        super.mouseDoubleClicked(event);
        _indicator.setText(String.format(_textFormat, getPosition()));
    }

    @Override
    protected void positionDecrement() {
        super.positionDecrement();
        _indicator.setText(String.format(_textFormat, getPosition()));
    }

    @Override
    protected void positionIncrement() {
        super.positionIncrement();
        _indicator.setText(String.format(_textFormat, getPosition()));
    }

    @Override
    public void setPosition(
        final double position
    ) {
        super.setPosition(position);
        _indicator.setText(String.format(_textFormat, position));
    }
}
