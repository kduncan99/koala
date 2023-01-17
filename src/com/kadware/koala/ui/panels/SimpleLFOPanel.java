/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.modules.Module;
import com.kadware.koala.modules.ModuleManager;
import com.kadware.koala.modules.SimpleLFOModule;

public class SimpleLFOPanel extends ModulePanel {

    public SimpleLFOPanel() {
        super(ModuleManager.createModule(Module.ModuleType.SimpleLFO), PanelWidth.SINGLE, "LFO");
    }

    @Override
    public void populateControls() {
        //TODO
        //  wave selector
        //  low bias (-5, +0)
        //  dual VU/PEAK meters
        //  test-tone push-on-off button
        //  dim push-on-off button
    }

    @Override
    public void populateConnections() {
        //TODO
    }

    public SimpleLFOModule getModule() {
        return (SimpleLFOModule) _module;
    }
}
