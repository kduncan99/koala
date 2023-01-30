/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.messages.IListener;
import com.kadware.koala.messages.Message;
import com.kadware.koala.audio.modules.ModuleManager;
import com.kadware.koala.audio.modules.ModuleType;
import com.kadware.koala.audio.modules.StereoOutputModule;
import com.kadware.koala.ui.panels.elements.connections.InputConnectionPane;
import com.kadware.koala.ui.panels.elements.controls.StereoDBFSIndicator;
import com.kadware.koala.ui.panels.elements.controls.TestToneSelector;
import com.kadware.koala.ui.panels.messages.TestToneMessage;

public class StereoOutputPanel extends ModulePanel implements IListener {

    private TestToneSelector _testToneSelector;
    private StereoDBFSIndicator _dbfsIndicator;

    public StereoOutputPanel() {
        super(ModuleManager.createModule(ModuleType.StereoOutput), PanelWidth.SINGLE, "dbFS");
    }

    @Override
    public void populateControls() {
        var section = getControlsSection();
        _testToneSelector = new TestToneSelector();
        _testToneSelector.registerListener(this);
        section.setControlEntity(0, 4, _testToneSelector);

        _dbfsIndicator = new StereoDBFSIndicator("Audio");
        section.setControlEntity(1, 0, _dbfsIndicator);
        //TODO
        //  master volume (to be added to module)
        //  blend control (to be added to module)
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
        var module = getModule();
        _dbfsIndicator.setValues(module.getLeftDBFSComponent().getDBFSValue(),
                                 module.getRightDBFSComponent().getDBFSValue());
    }

    @Override
    public void close() {
        _testToneSelector.unregisterListener(this);
    }

    @Override
    public void notify(Message message) {
        //  something on the panel has been updated
        if (message instanceof TestToneMessage ttm) {
            if (ttm.getFrequency() == 0)
                getModule().disableTestTone();
            else
                getModule().enableTestTone(ttm.getFrequency());
        }
    }
}
