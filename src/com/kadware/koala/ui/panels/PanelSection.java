/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import javafx.geometry.Insets;
import javafx.scene.layout.*;

public abstract class PanelSection extends GridPane {

    public static final int HORIZONTAL_CELLS_PER_SPACE = 2;

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

    protected final int _horizontalCellCount;
    protected final PanelWidth _panelWidth;

    protected PanelSection(
        final PanelWidth panelWidth
    ) {
        setPadding(new Insets(2));
        setBorder(BORDER);
        setBackground(BACKGROUND);
        _panelWidth = panelWidth;
        _horizontalCellCount = _panelWidth._spaceCount * HORIZONTAL_CELLS_PER_SPACE;
    }
}
