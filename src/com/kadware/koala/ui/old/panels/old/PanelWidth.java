/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.panels.old;

/**
 * Indicates how many horizontal panel spaces are consumed by this panel.
 * The smallest (and the resolution) is one panel space.
 * Currently, the largest we support is three spaces, but this is arbitrary.
 */
public enum PanelWidth {
    SINGLE(1, BasePanel.HORIZONTAL_CELLS_SINGLE),
    DOUBLE(2, BasePanel.HORIZONTAL_CELLS_DOUBLE),
    TRIPLE(3, BasePanel.HORIZONTAL_CELLS_TRIPLE);

    public final int _spaceCount;
    public final int _horizontalCellCount;

    PanelWidth(
        final int spaceCount,
        final int horizontalCellCount
    ) {
        _spaceCount = spaceCount;
        _horizontalCellCount = horizontalCellCount;
    }
}
