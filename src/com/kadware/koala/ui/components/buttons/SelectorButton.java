/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.buttons;

import com.kadware.koala.PixelDimensions;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SelectorButton extends MomentaryButton {

    private final Pane[] _imagePanes;
    private int _currentSelection = 0;

    public SelectorButton(
        final PixelDimensions dimensions,
        final Pane[] imagePanes
    ) {
        super(dimensions, createParentPane(dimensions, imagePanes));
        _imagePanes = imagePanes;
        _currentSelection = 0;
        _imagePanes[_currentSelection].setVisible(true);
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

    private void incrementSelection() {
        _imagePanes[_currentSelection].setVisible(false);
        _currentSelection++;
        if (_currentSelection == _imagePanes.length)
            _currentSelection = 0;
        _imagePanes[_currentSelection].setVisible(true);
    }

    @Override
    protected void mouseClicked(
        final MouseEvent event
    ) {
        incrementSelection();
        super.mouseClicked(event);
    }
}
