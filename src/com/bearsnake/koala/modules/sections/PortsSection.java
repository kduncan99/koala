/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.sections;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.modules.elements.ports.BlankPort;
import com.bearsnake.koala.modules.elements.ports.ActivePort;
import javafx.scene.shape.Rectangle;

/**
 * This module section contains a grid of connection cells.
 * We initialize the section to contain x*y cells according to the indicated cell counts,
 * then allow higher-level code to replace the blank cells with real input or output cells.
 */
public class PortsSection extends Section {

    public PortsSection(
        final CellDimensions cellDimensions
    ) {
        super(cellDimensions);

        for (int rx = 0; rx < cellDimensions.getHeight(); ++rx) {
            for (int cx = 0; cx < cellDimensions.getWidth(); ++cx) {
                add(new BlankPort(), cx, rx, 1, 1);
            }
        }

        var clipDims = ActivePort.determinePixelDimensions(cellDimensions);
        var clipRect = new Rectangle(clipDims.getWidth(), clipDims.getHeight());
        setClip(clipRect);
    }

    public void setConnection(
        final int leftGridCell,
        final int topGridCell,
        final ActivePort conn
    ) {
        add(conn, leftGridCell, topGridCell, 1,1);
    }

    //  Only for Application thread
    public void repaint() {
        for (var p : getChildren()) {
            if (p instanceof ActivePort conn) {
                conn.repaint();
            }
        }
    }
}
