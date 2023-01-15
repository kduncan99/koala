/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.ui.elements.ConnectorPane;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public abstract class Panel extends VBox {

    //  panel geometry
    static final int MARGIN_PIXELS = 5;
    static final Insets MARGIN_INSETS = new Insets(MARGIN_PIXELS, MARGIN_PIXELS, MARGIN_PIXELS, MARGIN_PIXELS);
    static final int PIXELS_PER_PANEL_SPACE_WIDTH = 100;

    //  cell geometry
    static final int PIXELS_PER_CELL_EDGE = 40; //  TODO maybe this could be more specific only to the controls panel

    private final String _caption;
    private final ConnectionsSection _connectionsSection;
    private final ControlsSection _controlsSection;
    private final PanelWidth _panelWidth;

    public Panel(
        final PanelWidth panelWidth,
        final String caption
    ) {
        _caption = caption;
        _panelWidth = panelWidth;

        var cap = new Label(_caption);
        _controlsSection = new ControlsSection();
        _connectionsSection = new ConnectionsSection();

        getChildren().add(cap);
        getChildren().add(_controlsSection);
        getChildren().add(_connectionsSection);
        setPadding(MARGIN_INSETS);
    }

    protected final ConnectionsSection getConnectionsSection() { return _connectionsSection; }
    protected final ControlsSection getControlsSection() { return _controlsSection; }
    public final PanelWidth getPanelWidth() { return _panelWidth; }

    //  ----------------------------------------------------------------------------------------------------------------

    public class ControlsSection extends GridPane {

        private static final int VERTICAL_CELLS = 6;

        public ControlsSection() {
            var hCells = (_panelWidth._spaceCount * PIXELS_PER_PANEL_SPACE_WIDTH) / PIXELS_PER_CELL_EDGE;
            var w = hCells * PIXELS_PER_CELL_EDGE;
            var h = VERTICAL_CELLS * PIXELS_PER_CELL_EDGE;
            setPrefSize(w, h);

            var c = new Canvas(w, h);
            var gc = c.getGraphicsContext2D();
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, w, h);
            gc.setFill(Color.LIGHTGREY);
            gc.fillRect(1, 1, w - 2, h - 3);
            getChildren().add(c);
        }
    }

    //  ----------------------------------------------------------------------------------------------------------------

    public class ConnectionsSection extends StackPane {

        protected static final int VERTICAL_CELLS = 2;

        protected final int _horizontalCells;

        public ConnectionsSection() {
            //  TODO not sure how this works... we want our width to match the panel width, which might be a bit
            //      more than the sum of the connector widths... maybe it's stretching us anyway?
            _horizontalCells = (_panelWidth._spaceCount * PIXELS_PER_PANEL_SPACE_WIDTH) / ConnectorPane.EDGE_PIXELS;
            var w = _horizontalCells * ConnectorPane.EDGE_PIXELS;
            var h = VERTICAL_CELLS * ConnectorPane.EDGE_PIXELS;
            setPrefSize(w, h);

            var c = new Canvas(w, h);
            var gc = c.getGraphicsContext2D();
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, w, h);
            gc.setFill(Color.LIGHTGREY);
            gc.fillRect(1, 1, w - 2, h - 3);
            getChildren().add(c);
        }
    }
}
