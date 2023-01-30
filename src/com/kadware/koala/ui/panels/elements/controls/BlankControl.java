/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;

public class BlankControl extends ControlPane {

    private static final CellDimensions CELL_DIMENSIONS = new com.kadware.koala.CellDimensions(1, 1);

    public BlankControl() {
        super(-1, CELL_DIMENSIONS, null, null);
    }
}
