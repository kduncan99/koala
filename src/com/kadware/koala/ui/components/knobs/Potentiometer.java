/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.knobs;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.Koala;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.messages.PotentiometerKnobMessage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A knob control with absolute positioning and a minimum and maximum position.
 * The Potentiometer sends the PotentiometerKnobMessage, with a value corresponding to the knob's absolute position.
 * Position ranges from 0.0 to 1.0, inclusive.
 * <p>
 * We set the visual perception of the knob such that 0.0 is at 10 degrees, and 1.0 is at 350 degrees.
 * The visual hash line, if it was at 0 degrees, would be pointing straight down.
 */
public class Potentiometer extends Knob {

    private static final double POSITION_DELTA = 0.02;
    private static final DoubleRange ROTATION_RANGE = new DoubleRange(20.0, 340.0); //  in degrees

    private double _position;   //  from 0.0 to 1.0

    public Potentiometer(
        final int identifier,
        final PixelDimensions dimensions,
        final Color color
    ) {
        super(identifier, dimensions, color);
        setPosition(0.0);
    }

    @Override
    protected void drawDetail(
        final GraphicsContext gc
    ) {
        var w = gc.getCanvas().getWidth();
        var h = gc.getCanvas().getHeight();

        gc.setStroke(getBackgroundColor());
        var x = w / 2;
        var y1 = h * .6;
        var y2 = (h / 2) + getRadius();
        gc.strokeLine(x, y1, x, y2);
    }

    @Override
    protected void positionDecrement() {
        setPosition(Koala.POSITIVE_RANGE.clipValue(_position - POSITION_DELTA));
        notifyListeners(new PotentiometerKnobMessage(this, getIdentifier(), _position));
    }

    @Override
    protected void positionIncrement() {
        setPosition(Koala.POSITIVE_RANGE.clipValue(_position + POSITION_DELTA));
        notifyListeners(new PotentiometerKnobMessage(this, getIdentifier(), _position));
    }

    public void setPosition(
        final double position
    ) {
        var effective = Koala.POSITIVE_RANGE.clipValue(position);
        var rotation = ROTATION_RANGE.scaleValue(effective);
        setRotate(rotation);
        _position = position;
    }
}
