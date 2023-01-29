/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020, 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.LogicInputPort;

/**
 * Simple AR module, with output values ranging between 0.0 and 1.0
 */
public class AREnvelopeModule extends Module {

    public static final int GATE_INPUT_PORT = 0;
    public static final int TRIGGER_INPUT_PORT = 1;
    public static final int SIGNAL_OUTPUT_PORT = 2;

    private double _attackTime = 0.0f;           //  in milliseconds
    private double _decrementPerSample = 0.0f;   //  in release mode
    private double _incrementPerSample = 0.0f;   //  in attack mode
    private boolean _manualGateOpen = false;
    private double _releaseTime = 0.0f;          //  in milliseconds
    private boolean _triggered = false;
    private double _value = Koala.POSITIVE_RANGE.getLowValue();

    AREnvelopeModule() {
        _inputPorts.put(GATE_INPUT_PORT, new LogicInputPort());
        _inputPorts.put(TRIGGER_INPUT_PORT, new LogicInputPort());
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort());
        reset();
    }

    @Override
    public void advance() {
        LogicInputPort gateInput = (LogicInputPort) _inputPorts.get(GATE_INPUT_PORT);
        LogicInputPort triggerInput = (LogicInputPort) _inputPorts.get(TRIGGER_INPUT_PORT);
        //  TODO - debounce trigger input - basically, only react when it goes from low to high
        //  TODO        should we have a TriggerInputPort which does this automagically?
        //  TODO        it would have to extend the LogicInputPort...
        _triggered = triggerInput.getValue();
        boolean effectiveGate = _triggered || _manualGateOpen || gateInput.getValue();
        if (effectiveGate) {
            if (_value < Koala.POSITIVE_RANGE.getHighValue()) {
                _value = Koala.POSITIVE_RANGE.clipValue(_value + _incrementPerSample);
                _triggered = false;
                ContinuousOutputPort outputPort = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
                outputPort.setCurrentValue(_value);
            }
        } else {
            if (_value > Koala.POSITIVE_RANGE.getLowValue()) {
                _value = Koala.POSITIVE_RANGE.clipValue(_value -_decrementPerSample);
                ContinuousOutputPort outputPort = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
                outputPort.setCurrentValue(_value);
            }
        }
    }

    @Override
    public void close() {}

    public double getAttackTime() {
        return _attackTime;
    }

    public boolean getManualGateOpen() {
        return _manualGateOpen;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.AREnvelopeGenerator;
    }

    public double getReleaseTime() {
        return _releaseTime;
    }

    @Override
    public void reset() {
        _value = Koala.POSITIVE_RANGE.getLowValue();
        _manualGateOpen = false;
    }

    public void setAttackTime(
        final double value
    ) {
        _attackTime = value;
        if (_attackTime <= 0.0) {
            _incrementPerSample = Koala.POSITIVE_RANGE.getDelta();
        } else {
            _incrementPerSample = Koala.POSITIVE_RANGE.getDelta() / (_attackTime / 1000 * Koala.SAMPLE_RATE);
        }
    }

    public void setManualGateOpen(
        final boolean value
    ) {
        if (!_manualGateOpen && value) {
            _value = Koala.POSITIVE_RANGE.getLowValue();
        }
        _manualGateOpen = value;
    }

    public void setTriggered() {
        _triggered = true;
        _value = 0.0f;
    }

    public void setReleaseTime(
        final double value
    ) {
        _releaseTime = value;
        if (_releaseTime <= 0.0) {
            _decrementPerSample = Koala.POSITIVE_RANGE.getDelta();
        } else {
            _decrementPerSample = Koala.POSITIVE_RANGE.getDelta() / (_releaseTime / 1000 * Koala.SAMPLE_RATE);
        }
    }
}
