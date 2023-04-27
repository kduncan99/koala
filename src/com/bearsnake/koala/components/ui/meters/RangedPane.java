/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.meters;

import com.bearsnake.koala.DoubleRange;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RangedPane extends Pane {

    private final Color _color;
    private final DoubleRange _range;

    public RangedPane(
        final DoubleRange range,
        final Color color
    ) {
        _color = color;
        _range = range;

        var bgColor = color.darker().darker().darker();
        setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public Color getColor() { return _color; }
    public DoubleRange getRange() { return _range; }

    /**
     * Input is a value relative to the given range.
     * We clip the value to the range, then normalize it such that it ranges from 0.0 to 1.0,
     * where an input at range low value produces 0.0, while an input at range high value produces 1.0.
     */
    protected double getClippedNormalizedValue(
        final double inp
    ) {
        return _range.normalizeValue(_range.clipValue(inp));
    }

    /**
     * Given a range-relative input value, we calculate the x-coordinate to which it corresponds
     * for a horizontal gradient pane.
     */
    protected double getXCoordinateFor(
        final double inp
    ) {
        return getClippedNormalizedValue(inp) * getPrefWidth();
    }

    /**
     * Given a range-relative input value, we calculate the y-coordinate to which it corresponds
     * for a vertical (upside-down) gradient pane.
     */
    protected double getYCoordinateFor(
        final double inp
    ) {
        return (1.0 - getClippedNormalizedValue(inp)) * getPrefHeight();
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        setClip(new Rectangle(width, height));
    }
}
