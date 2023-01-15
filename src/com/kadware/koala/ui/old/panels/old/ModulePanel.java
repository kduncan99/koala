/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.panels.old;

import com.kadware.koala.modules.Module;

import java.awt.*;

public abstract class ModulePanel extends BasePanel {

    protected static final Color CONTINUOUS_PORT_COLOR = new Color(0, 255, 0);
    protected static final Color LOGIC_PORT_COLOR = new Color(255, 127, 255);
    protected static final Color DISCRETE_PORT_COLOR = new Color(0, 127, 255);

    protected final Module _module;

    protected ModulePanel(
        final Module module,
        final PanelWidth panelWidth,
        final String caption
    ) {
        super(panelWidth, caption);
        _module = module;
        populateConnectors();
        populateControls();
    }

    public abstract void populateConnectors();
    public abstract void populateControls();
}
