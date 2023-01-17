/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ControlsSection extends GridPane {

    private static final int VERTICAL_CELLS = 6;

    private final PanelWidth _panelWidth;

    public ControlsSection(
        final PanelWidth panelWidth
    ) {
        _panelWidth = panelWidth;

        var hCells = (_panelWidth._spaceCount * Panel.PIXELS_PER_PANEL_SPACE_WIDTH) / Panel.PIXELS_PER_CELL_EDGE;
        var w = hCells * Panel.PIXELS_PER_CELL_EDGE;
        var h = VERTICAL_CELLS * Panel.PIXELS_PER_CELL_EDGE;
        setPrefSize(w, h);

        var c = new Canvas(w, h);
        var gc = c.getGraphicsContext2D();
        gc.setFill(Panel.PANEL_TRIM_COLOR);
        gc.fillRect(0, 0, w, h);
        gc.setFill(Panel.PANEL_SECTION_BACKGROUND_COLOR);
        gc.fillRect(1, 1, w - 2, h - 3);
        getChildren().add(c);
    }
}
