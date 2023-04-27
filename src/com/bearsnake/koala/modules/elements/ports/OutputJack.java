/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * A graphic (and active) representation of an output connector.
 */
public class OutputJack extends Jack {

    public OutputJack(
        final double radius,
        final Color ringColor
    ) {
        super(radius, ringColor);
        var label = new Label("->");
        label.setTextFill(ringColor);
        getChildren().add(_circle);
        getChildren().add(label);
    }
}
