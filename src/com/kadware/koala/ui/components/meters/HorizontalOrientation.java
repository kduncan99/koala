/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Koala;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.DoubleRange;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Horizontal:  ---------------
 *              |    graph    |
 *              ---------------
 *              |   gradient  |
 *              ---------------
 */
public class HorizontalOrientation extends BaseOrientation {

    @Override
    public void applyOrientation(
        final GridPane parent,
        final GraphPane graphPane,
        final GradientPane gradientPane
    ) {
        var w = parent.getPrefWidth();
        var h = parent.getPrefHeight() / 2;
        graphPane.setPrefSize(w, h);
        gradientPane.setPrefSize(w, h);
        parent.add(graphPane, 0, 0, 1, 1);
        parent.add(gradientPane, 0, 1, 1, 1);
    }

    @Override
    public void drawGradient(
        final GradientPane gradientPane,
        final Scalar scalar
    ) {
        var width = gradientPane.getPrefWidth();
        var height = gradientPane.getPrefHeight();
        var canvas = new Canvas(width, height);
        var gc = canvas.getGraphicsContext2D();

        //  ticks
        gc.setStroke(gradientPane.getColor());
        var y1 = 0;
        var y2 = height * 0.3;
        var denom = gradientPane.getRange().getHighValue() - gradientPane.getRange().getLowValue();
        for (var tp : gradientPane.getTickPositions()) {
            var normalizedPos = (tp - gradientPane.getRange().getLowValue()) / denom;  //  0.0 <= value <= 1.0
            var scaledPos = scalar.getScaledValue(normalizedPos);
            var x = Koala.getBounded(0, scaledPos * width, width - 1);
            gc.strokeLine(x, y1, x, y2);
        }

        //  labels
        gc.setFont(GRADIENT_FONT);
        gc.setFill(gradientPane.getColor());
        var y = height - 1;
        for (var lp : gradientPane.getLabelPositions()) {
            var normalizedPos = (lp - gradientPane.getRange().getLowValue()) / denom;  //  0.0 <= value <= 1.0
            var scaledPos = scalar.getScaledValue(normalizedPos);

            var label = String.format(gradientPane.getFormatString(), lp);
            var text = new Text(label);
            text.setFont(GRADIENT_FONT);
            var textWidth = text.getLayoutBounds().getWidth();

            var x = (scaledPos * width) - (textWidth / 2.0);    //  centers the text on scaledPos
            x = Koala.getBounded(0, x, width - textWidth);  //  ensures we don't leak left or right
            gc.fillText(label, x, y);
        }

        gradientPane.getChildren().add(canvas);
    }

    @Override
    public double getGraphCenterPointX(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final Scalar scalar,
        final double value
    ) {
        var w = dimensions.getWidth();
        return scalar.getScaledValue(range.normalizeValue(value)) * w;
    }

    @Override
    public double getGraphCenterPointY(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final Scalar scalar,
        final double value
    ) {
        var h = dimensions.getHeight();
        return h / 2.0;
    }
}
