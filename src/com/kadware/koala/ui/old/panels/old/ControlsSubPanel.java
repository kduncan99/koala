/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.panels.old;

import com.kadware.koala.ui.old.elements.old.Cell;

import java.awt.*;

/**
 * This is the section of the ContentPanel which would contain controls and indicators.
 */
class ControlsSubPanel extends Container {

    private static final int VERTICAL_CELLS = 6;

    public ControlsSubPanel(
        final PanelWidth panelWidth
    ) {
        setLayout(new GridBagLayout());

        var w = panelWidth._horizontalCellCount * Cell.CELL_WIDTH_PIXELS;
        var h = VERTICAL_CELLS * Cell.CELL_HEIGHT_PIXELS;
        var d = new Dimension(w, h);
        setSize(d);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
    }

    @Override
    public void paint(
        final Graphics graphics
    ) {
        graphics.setColor(new Color(0, 0, 127));
        graphics.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
        super.paint(graphics);
    }
}
