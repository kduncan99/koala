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
import com.kadware.koala.ui.panels.elements.controlEntities.indicators.DigitalReadout;
import javafx.scene.paint.Color;

public class SimpleLFOPanel extends ModulePanel {

    private DigitalReadout _frequencyDisplay;

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
        var section = getControlsSection();

        _frequencyDisplay = new DigitalReadout(2, 1, "Frequency", Color.GREEN);
        section.setControlEntity(0, 0, _frequencyDisplay);
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

    //  To be invoked only on the Application thread
    @Override
    public void repaint() {
        //  update frequency display
        var text = String.format("%6.3f Hz", ((SimpleLFOModule)_module).getBaseFrequency());
        _frequencyDisplay.setText(text);
        _frequencyDisplay.paint();

        //  TODO other indicators which might need updated
    }
}
