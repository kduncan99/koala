/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.modules.Module;
import com.kadware.koala.modules.ModuleManager;
import com.kadware.koala.modules.SimpleLFOModule;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ui.panels.elements.connections.InputConnectionPane;
import com.kadware.koala.ui.panels.elements.connections.OutputConnectionPane;
import com.kadware.koala.ui.panels.elements.controls.*;
import com.kadware.koala.waves.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class SimpleLFOPanel extends ModulePanel {

    private static final String RESOURCE_PATH = "resources/";

    private enum WaveId {
        SINE("SineWave.jpg", WaveManager.createWave(WaveType.SINE)),
        TRIANGLE("TriangleWave.jpg", WaveManager.createWave(WaveType.TRIANGLE)),
        RAMP("RampWave.jpg", WaveManager.createWave(WaveType.RAMP)),
        SAWTOOTH("SawtoothWave.jpg", WaveManager.createWave(WaveType.SAWTOOTH)),
        SQUARE("SquareWave.jpg", WaveManager.createWave(WaveType.SQUARE));

        public final String _fileName;
        public final IWave _wave;
        public final Image _image;

        WaveId(
            final String fileName,
            final IWave wave
        ) {
            _fileName = fileName;
            _wave = wave;
            try {
                _image = new Image(new FileInputStream(new File(RESOURCE_PATH + fileName)));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public static Image[] getImages() {
            return Arrays.stream(values()).map(waveId -> waveId._image).toArray(Image[]::new);
        }
    }

    private ControlValueIndicator _cvMeter;
    private FrequencyDisplay _frequencyDisplay;
    private LinearKnobControl _frequencyControl;
    private LinearKnobControl _pulseWidthControl;
    private LinearKnobControl _biasControl;
    private LinearKnobControl _rangeControl;
    private ButtonControl _waveSelector;
    private ButtonControl _frequencyRangeSelector;

    public SimpleLFOPanel() {
        super(ModuleManager.createModule(Module.ModuleType.SimpleLFO), PanelWidth.SINGLE, "LFO");
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

//        var waveButton = new SelectorButton(new PixelDimensions(30, 30), WaveId.getImages());
//        var waveButton = new ImageButton(new PixelDimensions(30, 30), Color.GRAY, null);
//        _waveSelector = new ButtonControl("Wave", waveButton);
//        section.setControlEntity(0, 4, _waveSelector);

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
}
