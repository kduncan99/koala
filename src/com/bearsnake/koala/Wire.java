/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;

public class Wire extends QuadCurve {

    public Wire(
        final Point2D point1,
        final Point2D point2,
        final Color color
    ) {
        setStartX(point1.getX());
        setStartY(point1.getY());
        setEndX(point2.getX());
        setEndY(point2.getY());
        setControlX((point1.getX() + point2.getX()) / 6.0);
        setControlY(Math.max(point1.getY(), point2.getY()) + 50.0);
        setStroke(color);
        setStrokeWidth(2.0);
        setFill(Color.WHITE);//TODO
        setMouseTransparent(true);
    }
}
