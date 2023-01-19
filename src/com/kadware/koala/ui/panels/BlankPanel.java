/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

public class BlankPanel extends Panel {

    public BlankPanel(
        final PanelWidth panelWidth
    ) {
        super(panelWidth, "- blank -");
    }

    @Override
    public void repaint() {}
}
