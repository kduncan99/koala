/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.panels.old;

import com.kadware.koala.ui.old.elements.old.Cell;

import java.awt.*;

/**
 * This is the section of the ContentPanel which would contain input and output connectors.
 * All connectors are the same size, corresponding to the abstract CELL pixel size.
 */
class ConnectorsSubPanel extends Container {

    private static final int PIXELS_PER_MARGIN = 5;
    private static final int VERTICAL_CELLS = 2;

//    private static class Content extends Panel {
//
//        public Content(
//            final int rows,
//            final int columns
//        ) {
//            setLayout(new GridBagLayout());
//
//            var cw = columns * Cell.CELL_WIDTH_PIXELS;
//            var ch = rows * Cell.CELL_HEIGHT_PIXELS;
//            var cd = new Dimension(cw, ch);
//            setSize(cd);
//            setPreferredSize(cd);
//            setMinimumSize(cd);
//            setMaximumSize(cd);
//        }
//
//        @Override
//        public void paint(
//            final Graphics graphics
//        ) {
//            graphics.setColor(new Color(255, 0, 0));
//            graphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//            super.paint(graphics);
//        }
//    }
//
//    private final int _rows;
//    private final int _columns;

    public ConnectorsSubPanel(
        final PanelWidth panelWidth
    ) {
        var w = panelWidth._horizontalCellCount * Cell.CELL_WIDTH_PIXELS;
        var h = VERTICAL_CELLS * Cell.CELL_HEIGHT_PIXELS;
        var d = new Dimension(w, h);
        setSize(d);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
    }

//    public void addCell(
//        final Cell cell
//    ) {
//        _content.add(cell);
//        System.out.println("Sub " + getSize() + "  Content " + _content.getSize());
//    }

    @Override
    public void paint(
        final Graphics graphics
    ) {
        graphics.setColor(new Color(0, 0, 127));
        graphics.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
        super.paint(graphics);
    }
}
