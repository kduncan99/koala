/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.knobs;

import com.kadware.koala.PixelDimensions;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A subclass of Potentiometer which adds an indicator display in the knob.
 */
public class LabeledPotentiometer extends Potentiometer {

    private final Label _indicator;

    public LabeledPotentiometer(
        final int identifier,
        final PixelDimensions dimensions,
        final Color bgColor,
        final Color textColor
    ) {
        super(identifier, dimensions, bgColor);
        _indicator = new Label();
        _indicator.setPrefWidth(dimensions.getWidth());
        _indicator.setPrefHeight(dimensions.getHeight());
        _indicator.setTextFill(textColor.brighter());
        _indicator.setFont(new Font(10));
        _indicator.setAlignment(Pos.CENTER);
        getChildren().add(_indicator);
    }

    public void setLabelText(
        final String text
    ) {
        _indicator.setText(text);
    }
}
