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

public class HorizontalGradientPane extends GradientPane {

    public HorizontalGradientPane(
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
        gc.setStroke(getColor().brighter().brighter());
        var y1 = 0;
        var y2 = height * 0.3;
        for (var tp : getTickPoints()) {
            var x = getXCoordinateFor(tp);
            gc.strokeLine(x, y1, x, y2);
        }

        //  labels
        gc.setFont(GRADIENT_FONT);
        gc.setFill(getColor().brighter().brighter());
        var y = height - 1;
        for (var lp : getLabelPoints()) {
            var x = getXCoordinateFor(lp);
            var label = String.format(getLabelFormat(), lp);
            var text = new Text(label);
            text.setFont(GRADIENT_FONT);
            var textWidth = text.getLayoutBounds().getWidth();
            x -= textWidth / 2.0;   //  center text around the x position
            x = Koala.getBounded(0, x, width - textWidth);  //  make sure we don't clip the text
            gc.fillText(label, x, y);
        }

        getChildren().add(canvas);
    }
}
