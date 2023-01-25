/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import javafx.scene.layout.Pane;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public abstract class MeterIndicator extends ControlPane {

    protected MeterIndicator(
        final CellDimensions cellDimensions,
        final Pane pane,
        final String legend
    ) {
        super(cellDimensions, pane, legend);
    }
}
