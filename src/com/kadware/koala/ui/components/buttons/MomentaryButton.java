/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.buttons;

import com.kadware.koala.PixelDimensions;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MomentaryButton extends Button {

    private static final Font LEGEND_FONT = new Font(12);
    protected static final int LEGEND_INSET = 2;

    private final Pane _legend;
    private final int _xLayout;
    private final int _yLayout;

    public MomentaryButton(
        final int identifier,
        final PixelDimensions dimensions,
        final Pane legend
    ) {
        super(identifier, dimensions);

        var xDelta = dimensions.getWidth() - legend.getPrefWidth();
        var yDelta = dimensions.getHeight() - legend.getPrefHeight();
        _legend = legend;
        _xLayout = (int)(xDelta / 2);
        _yLayout = (int)(yDelta / 2);
        _legend.setLayoutX(_xLayout);
        _legend.setLayoutY(_yLayout);
        getChildren().add(_legend);
    }

    public MomentaryButton(
        final int identifier,
        final PixelDimensions dimensions,
        final String legend,
        final Color color
    ) {
        this(identifier, dimensions, getLegendPane(dimensions, legend, color));
    }

    private static Pane getLegendPane(
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

    @Override
    protected void mouseClicked(MouseEvent event) {
        //  TODO this should callback something somewhere
    }

    @Override
    protected void mousePressed(MouseEvent event) {
        super.mousePressed(event);
        _legend.setLayoutX(_xLayout + 1);
        _legend.setLayoutY(_yLayout + 1);
    }

    @Override
    protected void mouseReleased(MouseEvent event) {
        super.mouseReleased(event);
        _legend.setLayoutX(_xLayout);
        _legend.setLayoutY(_yLayout);
    }
}
