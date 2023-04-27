/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Jack extends HBox {

    protected final Circle _circle;

    protected Jack(
        final double radius,
        final Color ringColor
    ) {
        _circle = new Circle(radius);
        _circle.setFill(Color.BLACK);
        _circle.setStrokeWidth(4);
        _circle.setStroke(ringColor);
    }
}
