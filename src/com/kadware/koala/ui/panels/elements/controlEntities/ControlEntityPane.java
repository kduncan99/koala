/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;

public abstract class ControlEntityPane extends GridPane {

    public static final int HORIZONTAL_PIXELS_PER_CELL = 40;
    public static final int VERTICAL_PIXELS_PER_CELL = 55;
    public static final BackgroundFill BACKGROUND_FILL
        = new BackgroundFill(Panel.PANEL_CELL_BACKGROUND_COLOR, null, new Insets(1));
    public static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    protected final CellDimensions _cellDimensions;
    protected final PixelDimensions _pixelDimensions;

    public ControlEntityPane(
        final CellDimensions cellDimensions
    ) {
        _cellDimensions = cellDimensions;
        _pixelDimensions = toPixelDimensions(cellDimensions);

        setPrefSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());
        setBackground(BACKGROUND);
    }

    //  Can only be invoked on the Application thread
    public abstract void repaint();

    public int getHorizontalCellCount() { return _cellDimensions.getWidth(); }
    public int getVerticalCellCount() { return _cellDimensions.getHeight(); }

    public static PixelDimensions toPixelDimensions(
        final CellDimensions cellDimensions
    ) {
        var w = cellDimensions.getWidth() * HORIZONTAL_PIXELS_PER_CELL;
        var h = cellDimensions.getHeight() * VERTICAL_PIXELS_PER_CELL;
        return new PixelDimensions(w, h);
    }
}
