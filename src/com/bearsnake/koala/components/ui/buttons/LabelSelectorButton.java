/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.buttons;

import com.bearsnake.koala.PixelDimensions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A LabelSelectorButton is a special case of a SelectorButton which provides string values
 * corresponding to the states of the button.
 */
public class LabelSelectorButton extends SelectorButton {

    private static final Font LABEL_FONT = new Font(12);

    public LabelSelectorButton(
        final PixelDimensions dimensions,
        final String[] labelStrings,
        final Color[] colors
    ) {
        super(dimensions, createPanes(dimensions, labelStrings, colors));
    }

    private static Pane[] createPanes(
        final PixelDimensions dimensions,
        final String[] labelStrings,
        final Color[] colors
    ) {
        var result = new Pane[labelStrings.length];
        for (int x = 0; x < labelStrings.length; x++) {
            var color = colors[x];

            var label = new Label(labelStrings[x]);
            label.setFont(LABEL_FONT);
            label.setTextFill(color.invert());
            label.setPrefWidth(dimensions.getWidth());
            label.setPrefHeight(dimensions.getHeight());
            label.setAlignment(Pos.CENTER);

            var pane = new Pane(label);
            pane.setPrefWidth(dimensions.getWidth());
            pane.setPrefHeight(dimensions.getHeight());
            pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
            result[x] = pane;
        }

        return result;
    }
}
