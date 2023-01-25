/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components;

import com.kadware.koala.Koala;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Models a simple circular LED which varies in intensity from black to the given color
 */
public class AnalogLED extends Pane {

    private final Circle _circle;
    private final Color _color;
    private final double _rangeLow;
    private final double _rangeHigh;
    private final double _range;
    private double _value;

    public AnalogLED(
        final int radius,
        final Color color,
        final double rangeLow,
        final double rangeHigh
    ) {
        _color = color;
        _rangeLow = rangeLow;
        _rangeHigh = rangeHigh;
        _range = _rangeHigh - _rangeLow;

        setPrefSize(2 * radius, 2 * radius);
        _circle = new Circle();
        _circle.setRadius(radius);
        _circle.setCenterX(radius);
        _circle.setCenterY(radius);
        _circle.setFill(color.brighter());

        getChildren().add(_circle);
    }

    public void setValue(
        final double value
    ) {
        _value = value;
    }

    //  Only invoke from the Application thread
    public void paint() {
        var ratio = _value / _range * 255;
        var red = Koala.getBounded(0, _color.getRed() * ratio, 255);
        var green = Koala.getBounded(0, _color.getGreen() * ratio, 255);
        var blue = Koala.getBounded(0, _color.getBlue() * ratio, 255);
        _circle.setFill(Color.rgb((int)red, (int)green, (int)blue));
    }
}
