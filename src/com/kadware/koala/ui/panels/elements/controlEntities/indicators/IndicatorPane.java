/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.ui.panels.elements.controlEntities.ControlEntityPane;

public abstract class IndicatorPane extends ControlEntityPane {

    protected IndicatorPane(
        final CellDimensions cellDimensions
    ) {
        super(cellDimensions);
    }
}
