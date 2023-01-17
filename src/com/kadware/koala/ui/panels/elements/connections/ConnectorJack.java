/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

/**
 * A statically-sized graphic element which depicts a physical input or output connection.
 */
public class ConnectorJack extends HBox {

    public static final int HORIZONTAL_EDGE_PIXELS = 40;
    public static final int VERTICAL_EDGE_PIXELS = 20;

    public ConnectorJack(
        final Color ringColor
        //  TODO mouse properties
    ) {
        setPrefSize(HORIZONTAL_EDGE_PIXELS, VERTICAL_EDGE_PIXELS);
        setMaxSize(HORIZONTAL_EDGE_PIXELS, VERTICAL_EDGE_PIXELS);
        setMinSize(HORIZONTAL_EDGE_PIXELS, VERTICAL_EDGE_PIXELS);

        var graphic = new Label("➜");
        graphic.setPrefSize(HORIZONTAL_EDGE_PIXELS / 2.0, VERTICAL_EDGE_PIXELS);
        graphic.setTextFill(Panel.PANEL_LEGEND_COLOR);
        graphic.setAlignment(Pos.BASELINE_RIGHT);

        var xPos = HORIZONTAL_EDGE_PIXELS * 0.75;
        var yPos = VERTICAL_EDGE_PIXELS * 0.5;
        var radius = VERTICAL_EDGE_PIXELS * 0.35;
        var jack = new Circle(xPos, yPos, radius);
        jack.setStroke(ringColor);
        jack.setStrokeWidth(4.0);
        jack.setFill(Color.BLACK);

        getChildren().addAll(graphic, jack);

        //  TODO onMouseDragEnteredProperty
        //  TODO onMouseDragReleasedProperty
        //  TODO onMousePressedProperty
    }
}
