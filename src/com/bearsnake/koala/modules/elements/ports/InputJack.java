/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * A graphic (and active) representation of an input connector.
 */
public class InputJack extends Jack {

    public InputJack(
        final double radius,
        final Color ringColor
    ) {
        super(radius, ringColor);

        var label = new Label("->");
        label.setTextFill(ringColor);
        getChildren().add(label);
        getChildren().add(_circle);
    }
}
