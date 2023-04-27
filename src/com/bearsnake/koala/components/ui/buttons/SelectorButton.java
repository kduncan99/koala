/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.buttons;

import com.bearsnake.koala.IntegerRange;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.RolloverCounter;
import com.bearsnake.koala.messages.components.SelectorButtonComponentMessage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * A selector button is a MomentaryButton which cycles through {n} possible states.
 * The legend pane is updated according to which state the button advances.
 */
public class SelectorButton extends MomentaryButton {

    private final Pane[] _imagePanes;
    private final RolloverCounter _selector;

    public SelectorButton(
        final PixelDimensions dimensions,
        final Pane[] imagePanes
    ) {
        super(dimensions, createParentPane(dimensions, imagePanes));
        _selector = new RolloverCounter(new IntegerRange(0, imagePanes.length));
        _imagePanes = imagePanes;
        _imagePanes[_selector.getValue()].setVisible(true);
    }

    private static Pane createParentPane(
        final PixelDimensions dimensions,
        final Pane[] imagePanes
    ) {
        if (imagePanes.length < 1)
            throw new RuntimeException("No images for SelectorButton");

        var parent = new Pane();
        var inset = LEGEND_INSET;
        parent.setPrefWidth(dimensions.getWidth() - 2 * inset);
        parent.setPrefHeight(dimensions.getHeight() - 2 * inset);

        for (var pane : imagePanes) {
            var xLayout = (parent.getPrefWidth() - pane.getPrefWidth()) / 2;
            var yLayout = (parent.getPrefHeight() - pane.getPrefHeight()) / 2;
            pane.setLayoutX(xLayout);
            pane.setLayoutY(yLayout);
            pane.setPrefWidth(parent.getPrefWidth());
            pane.setPrefHeight(parent.getPrefHeight());
            pane.setVisible(false);
            parent.getChildren().add(pane);
        }

        return parent;
    }

    @Override
    protected void mouseClicked(
        final MouseEvent event
    ) {
        _imagePanes[_selector.getValue()].setVisible(false);
        _selector.increment();
        _imagePanes[_selector.getValue()].setVisible(true);
        notifyListeners(new SelectorButtonComponentMessage(_selector.getValue()));
    }

    public int getSelectorValue() { return _selector.getValue(); }
}
