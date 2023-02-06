/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.DoubleRange;
import com.kadware.koala.messages.IListener;
import com.kadware.koala.messages.Message;
import com.kadware.koala.audio.modules.ModuleType;
import com.kadware.koala.audio.modules.ModuleManager;
import com.kadware.koala.audio.modules.SimpleLFOModule;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ui.components.messages.EncoderKnobMessage;
import com.kadware.koala.ui.panels.elements.connections.InputConnectionPane;
import com.kadware.koala.ui.panels.elements.connections.OutputConnectionPane;
import com.kadware.koala.ui.panels.elements.controls.*;
import com.kadware.koala.ui.panels.messages.PulseWidthMessage;
import com.kadware.koala.ui.panels.messages.WaveMessage;
import javafx.scene.paint.Color;

public class SimpleLFOPanel extends ModulePanel implements IListener {

    private static final int ID_COARSE_FREQUENCY = 1;
    private static final int ID_FINE_FREQUENCY = 2;
    private static final int ID_PULSE_WIDTH = 3;
    private static final int ID_WAVE_SELECTOR = 4;

    private static final DoubleRange PULSE_WIDTH_RANGE = new DoubleRange(0.1, 0.9);

    private ControlValueIndicator _cvMeter;
    private FrequencyDisplay _frequencyDisplay;
    private EncoderControl _frequencyCoarse;
    private EncoderControl _frequencyFine;
    private LabeledPotentiometerControl _pulseWidth;
    private WaveSelectorControl _waveSelectorControl;

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
         *    |Crse|Fine|
         *    -----------
         *    |Wave|Pwdt|
         *    -----------
         *    |    |    |
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

        _frequencyCoarse = new EncoderControl(ID_COARSE_FREQUENCY, "Crse", Color.GRAY);
        _frequencyCoarse.registerListener(this);
        section.setControlEntity(0, 2, _frequencyCoarse);

        _frequencyFine = new EncoderControl(ID_FINE_FREQUENCY, "Fine", Color.GRAY);
        _frequencyFine.registerListener(this);
        section.setControlEntity(1, 2, _frequencyFine);

        _waveSelectorControl = new WaveSelectorControl(ID_WAVE_SELECTOR);
        _waveSelectorControl.registerListener(this);
        section.setControlEntity(0, 3, _waveSelectorControl);

        _pulseWidth = new LabeledPotentiometerControl(ID_PULSE_WIDTH, "Width", Color.GRAY, PULSE_WIDTH_RANGE);
        _pulseWidth.registerListener(this);
        section.setControlEntity(1, 3, _pulseWidth);
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
        var mod = getModule();

        var op = mod.getOutputPort(SimpleLFOModule.OUTPUT_PORT).getPort();
        var opValue = ((ContinuousOutputPort) op).getCurrentValue();
        _cvMeter.setValue(opValue);
        _frequencyDisplay.setValue(mod.getFrequency());
        _pulseWidth.setValue(mod.getBasePulseWidth());

        //  TODO other indicators which might need updated
    }

    @Override
    public void close() {
        _waveSelectorControl.unregisterListener(this);
    }

    @Override
    public void notify(
        final Message message
    ) {
        //  something on the panel has been updated
        switch (message.getIdentifier()) {
            case ID_COARSE_FREQUENCY -> handleCoarseFrequencyMessage((EncoderKnobMessage) message);
            case ID_FINE_FREQUENCY -> handleFineFrequencyMessage((EncoderKnobMessage) message);
            case ID_PULSE_WIDTH -> handlePulseWidthMessage((PulseWidthMessage) message);
            case ID_WAVE_SELECTOR -> handleWaveSelectorMessage((WaveMessage) message);
        }
    }

    private void handleCoarseFrequencyMessage(
        final EncoderKnobMessage message
    ) {
        var currentFreq = getModule().getFrequency();

        double change;
        if (currentFreq <= 1.0)
            change = 0.1;
        else if (currentFreq <= 10.0)
            change = 1.0;
        else
            change = 10.0;

        currentFreq += switch (message.getDirection()) {
            case CLOCK_WISE -> change;
            case COUNTER_CLOCK_WISE -> -change;
        };

        getModule().setFrequency(currentFreq);
    }

    private void handleFineFrequencyMessage(
        final EncoderKnobMessage message
    ) {
        var currentFreq = getModule().getFrequency();
        currentFreq += switch (message.getDirection()) {
            case CLOCK_WISE -> 0.01;
            case COUNTER_CLOCK_WISE -> -0.01;
        };

        getModule().setFrequency(currentFreq);
    }

    private void handlePulseWidthMessage(
        final PulseWidthMessage message
    ) {
        getModule().setPulseWidth(message.getValue());
        _pulseWidth.setValue(message.getValue());
    }

    private void handleWaveSelectorMessage(
        final WaveMessage message
    ) {
        getModule().setWave(message.getNewWaveValue());
    }
}
