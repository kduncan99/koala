/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.Koala;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class VerticalGradientPane extends GradientPane {

    public VerticalGradientPane(
        final DoubleRange range,
        final Color color,
        final double[] tickPoints,
        final double[] labelPoints,
        final String labelFormat
    ) {
        super(range, color, tickPoints, labelPoints, labelFormat);
    }

    @Override
    public void drawGradient() {
        var width = getPrefWidth();
        var height = getPrefHeight();
        var canvas = new Canvas(width, height);
        var gc = canvas.getGraphicsContext2D();

        //  ticks
        gc.setStroke(getColor());
        var x1 = width * 0.7;
        var x2 = width - 1;
        for (var tp : getTickPoints()) {
            var y = getYCoordinateFor(tp);
            gc.strokeLine(x1, y, x2, y);
        }

        //  labels
        gc.setFont(GRADIENT_FONT);
        gc.setFill(getColor());

        var text = new Text("H");
        text.setFont(GRADIENT_FONT);
        var textBounds = text.getLayoutBounds();
        var textAscent = 0 - (textBounds.getMinY());

        var x = 0.0;
        for (var lp : getLabelPoints()) {
            var y = getYCoordinateFor(lp);
            y += textAscent / 2;
            y = Koala.getBounded(textAscent, y, height);
            gc.fillText(String.format(getLabelFormat(), lp), x, y);
        }

        getChildren().add(canvas);
    }
}
