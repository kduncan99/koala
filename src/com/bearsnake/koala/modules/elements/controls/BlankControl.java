/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.Koala;

public class BlankControl extends UIControl {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);

    public BlankControl() {
        super(CELL_DIMENSIONS);
        setBorder(Koala.BLANK_BORDER);
    }
}
