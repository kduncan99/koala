/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.knobs;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.Component;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Represents a physical knob of some type.
 * A Knob is always subclassed for more specific attributes and functionality.
 * The base class has a rotational position based on the angle of counter-clockwise rotation.
 */
public abstract class Knob extends Component {

    private final Color _bgColor;   //  face of the knob
    private final Color _fgColor;   //  edge and any markings on the knob
    private final double _radius;   //  radius of the drawn knob

    private double _lastScreenY;

    public Knob(
        final int identifier,
        final PixelDimensions dimensions,
        final Color color
    ) {
        super(identifier);

        _bgColor = color.darker().darker().darker();
        _fgColor = color.brighter();
        _radius = Math.min(dimensions.getWidth(), dimensions.getHeight()) * 0.3;

        setPrefSize(dimensions.getWidth(), dimensions.getHeight());

        var canvas = new Canvas(dimensions.getWidth(), dimensions.getHeight());
        var gc = canvas.getGraphicsContext2D();

        var xpos = (dimensions.getWidth() - 2 * _radius) / 2.0;
        var ypos = (dimensions.getWidth() - 2 * _radius) / 2.0;
        gc.setStroke(_bgColor);
        gc.setFill(_fgColor);
        gc.fillOval(xpos, ypos, dimensions.getWidth() - 2 * xpos, dimensions.getHeight() - 2 * ypos);
        gc.strokeOval(xpos, ypos, dimensions.getWidth() - 2 * xpos, dimensions.getHeight() - 2 * ypos);
        drawDetail(gc);

        getChildren().add(canvas);

        setOnMouseDragged(this::mouseDragged);
        setOnMousePressed(this::mousePressed);
    }

    protected Color getBackgroundColor() { return _bgColor; }
    protected Color getColor() { return _fgColor; }
    protected double getRadius() { return _radius; }

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

    protected abstract void drawDetail(final GraphicsContext gc);
    protected abstract void positionDecrement();
    protected abstract void positionIncrement();
}
