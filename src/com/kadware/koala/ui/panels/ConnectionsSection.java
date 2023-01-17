/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.ui.panels.elements.connections.ConnectorPane;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class ConnectionsSection extends StackPane {

    public static final BackgroundFill BACKGROUND_FILL = new BackgroundFill(Panel.PANEL_SECTION_BACKGROUND_COLOR,
                                                                            CornerRadii.EMPTY,
                                                                            new Insets(1));
    public static final Background BACKGROUND = new Background(BACKGROUND_FILL);
    private static final BorderStroke BORDER_STROKE = new BorderStroke(Panel.PANEL_TRIM_COLOR,
                                                                       BorderStrokeStyle.SOLID,
                                                                       CornerRadii.EMPTY,
                                                                       new BorderWidths(1),
                                                                       new Insets(0));
    private static final Border BORDER = new Border(BORDER_STROKE);

    protected static final int VERTICAL_CELLS = 2;

    protected final int _horizontalCells;
    protected final PanelWidth _panelWidth;

    public ConnectionsSection(
        final PanelWidth panelWidth
    ) {
        setPadding(new Insets(2));
        setBorder(BORDER);
        setBackground(BACKGROUND);
        _panelWidth = panelWidth;
        _horizontalCells = (_panelWidth._spaceCount * Panel.PIXELS_PER_PANEL_SPACE_WIDTH) / ConnectorPane.EDGE_PIXELS;
    }
}
