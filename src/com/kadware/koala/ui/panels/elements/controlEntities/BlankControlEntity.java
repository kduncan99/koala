/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities;

import com.kadware.koala.CellDimensions;

public class BlankControlEntity extends ControlEntityPane {

    private static final CellDimensions CELL_DIMENSIONS = new com.kadware.koala.CellDimensions(1, 1);
    public BlankControlEntity() {
        super(CELL_DIMENSIONS);
    }

    @Override
    public void repaint() {}
}
