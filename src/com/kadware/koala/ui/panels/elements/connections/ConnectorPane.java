/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ui.panels.Panel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ConnectorPane extends VBox {

    public static final int EDGE_PIXELS = 2 * ConnectorJack.VERTICAL_EDGE_PIXELS;
    public static final BackgroundFill BACKGROUND_FILL
        = new BackgroundFill(Panel.PANEL_CELL_BACKGROUND_COLOR, null, new Insets(1));
    public static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    //  For a blank pane
    protected ConnectorPane() {
        setPrefSize(EDGE_PIXELS, EDGE_PIXELS);
        setMaxSize(EDGE_PIXELS, EDGE_PIXELS);
        setMinSize(EDGE_PIXELS, EDGE_PIXELS);
        setBackground(BACKGROUND);
    }

    protected ConnectorPane(
        final String captionStr,
        final Color ringColor
    ) {
        this();

        var jack = new ConnectorJack(ringColor);
        var caption = new Label(captionStr);
        caption.setAlignment(Pos.BASELINE_CENTER);
        caption.setTextFill(Panel.PANEL_LEGEND_COLOR);
        caption.setPrefSize(EDGE_PIXELS, EDGE_PIXELS * 0.5);
        getChildren().addAll(jack, caption);
    }
}
