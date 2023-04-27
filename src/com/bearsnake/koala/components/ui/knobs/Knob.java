/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.knobs;

import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.ActiveComponent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a physical knob of some type.
 * A Knob is always subclassed for more specific attributes and functionality.
 * The base class has a rotational position based on the angle of counter-clockwise rotation.
 */
public abstract class Knob extends ActiveComponent {

    private final Color _bgColor;   //  face of the knob
    private final Canvas _canvas;
    private final Color _fgColor;   //  edge and any markings on the knob
    private final double _radius;   //  radius of the drawn knob

    private double _lastScreenY;

    /**
     * Represents and presents an image of a knob of some type.
     * Specifics are applied by subclasses.
     * @param dimensions Pixel dimensions within which this knob must fit
     * @param color Base color of the knob - the face will this color while the edge and any other markings will be a
     *              darker version of the color.
     */
    public Knob(
        final PixelDimensions dimensions,
        final Color color
    ) {
        _bgColor = color.darker().darker();
        _fgColor = color;
        _radius = Math.min(dimensions.getWidth(), dimensions.getHeight()) * 0.4;

        setMinSize(dimensions.getWidth(), dimensions.getHeight());
        setMaxSize(dimensions.getWidth(), dimensions.getHeight());
        setPrefSize(dimensions.getWidth(), dimensions.getHeight());

        _canvas = new Canvas(dimensions.getWidth(), dimensions.getHeight());
        var gc = _canvas.getGraphicsContext2D();

        var xpos = (dimensions.getWidth() - 2 * _radius) / 2.0;
        var ypos = (dimensions.getHeight() - 2 * _radius) / 2.0;
        gc.setStroke(_bgColor);
        gc.setLineWidth(1.0);
        gc.setFill(_fgColor);
        gc.fillOval(xpos, ypos, dimensions.getWidth() - 2 * xpos, dimensions.getHeight() - 2 * ypos);
        gc.setLineWidth(3.0);
        gc.strokeOval(xpos, ypos, dimensions.getWidth() - 2 * xpos, dimensions.getHeight() - 2 * ypos);
        drawDetail(gc);

        getChildren().add(_canvas);
    }

    protected Color getBackgroundColor() { return _bgColor; }
    protected Canvas getCanvas() { return _canvas; }
    protected Color getForegroundColor() { return _fgColor; }
    protected double getRadius() { return _radius; }

    @Override
    public void mouseDragged(
        final MouseEvent event
    ) {
        if (event.getScreenY() > _lastScreenY)
            positionDecrement();
        else if (event.getScreenY() < _lastScreenY)
            positionIncrement();
        _lastScreenY = event.getScreenY();
    }

    public void mousePressed(
        final MouseEvent event
    ) {
        _lastScreenY = event.getScreenY();
    }

    /**
     * Invoked in order to let the subclass draw the detail of the knob face
     * @param gc GraphicsContext object into which the subclass should draw
     */
    protected abstract void drawDetail(final GraphicsContext gc);

    /**
     * Invoked by us to tell the subclass that the knob has been manipulated in a manner which represents
     * an increase in the value which is being represented.
     */
    protected abstract void positionDecrement();

    /**
     * Invoked by us to tell the subclass that the knob has been manipulated in a manner which represents
     * a decrease in the value which is being represented.
     */
    protected abstract void positionIncrement();
}
