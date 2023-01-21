/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * ---------
 * |display|
 * |-------|
 * |legend |
 * ---------
 *
 * Generally, it's one cell high and one or more cells wide.
 */
public class DigitalReadout extends IndicatorPane {

    private final Label _display;
    private String _text;

    public DigitalReadout(
        final CellDimensions cellDimensions,
        final String legend,
        final Color color
    ) {
        super(cellDimensions);
        var bgColor = color.darker().darker().darker();

        var halfHeight = _pixelDimensions.getHeight() / 2;
        _display = new Label();
        _display.setTextFill(color.brighter());
        _display.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        _display.setPrefSize(_pixelDimensions.getWidth(), halfHeight);
        _display.setAlignment(Pos.BASELINE_CENTER);

        var caption = new Label(legend);
        caption.setTextFill(Panel.PANEL_LEGEND_COLOR);
        caption.setPrefSize(_pixelDimensions.getWidth(), halfHeight);
        caption.setAlignment(Pos.BASELINE_CENTER);

        add(_display, 0, 0);
        add(caption, 0, 1);
    }

    public void setText(
        final String text
    ) {
        _text = text;
    }

    @Override
    public void repaint() {
        _display.setText(_text);
    }
}
