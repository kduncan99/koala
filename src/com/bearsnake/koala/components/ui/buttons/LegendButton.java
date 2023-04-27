/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.buttons;

import com.bearsnake.koala.PixelDimensions;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A button with a Pane which provides some sort of legend for the button.
 */
public abstract class LegendButton extends Button {

    private static final Font LEGEND_FONT = new Font(12);
    protected static final int LEGEND_INSET = 2;

    private final Pane _legendPane;
    private final int _legendXPos;
    private final int _legendYPos;

    public LegendButton(
        final PixelDimensions dimensions,
        final Pane legend
    ) {
        super(dimensions);

        var xDelta = dimensions.getWidth() - legend.getPrefWidth();
        var yDelta = dimensions.getHeight() - legend.getPrefHeight();
        _legendPane = legend;
        _legendXPos = (int)(xDelta / 2);
        _legendYPos = (int)(yDelta / 2);
        _legendPane.setLayoutX(_legendXPos);
        _legendPane.setLayoutY(_legendYPos);
        getChildren().add(_legendPane);
    }

    public LegendButton(
        final PixelDimensions dimensions,
        final String legend,
        final Color color
    ) {
        this(dimensions, createLegendPane(dimensions, legend, color));
    }

    private static Pane createLegendPane(
        final PixelDimensions dimensions,
        final String text,
        final Color color
    ) {
        var pane = new Pane();
        var inset = LEGEND_INSET;
        pane.setPrefWidth(dimensions.getWidth() - 2 * inset);
        pane.setPrefHeight(dimensions.getHeight() - 2 * inset);

        var label = new Label(text);
        label.setFont(LEGEND_FONT);
        label.setTextFill(color.brighter());
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(pane.getPrefHeight());
        label.setPrefWidth(pane.getPrefWidth());

        pane.getChildren().add(label);
        return pane;
    }

    protected Pane getLegendPane() { return _legendPane; }

    protected void setButtonPressed(
        final boolean value
    ) {
        super.setButtonPressed(value);
        if (value) {
            _legendPane.setLayoutX(_legendXPos + 1);
            _legendPane.setLayoutY(_legendYPos + 1);
        } else {
            _legendPane.setLayoutX(_legendXPos);
            _legendPane.setLayoutY(_legendYPos);
        }
    }
}
