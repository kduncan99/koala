/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Models a simple circular LED
 */
public class DigitalLED extends Pane {

    private final Circle _on;
    private final Circle _off;
    private boolean _value;

    public DigitalLED(
        final int radius,
        final Color color
    ) {
        setPrefSize(2 * radius, 2 * radius);
        _on = new Circle();
        _on.setRadius(radius);
        _on.setCenterX(radius);
        _on.setCenterY(radius);
        _on.setFill(color.brighter());

        _off = new Circle();
        _off.setRadius(radius);
        _off.setCenterX(radius);
        _off.setCenterY(radius);
        _off.setFill(color.darker().darker());

        getChildren().add(_off);
    }

    public void setValue(
        final boolean value
    ) {
        _value = value;
    }

    //  Only invoke from the Application thread
    public void paint() {
        getChildren().add(_value ? _on : _off);
    }
}
