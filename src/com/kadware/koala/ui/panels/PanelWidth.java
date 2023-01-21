/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

/**
 * Indicates how many horizontal panel spaces are consumed by this panel.
 * The smallest (and the resolution) is one panel space.
 * Currently, the largest we support is three spaces, but this is arbitrary.
 */
public enum PanelWidth {
    SINGLE(1),
    DOUBLE(2),
    TRIPLE(3);

    public final int _spaceCount;

    PanelWidth(
        final int spaceCount
    ) {
        _spaceCount = spaceCount;
    }
}
