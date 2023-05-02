/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class PendingWire extends Wire {

    public PendingWire(
        final Point2D initialPoint
    ) {
        super(initialPoint, initialPoint, Color.BLACK);
        getStrokeDashArray().add(5.0);
    }

    public void updateTerminalPoint(
        final Point2D point
    ) {
        setEndX(point.getX());
        setEndY(point.getY());
    }
}
