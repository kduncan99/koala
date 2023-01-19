/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators;

import com.kadware.koala.ui.panels.elements.controlEntities.ControlEntityPane;

public abstract class IndicatorPane extends ControlEntityPane {

    protected IndicatorPane(
        int horizontalCellCount,
        int verticalCellCount
    ) {
        super(horizontalCellCount, verticalCellCount);
    }
}
