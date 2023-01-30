/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.ui.components.meters.Meter;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public abstract class MeterIndicator extends ControlPane {

    private final Meter _meter;

    protected MeterIndicator(
        final CellDimensions cellDimensions,
        final Meter meter,        //  this is a pane which contains the meter
        final String legend
    ) {
        super(cellDimensions, meter, legend);
        _meter = meter;
    }

    public Meter getMeter() { return _meter; }
}
