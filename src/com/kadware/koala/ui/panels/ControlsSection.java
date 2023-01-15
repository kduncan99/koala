/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public abstract class ControlsSection extends StackPane {

    private static final int PIXELS_PER_SECTION_HEIGHT = 500;

    public ControlsSection(
        final PanelWidth panelWidth
    ) {
        var w = (panelWidth._spaceCount * Panel.PIXELS_PER_PANEL_SPACE_WIDTH) - (2 * Panel.MARGIN_PIXELS);
        var h = PIXELS_PER_SECTION_HEIGHT;
        setPrefSize(w, h);

        var c = new Canvas(w, h);
        var gc = c.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, w, h);
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(1, 1, w - 2, h - 3);
        getChildren().add(c);
        getChildren().add(createContentGroup());
    }

    public abstract Group createContentGroup();
}
