/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.knobs;

import com.kadware.koala.Koala;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.Component;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * Represents a physical knob of some type.
 * A Knob is always subclassed for more specific attributes and functionality.
 * The base class has a rotational position based on the angle of counter-clockwise rotation.
 */
public class Knob extends Component {

    private final Canvas _canvas;
    private final Color _bgColor;
    private final Color _fgColor;
    private double _position;

    //  TODO needs completely redone.
    public Knob(
        final int identifier,
        final PixelDimensions dimensions,
        final Color color
    ) {
        super(identifier);

        _bgColor = color.darker().darker().darker();
        _fgColor = color.brighter();

        //  Create a canvas and draw a circle, with a marker pointing down from past-center.
        //  This is a knob in standard position (offset of 0.0).
        //  In practice, the knob may be anywhere from 0.0 to 1.0, with increasing values corresponding
        //  to clock-wise rotations (and 1.0 is 360 degrees, which means it is visually equal to 0.0).
        _position = 0.0;
        _canvas = new Canvas(dimensions.getWidth(), dimensions.getHeight());

        var radius = dimensions.getWidth() * 0.75;
        var xpos = (dimensions.getWidth() - radius) / 2.0;
        var ypos = (dimensions.getWidth() - radius) / 2.0;
        var gc = _canvas.getGraphicsContext2D();
        gc.setStroke(_bgColor);
        gc.setFill(_fgColor);
        gc.fillOval(xpos, ypos, dimensions.getWidth() - 2 * xpos, dimensions.getHeight() - 2 * ypos);
        gc.strokeOval(xpos, ypos, dimensions.getWidth() - 2 * xpos, dimensions.getHeight() - 2 * ypos);

        gc.moveTo(dimensions.getWidth() / 2.0, dimensions.getHeight() * 0.75);
        gc.lineTo(dimensions.getWidth() / 2.0, dimensions.getHeight() - ypos);
        gc.stroke();
        getChildren().add(_canvas);

        //  TODO set up mouse capturing
    }

    //  only to be invoked on the Application thread
    public void adjustPosition(
        final double value
    ) {
        setPosition(_position + value);
    }

    //  only to be invoked on the Application thread
    public void setPosition(
        final double value
    ) {
        _position = Koala.getBounded(0.0, value, 1.0);
        var angle = (1.0 - _position) * 360;
        _canvas.setRotate(angle);
    }
}
