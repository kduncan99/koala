/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.knobs;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.messages.EncoderKnobMessage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An encoder is a knob with no movement limits - it is continuously rotatable
 * (by using scroll up/down), and it can also be pressed.
 * The Encoder sends the EncoderKnobMessage, with one of the following values:
 *      ENCODER_UP
 *      ENCODER_DOWN
 *      ENCODER_CLICKED
 *      ENCODER_PRESSED
 *      ENCODER_RELEASED
 */
public class Encoder extends Knob {

    private final EncoderKnobMessage _clockwiseMessage;
    private final EncoderKnobMessage _counterClockwiseMessage;

    public Encoder(
        final int identifier,
        final PixelDimensions dimensions,
        final Color color
    ) {
        super(identifier, dimensions, color);
        _clockwiseMessage = new EncoderKnobMessage(this,
                                                   identifier,
                                                   EncoderKnobMessage.Direction.CLOCK_WISE);
        _counterClockwiseMessage = new EncoderKnobMessage(this,
                                                          identifier,
                                                          EncoderKnobMessage.Direction.COUNTER_CLOCK_WISE);
    }

    @Override
    protected void drawDetail(
        final GraphicsContext gc
    ) {
        var w = gc.getCanvas().getWidth();
        var h = gc.getCanvas().getHeight();

        gc.setStroke(getBackgroundColor());
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
        notifyListeners(_counterClockwiseMessage);
    }

    @Override
    protected void positionIncrement() {
        var r = getRotate();
        r += 4;
        if (r >= 360)
            r -= 360;
        setRotate(r);
        notifyListeners(_clockwiseMessage);
    }
}
