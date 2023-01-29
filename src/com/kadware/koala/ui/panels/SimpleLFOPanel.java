/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.messages.IListener;
import com.kadware.koala.messages.Message;
import com.kadware.koala.audio.modules.ModuleType;
import com.kadware.koala.ui.panels.messages.WaveMessage;
import com.kadware.koala.audio.modules.ModuleManager;
import com.kadware.koala.audio.modules.SimpleLFOModule;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ui.panels.elements.connections.InputConnectionPane;
import com.kadware.koala.ui.panels.elements.connections.OutputConnectionPane;
import com.kadware.koala.ui.panels.elements.controls.*;
import javafx.scene.paint.Color;

public class SimpleLFOPanel extends ModulePanel implements IListener {

    private ControlValueIndicator _cvMeter;
    private FrequencyDisplay _frequencyDisplay;
    private LinearKnobControl _frequencyControl;
    private LinearKnobControl _pulseWidthControl;
    private LinearKnobControl _biasControl;
    private LinearKnobControl _rangeControl;
    private WaveSelector _waveSelector;
    private ButtonControl _frequencyRangeSelector;

    public SimpleLFOPanel() {
        super(ModuleManager.createModule(ModuleType.SimpleLFO), PanelWidth.SINGLE, "LFO");
    }

    @Override
    public void populateControls() {
        /*
         *    -----------
         *    |  Meter  |
         *    -----------
         *    |FreqDisp |
         *    -----------
         *    |Freq|PWdt|
         *    -----------
         *    |Rnge|Bias|
         *    -----------
         *    |Wave|FqRg|
         *    ----------|
         *    |    |    |
         *    ----------|
         *    |Rset|    |
         *    -----------
         */
        var section = getControlsSection();
        _cvMeter = new ControlValueIndicator("Output", Color.GREEN);
        section.setControlEntity(0, 0, _cvMeter);

        _frequencyDisplay = new FrequencyDisplay(new CellDimensions(2, 1), "Frequency", Color.GREEN);
        section.setControlEntity(0, 1, _frequencyDisplay);

//        _frequencyControl = new LinearKnobControl("Freq", Color.GRAY, new Range(0.01, 100.0));
//        section.setControlEntity(0, 2, _frequencyControl);

//        _pulseWidthControl = new LinearKnobControl("PWdth", Color.GRAY, new Range(0.01, 100.0));
//        section.setControlEntity(1, 2, _pulseWidthControl);

//        _biasControl = new LinearKnobControl("Bias", Color.GRAY, new Range(-5.0, 4.5));
//        section.setControlEntity(0, 3, _biasControl);

//        _rangeControl = new LinearKnobControl("Range", Color.GRAY, new Range(0.0, 10.0));
//        section.setControlEntity(1, 3, _rangeControl);

//        var buttonDim = new PixelDimensions(30, 30);
//        var button = new MomentaryButton(buttonDim, "Foo", Color.PURPLE);
        _waveSelector = new WaveSelector();
        _waveSelector.registerListener(this);
        section.setControlEntity(0, 4, _waveSelector);

//        _frequencyRangeSelector = new SelectorButtonControl("FqRng");
//        section.setControlEntity(1, 4, _frequencyRangeSelector);
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
        var op = getModule().getOutputPort(SimpleLFOModule.OUTPUT_PORT).getPort();
        var opValue = ((ContinuousOutputPort) op).getCurrentValue();
        _cvMeter.setValue(opValue);
        _frequencyDisplay.setValue(((SimpleLFOModule)_module).getFrequency());

        //  TODO other indicators which might need updated
    }

    @Override
    public void close() {
        _waveSelector.unregisterListener(this);
    }

    @Override
    public void notify(Message message) {
        //  something on the panel has been updated
        if (message instanceof WaveMessage wm) {
            getModule().setWave(wm.getNewWaveValue());
        }
    }
}
