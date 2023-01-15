/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.elements;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * A statically-sized graphic element which depicts a physical input or output connection.
 */
public class ConnectorJack extends StackPane {

    public static final int EDGE_PIXELS = 20;

    public ConnectorJack(
        final Color color
        //  TODO mouse properties
    ) {
        setPrefSize(EDGE_PIXELS, EDGE_PIXELS);
        setMaxSize(EDGE_PIXELS, EDGE_PIXELS);
        setMinSize(EDGE_PIXELS, EDGE_PIXELS);

        var center = EDGE_PIXELS * 0.5;
        var radius = EDGE_PIXELS * 0.4;
        var ring = new Ellipse(center, center, radius, radius);
        ring.setFill(color);

        var radius2 = EDGE_PIXELS * 0.2;
        var hole = new Ellipse(center, center, radius2, radius2);
        hole.setFill(Color.BLACK);

        //  TODO onMouseDragEnteredProperty
        //  TODO onMouseDragReleasedProperty
        //  TODO onMousePressedProperty

        getChildren().addAll(ring, hole);
    }
}
