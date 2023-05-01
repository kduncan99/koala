/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.components.signals.Oscillator;
import com.bearsnake.koala.messages.components.EncoderKnobComponentMessage;
import com.bearsnake.koala.messages.IListener;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.messages.controls.RangeTypeSelectorControlMessage;
import com.bearsnake.koala.modules.elements.ports.AnalogOutputPort;
import com.bearsnake.koala.modules.elements.controls.AnalogMeterIndicator;
import com.bearsnake.koala.modules.elements.controls.EncoderControl;
import com.bearsnake.koala.modules.elements.controls.RangeTypeSelectorButtonControl;
import javafx.scene.paint.Color;

public class SimpleLFOModule extends Module implements IListener {

    public static final String DEFAULT_NAME = "LFO";

    public static class SimpleLFOConfiguration extends Configuration {

        public SimpleLFOConfiguration(
            final int identifier,
            final String name
        ) {
            super(identifier, name);
        }
    }

    //  TODO possibile thoughts:
    //      Forget the range selector
    //      Lose the meter
    //      Add freq. display
    //      Replace Freq knob with Fine and Coarse knobs
    //      Add wave selector
    //      Add sync input (reset wave cycle)
    private static final CellDimensions METER_CELL_DIMENSIONS = new CellDimensions(1, 2);
    public static final int SIGNAL_OUTPUT_PORT_ID = 0;

    private EncoderControl _frequencyControl;
    private final Oscillator _oscillator = new Oscillator();
    private final RangeTypeSelectorButtonControl _rangeSelector;
    private final AnalogOutputPort _signalOutput;
    private final AnalogMeterIndicator _meterIndicator;

    public SimpleLFOModule(
        final String name
    ) {
        super(1, name);

        _oscillator.setFrequency(1.0);

        //  controls and indicators
        var controlsSection = getControlsSection();
        _meterIndicator = new AnalogMeterIndicator(METER_CELL_DIMENSIONS, "signal", Color.YELLOW);

        _frequencyControl = new EncoderControl("freq", Color.YELLOW);
        _frequencyControl.registerListener(this);
        _rangeSelector = new RangeTypeSelectorButtonControl();
        _rangeSelector.registerListener(this);

        controlsSection.setControl(0, 0, _meterIndicator);
        controlsSection.setControl(0, 2, _frequencyControl);
        controlsSection.setControl(0, 3, _rangeSelector);

        //  ports
        _signalOutput = new AnalogOutputPort("Control Output", "control");
        _ports.put(SIGNAL_OUTPUT_PORT_ID, _signalOutput);
        getPortsSection().setConnection(0, 1, _signalOutput);
    }

    @Override
    public synchronized void advance() {
        super.advance();
        _oscillator.advance();
        _signalOutput.setSignalValue(_oscillator.getValue());
    }

    @Override
    public void close() {}

    @Override
    public Configuration getConfiguration() {
        return new SimpleLFOConfiguration(getIdentifier(), getName());
    }

    @Override
    public void notify(
        final int senderIdentifier,
        final Message message
    ) {
        if (senderIdentifier == _frequencyControl.getIdentifier()) {
            if (message instanceof EncoderKnobComponentMessage msg) {
                var freq = _oscillator.getFrequency();
                switch (msg.getDirection()) {
                    case CLOCK_WISE -> freq *= 1.01;
                    case COUNTER_CLOCK_WISE -> freq *= 0.99;
                }
                freq = Koala.getBounded(0.01, freq, 100);
                _oscillator.setFrequency(freq);
            }
        } else if (senderIdentifier == _rangeSelector.getIdentifier()) {
            if (message instanceof RangeTypeSelectorControlMessage msg) {
                _oscillator.setRangeType(msg.getRangeTypeValue());
            }
        }
    }

    @Override
    public void repaint() {
        if (_oscillator != null) {
            _meterIndicator.setValue(_oscillator.getValue());
        }
    }

    @Override
    public void reset() {
        _oscillator.reset();
    }

    @Override
    public void setConfiguration(
        final Configuration configuration
    ) {
        if (configuration instanceof SimpleLFOConfiguration config) {
            setIdentifier(config._identifier);
            setName(config._name);
        }
    }

    public void setFrequency(
        final double frequency
    ) {
        _oscillator.setFrequency(frequency);
    }
}
