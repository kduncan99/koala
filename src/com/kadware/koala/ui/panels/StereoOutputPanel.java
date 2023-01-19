/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.modules.Module;
import com.kadware.koala.modules.ModuleManager;
import com.kadware.koala.modules.StereoOutputModule;
import com.kadware.koala.ui.panels.elements.connections.InputConnectionPane;

public class StereoOutputPanel extends ModulePanel {

    public StereoOutputPanel() {
        super(ModuleManager.createModule(Module.ModuleType.StereoOutput), PanelWidth.SINGLE, "Output");
    }

    @Override
    public void populateControls() {
        //TODO
        //  master volume (to be added to module)
        //  blend control (to be added to module)
        //  dual VU/PEAK meters
        //  test-tone push-on-off button
        //  dim push-on-off button
    }

    @Override
    public void populateConnections() {
        var section = getConnectionsSection();
        var leftIn = new InputConnectionPane("Left", getModule().getInputPort(StereoOutputModule.LEFT_SIGNAL_INPUT_PORT));
        var rightIn = new InputConnectionPane("Right", getModule().getInputPort(StereoOutputModule.RIGHT_SIGNAL_INPUT_PORT));
        section.setInputConnection(0, leftIn);
        section.setInputConnection(1, rightIn);
    }

    public StereoOutputModule getModule() {
        return (StereoOutputModule) _module;
    }

    @Override
    public void repaint() {
        //  TODO
    }
}
