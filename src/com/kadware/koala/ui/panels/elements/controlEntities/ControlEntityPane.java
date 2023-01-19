/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities;

import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;

public abstract class ControlEntityPane extends GridPane {

    public static final int HORIZONTAL_PIXELS = 40;
    public static final int VERTICAL_PIXELS = 55;
    public static final BackgroundFill BACKGROUND_FILL
        = new BackgroundFill(Panel.PANEL_CELL_BACKGROUND_COLOR, null, new Insets(1));
    public static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    protected final int _horizontalCellCount;
    protected final int _verticalCellCount;

    public ControlEntityPane(
        final int horizontalCellCount,
        final int verticalCellCount
    ) {
        _horizontalCellCount = horizontalCellCount;
        _verticalCellCount = verticalCellCount;

        int width = _horizontalCellCount * HORIZONTAL_PIXELS;
        int height = _verticalCellCount * VERTICAL_PIXELS;
        setPrefSize(width, height);
        setBackground(BACKGROUND);
    }

    //  Can only be invoked on the Application thread
    public abstract void paint();
}
