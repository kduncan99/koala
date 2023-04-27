/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.knobs;

import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.messages.components.PotentiometerKnobComponentMessage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * A knob control with absolute positioning and a minimum and maximum position.
 * The Potentiometer sends the PotentiometerKnobMessage, with a value corresponding to the knob's absolute position.
 * Position ranges from 0.0 to 1.0, inclusive.
 * <p>
 * We set the visual perception of the knob such that 0.0 is at 20 degrees, and 1.0 is at 340 degrees.
 * The visual hash line, if it was at 0 degrees, would be pointing straight down.
 */
public class Potentiometer extends Knob {

    private static final double POSITION_DELTA = 0.02;
    private static final DoubleRange ROTATION_RANGE = new DoubleRange(20.0, 340.0); //  in degrees

    private double _initialPosition;
    private double _position;   //  from 0.0 to 1.0

    /**
     * Creates a Potentiometer control
     * @param dimensions Pixel dimensions within which this knob must fit
     * @param color Base color of the knob - the face will this color while the edge and any other markings will be a
     *              darker version of the color.
     * @param initialPosition Initial position of the knob from 0.0 to 1.0
     */
    public Potentiometer(
        final PixelDimensions dimensions,
        final Color color,
        final double initialPosition
    ) {
        super(dimensions, color);
        _initialPosition = Koala.POSITIVE_RANGE.clipValue(initialPosition);
        establishPosition(_initialPosition);
    }

    /**
     * Creates a Potentiometer control with an initial position of 0.0.
     * @param dimensions Pixel dimensions within which this knob must fit
     * @param color Base color of the knob - the face will this color while the edge and any other markings will be a
     *              darker version of the color.
     */
    public Potentiometer(
        final PixelDimensions dimensions,
        final Color color
    ) {
        this(dimensions, color, 0.0);
    }

    @Override
    protected void drawDetail(
        final GraphicsContext gc
    ) {
        var w = getCanvas().getWidth();
        var h = getCanvas().getHeight();

        gc.setStroke(getBackgroundColor());
        gc.setLineWidth(1.0);
        var x = w / 2;
        var y1 = h * .6;
        var y2 = (h / 2) + getRadius();
        gc.strokeLine(x, y1, x, y2);
    }

    /**
     * Reports the current position of the Potentiometer
     */
    public double getPosition() {
        return _position;
    }

    @Override
    protected void mouseDoubleClicked(
        final MouseEvent event
    ) {
        establishPosition(_initialPosition);
        notifyListeners(new PotentiometerKnobComponentMessage(getPosition()));
    }

    @Override
    protected void positionDecrement() {
        establishPosition(Koala.POSITIVE_RANGE.clipValue(_position - POSITION_DELTA));
        notifyListeners(new PotentiometerKnobComponentMessage(getPosition()));
    }

    @Override
    protected void positionIncrement() {
        establishPosition(Koala.POSITIVE_RANGE.clipValue(_position + POSITION_DELTA));
        notifyListeners(new PotentiometerKnobComponentMessage(getPosition()));
    }

    private void establishPosition(
        final double position
    ) {
        //  this is separate from setPosition() because internally we want to avoid calling subclasses, sometimes.
        _position = Koala.POSITIVE_RANGE.clipValue(position);
        var rotation = ROTATION_RANGE.scaleValue(_position);
        getCanvas().setRotate(rotation);
    }

    /**
     * Sets the position of the potentiometer
     */
    public void setPosition(
        final double position
    ) {
        establishPosition(position);
    }
}
