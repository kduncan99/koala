/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.modules.Module;
import com.kadware.koala.modules.ModuleManager;
import com.kadware.koala.modules.StereoOutputModule;
import com.kadware.koala.ui.elements.InputConnectorPane;

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
        //TODO
        //  left, right inputs
        var leftIn = new InputConnectorPane("Left", getModule().getInputPort(StereoOutputModule.LEFT_SIGNAL_INPUT_PORT));
        var rightIn = new InputConnectorPane("Right", getModule().getInputPort(StereoOutputModule.RIGHT_SIGNAL_INPUT_PORT));
        _connectionsPane.add(leftIn, 0, 1);
        _connectionsPane.add(rightIn, 1, 1);
//        getModule().enableTestTone(440);
    }

    public StereoOutputModule getModule() {
        return (StereoOutputModule) _module;
    }
}
