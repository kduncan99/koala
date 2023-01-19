/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public abstract class Panel extends VBox {

    //  panel geometry
    public static final int MARGIN_PIXELS = 5;
    public static final Insets MARGIN_INSETS = new Insets(MARGIN_PIXELS, MARGIN_PIXELS, MARGIN_PIXELS, MARGIN_PIXELS);
    public static final Color PANEL_SECTION_BACKGROUND_COLOR = Color.rgb(223, 223, 223);
    public static final Color PANEL_CELL_BACKGROUND_COLOR = Color.rgb(200, 200, 200);
    public static final Color PANEL_TRIM_COLOR = Color.BLACK;
    public static final Color PANEL_LEGEND_COLOR = Color.DARKBLUE;

    //  cell geometry
    static final int PIXELS_PER_CELL_EDGE = 40; //  TODO maybe this could be more specific only to the controls panel

    private final ConnectionsSection _connectionsSection;
    private final ControlsSection _controlsSection;
    private final PanelWidth _panelWidth;

    public Panel(
        final PanelWidth panelWidth,
        final String caption
    ) {
        _panelWidth = panelWidth;

        var cap = new Label(caption);
        _controlsSection = new ControlsSection(panelWidth);
        _connectionsSection = new ConnectionsSection(panelWidth);

        getChildren().add(cap);
        getChildren().add(_controlsSection);
        getChildren().add(_connectionsSection);
        setPadding(MARGIN_INSETS);
    }

    protected final ConnectionsSection getConnectionsSection() { return _connectionsSection; }
    protected final ControlsSection getControlsSection() { return _controlsSection; }
    public final PanelWidth getPanelWidth() { return _panelWidth; }

    public abstract void repaint();
}
