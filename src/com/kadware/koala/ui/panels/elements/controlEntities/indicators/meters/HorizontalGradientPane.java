/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.Koala;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HorizontalGradientPane extends GradientPane {

    public static final Font FONT = new Font(8);

    public HorizontalGradientPane(
        final PixelDimensions dimensions,
        final Range<Double> range,
        final GradientInfo info
    ) {
        super(dimensions, range, info);
    }

    @Override
    protected void drawCanvas(
        final Canvas canvas,
        final Range<Double> meterRange,
        final GradientInfo info
    ) {
        var gc = canvas.getGraphicsContext2D();
        gc.setStroke(info._color);

        var w = canvas.getWidth();
        var h = canvas.getHeight();
        var y = h * 0.1;
        var tickGap = (w - 2 * INSET_PIXELS) / (info._numberOfTickMarks - 1);
        for (double x = INSET_PIXELS; x < w - (2 * INSET_PIXELS); x += tickGap) {
            gc.moveTo(x, 0);
            gc.lineTo(x, y);
            gc.stroke();
        }

        var x = w - INSET_PIXELS;
        gc.moveTo(x, 0);
        gc.lineTo(x, y);
        gc.stroke();

        gc.setFill(info._color);
        gc.setFont(FONT);
        var xLimit = w - (2 * INSET_PIXELS);
        y = (h * 0.1) + 8;

        for (var lpt : info._labelPoints) {
            var text = String.format("%s%3.1f", ((lpt > 0) ? "+" : ""), lpt);
            Text t = new Text(text);
            t.setFont(FONT);
            var pixels = t.getLayoutBounds().getWidth() - 2;

            x = findGraphCoordinate(lpt) - (pixels / 2.0);
            x = Koala.getBounded(INSET_PIXELS, x, (xLimit - pixels));
            gc.fillText(text, x, y);
        }
        System.out.println();
    }

    /**
     * Given a raw value, relative to the established low/high range,
     * we produce a coordinate relative to the left/top edge of the graph which
     * represents that value (clipping to min/max if/as necessary)
     */
    protected double findGraphCoordinate(
        final double rawValue
    ) {
        var ratio = (rawValue - _range.getLowValue()) / (_range.getHighValue() - _range.getLowValue());
        ratio = Koala.getBounded(0.0, ratio, 1.0);
        return (ratio * getPrefWidth());
    }
}
