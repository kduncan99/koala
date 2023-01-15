/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.panels.old;

import com.kadware.koala.modules.DualNoiseModule;

public class DualNoisePanel extends ModulePanel {

    public DualNoisePanel(
        final DualNoiseModule module
    ) {
        super(module, PanelWidth.SINGLE, "Dual Noise");
    }

    @Override
    public void populateConnectors() {
//        var cPanel = getConnectorsPanel();
//
//        cPanel.addCell(new Cell());
//        cPanel.addCell(new Cell());
//
//        var leftPort = _module.getOutputPort(DualNoiseModule.LEFT_SIGNAL_OUTPUT_PORT);
//        var leftConn = new OutputConnector(CONTINUOUS_PORT_COLOR, leftPort);
//        cPanel.addCell(leftConn);
//
//        var rightPort = _module.getOutputPort(DualNoiseModule.RIGHT_SIGNAL_OUTPUT_PORT);
//        var rightConn = new OutputConnector(CONTINUOUS_PORT_COLOR, rightPort);
//        cPanel.addCell(rightConn);
    }

    @Override
    public void populateControls() {
        //  no controls on this module/panel
    }
}
