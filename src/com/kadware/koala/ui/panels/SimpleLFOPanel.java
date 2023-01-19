/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.modules.Module;
import com.kadware.koala.modules.ModuleManager;
import com.kadware.koala.modules.SimpleLFOModule;
import com.kadware.koala.ui.panels.elements.connections.InputConnectionPane;
import com.kadware.koala.ui.panels.elements.connections.OutputConnectionPane;

public class SimpleLFOPanel extends ModulePanel {

    public SimpleLFOPanel() {
        super(ModuleManager.createModule(Module.ModuleType.SimpleLFO), PanelWidth.SINGLE, "LFO");
    }

    @Override
    public void populateControls() {
        //TODO
        //  wave selector
        //  range (-5, +0) switch
        //  coarse frequency control
        //  fine frequency control
        //  frequency indicator
        //  reset button
        //  meter indicator
        //  reset trigger input
        /*
         *    -----------
         *    |FreqDisp |
         *    -----------
         *    |Crse|Fine|
         *    -----------
         *    |Wave|Rnge|
         *    -----------
         *    |PWdt|    |
         *    -----|    |
         *    |    |Metr|
         *    -----|    |
         *    |Rset|    |
         *    -----------
         */
    }

    @Override
    public void populateConnections() {
        var section = getConnectionsSection();
        var input = new InputConnectionPane("Reset", getModule().getInputPort(SimpleLFOModule.RESET_INPUT_PORT));
        section.setInputConnection(0, input);
        var output = new OutputConnectionPane("Signal", getModule().getOutputPort(SimpleLFOModule.OUTPUT_PORT));
        section.setOutputConnection(0, output);
    }

    public SimpleLFOModule getModule() {
        return (SimpleLFOModule) _module;
    }
}
