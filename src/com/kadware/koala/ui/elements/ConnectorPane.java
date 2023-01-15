/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.elements;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ConnectorPane extends GridPane {

    public static final int EDGE_PIXELS = 2 * ConnectorJack.EDGE_PIXELS;

    protected enum GraphicPosition {
        GRAPHIC_ON_LEFT,
        GRAPHIC_ON_RIGHT
    }

    protected ConnectorPane() {
        setPrefSize(EDGE_PIXELS, EDGE_PIXELS);
        setMaxSize(EDGE_PIXELS, EDGE_PIXELS);
        setMinSize(EDGE_PIXELS, EDGE_PIXELS);
    }

    protected ConnectorPane(
        final String captionStr,
        final Color ringColor,
        final GraphicPosition graphicPosition
    ) {
        var graphic = new Label("➜");
        graphic.setTextAlignment(TextAlignment.CENTER);
        graphic.setPrefSize(ConnectorJack.EDGE_PIXELS, ConnectorJack.EDGE_PIXELS);
        graphic.setMinSize(ConnectorJack.EDGE_PIXELS, ConnectorJack.EDGE_PIXELS);
        graphic.setTextAlignment(TextAlignment.RIGHT);

        var jack = new ConnectorJack(ringColor);

        var caption = new Label(captionStr);
        caption.setTextAlignment(TextAlignment.CENTER);
        caption.setPrefSize(EDGE_PIXELS, ConnectorJack.EDGE_PIXELS);
        caption.setMinSize(EDGE_PIXELS, ConnectorJack.EDGE_PIXELS);

        add(graphic, graphicPosition == GraphicPosition.GRAPHIC_ON_LEFT ? 0 : 1, 0, 1, 1);
        add(jack, graphicPosition == GraphicPosition.GRAPHIC_ON_LEFT ? 1 : 0, 0, 1, 1);
        add(caption, 0, 1, 2, 1);

        setPrefSize(EDGE_PIXELS, EDGE_PIXELS);
        setMaxSize(EDGE_PIXELS, EDGE_PIXELS);
        setMinSize(EDGE_PIXELS, EDGE_PIXELS);
    }
}
