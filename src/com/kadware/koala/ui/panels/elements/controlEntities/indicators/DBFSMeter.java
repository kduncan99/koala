/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators;

/**
 * This is a vertically-oriented dbFS VU meter.
 * The layout is as such:
 * -----
 * | | |
 * |a|b|
 * | | |
 * -----
 * where a is a gradient text display with db gradients at periodic intervals
 *   and b is a canvas upon which we draw the meter.
 *
 * Since Indicator is a Pane, we have to set up an HBox inside ourselves to accomplish this.
 */
public class DBFSMeter extends Meter {

    public DBFSMeter(
        final int horizontalCellCount,
        final int verticalCellCount
    ) {
        super(horizontalCellCount, verticalCellCount, 0.0, 1.0);
    }

    @Override
    public void paint() {
        //  TODO
    }
}
