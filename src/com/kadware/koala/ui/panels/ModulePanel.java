/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.audio.modules.Module;

import java.io.Closeable;

public abstract class ModulePanel extends Panel implements Closeable {

    protected final Module _module;

    public ModulePanel(
        final Module module,
        final PanelWidth panelWidth,
        final String caption
    ) {
        super(panelWidth, caption);
        _module = module;

        populateControls();
        populateConnections();
    }

    public abstract void close();
    public abstract void populateControls();
    public abstract void populateConnections();
}
