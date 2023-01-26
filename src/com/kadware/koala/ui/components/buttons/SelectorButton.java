/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.buttons;

import com.kadware.koala.IntegerRange;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.RolloverCounter;
import com.kadware.koala.ui.components.messages.SelectorButtonMessage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SelectorButton extends MomentaryButton {

    private final Pane[] _imagePanes;
    private final RolloverCounter _selector;

    public SelectorButton(
        final int identifier,
        final PixelDimensions dimensions,
        final Pane[] imagePanes
    ) {
        super(identifier, dimensions, createParentPane(dimensions, imagePanes));
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
        super.mouseClicked(event);
        notifyListeners(new SelectorButtonMessage(this, getIdentifier(), _selector.getValue()));
    }

    public int getSelectorValue() { return _selector.getValue(); }
}
