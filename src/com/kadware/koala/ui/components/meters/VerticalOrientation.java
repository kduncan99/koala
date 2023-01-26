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
 * Vertical: -----
 *           | | |  a -> gradient
 *           |a|b|  b -> graph
 *           | | |
 *           -----
 */
public class VerticalOrientation extends BaseOrientation {

    @Override
    public void applyOrientation(
        final GridPane parent,
        final GraphPane graphPane,
        final GradientPane gradientPane
    ) {
        var w = parent.getPrefWidth() / 2;
        var h = parent.getPrefHeight();
        graphPane.setPrefSize(w, h);
        gradientPane.setPrefSize(w, h);
        parent.add(gradientPane, 0, 0, 1, 1);
        parent.add(graphPane, 1, 0, 1, 1);
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
        var x1 = width * 0.7;
        var x2 = width - 1;
        var denom = gradientPane.getRange().getHighValue() - gradientPane.getRange().getLowValue();
        for (var tp : gradientPane.getTickPositions()) {
            var normalizedPos = (tp - gradientPane.getRange().getLowValue()) / denom;  //  0.0 <= value <= 1.0
            var scaledPos = scalar.getScaledValue(normalizedPos);
            scaledPos = 1.0 - scaledPos;    //  flip it upside down, because our zero point is greatest y point
            var y = Koala.getBounded(0, scaledPos * height, height - 1);
            gc.strokeLine(x1, y, x2, y);
        }

        //  labels
        gc.setFont(GRADIENT_FONT);
        gc.setFill(gradientPane.getColor());
        var y = height - 1;
        for (var lp : gradientPane.getLabelPositions()) {
            var normalizedPos = (lp - gradientPane.getRange().getLowValue()) / denom;  //  0.0 <= value <= 1.0
            var scaledPos = scalar.getScaledValue(normalizedPos);
            scaledPos = 1.0 - scaledPos;    //  again, upside down

            var label = String.format(gradientPane.getFormatString(), lp);
            var text = new Text(label);
            text.setFont(GRADIENT_FONT);
            var textHeight = text.getLayoutBounds().getHeight();

            var x = (scaledPos * height) + (textHeight / 2.0);  //  centers the text on scaledPos
            x = Koala.getBounded(textHeight, x, height);        //  ensures we don't leak above or below
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
        return w / 2.0;
    }

    @Override
    public double getGraphCenterPointY(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final Scalar scalar,
        final double value
    ) {
        var h = dimensions.getHeight();
        var scaled = 1.0 - scalar.getScaledValue(value);
        return Koala.getBounded(0, scaled * h, h);
    }
}
