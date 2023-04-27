/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.knobs;

import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.messages.components.EncoderKnobComponentMessage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An encoder is a knob with no movement limits - it is continuously rotatable
 * (by using scroll up/down), and it can also be pressed.
 */
public class Encoder extends Knob {

    public Encoder(
        final PixelDimensions dimensions,
        final Color color
    ) {
        super(dimensions, color);
    }

    @Override
    protected void drawDetail(
        final GraphicsContext gc
    ) {
        var w = gc.getCanvas().getWidth();
        var h = gc.getCanvas().getHeight();

        gc.setStroke(getBackgroundColor());
        gc.setLineWidth(1.0);
        var rectWidth = w / 3;
        var rectHeight = h / 3;
        var rectX = (w - rectWidth) / 2;
        var rectY = (h - rectHeight) / 2;
        gc.strokeRect(rectX, rectY, rectWidth, rectHeight);
    }

    @Override
    protected void positionDecrement() {
        var r = getRotate();
        r -= 4;
        if (r < 0)
            r += 360;
        setRotate(r);
        notifyListeners(new EncoderKnobComponentMessage(EncoderKnobComponentMessage.Direction.COUNTER_CLOCK_WISE));
    }

    @Override
    protected void positionIncrement() {
        var r = getRotate();
        r += 4;
        if (r >= 360)
            r -= 360;
        setRotate(r);
        notifyListeners(new EncoderKnobComponentMessage(EncoderKnobComponentMessage.Direction.CLOCK_WISE));
    }
}
