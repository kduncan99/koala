/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.LogicInputPort;

public class AREnvelopeModule extends Module {

    public static final int GATE_INPUT_PORT = 0;
    public static final int TRIGGER_INPUT_PORT = 1;
    public static final int SIGNAL_OUTPUT_PORT = 2;

    private float _attackTime = 0.0f;           //  in milliseconds
    private float _decrementPerSample = 0.0f;   //  in release mode
    private float _incrementPerSample = 0.0f;   //  in attack mode
    private boolean _manualGateOpen = false;
    private float _releaseTime = 0.0f;          //  in milliseconds
    private boolean _triggered = false;
    private float _value = Koala.MIN_CVPORT_VALUE;

    AREnvelopeModule() {
        _inputPorts.put(GATE_INPUT_PORT, new LogicInputPort("Gate", "GTE"));
        _inputPorts.put(TRIGGER_INPUT_PORT, new LogicInputPort("Trigger", "TRG"));
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new ContinuousOutputPort("Output", "OUT"));
        reset();
    }

    @Override
    public void advance() {
        LogicInputPort gateInput = (LogicInputPort) _inputPorts.get(GATE_INPUT_PORT);
        LogicInputPort triggerInput = (LogicInputPort) _inputPorts.get(TRIGGER_INPUT_PORT);
        _triggered = triggerInput.getValue();
        boolean effectiveGate = _triggered || _manualGateOpen || gateInput.getValue();
        if (effectiveGate) {
            if (_value < Koala.MAX_CVPORT_VALUE) {
                _value += _incrementPerSample;
                if (_value >= Koala.MAX_CVPORT_VALUE) {
                    _value = Koala.MAX_CVPORT_VALUE;
                    _triggered = false;
                }

                ContinuousOutputPort outputPort = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
                outputPort.setCurrentValue(_value);
            }
        } else {
            if (_value > Koala.MIN_CVPORT_VALUE) {
                _value -= _decrementPerSample;
                if (_value < Koala.MIN_CVPORT_VALUE) {
                    _value = Koala.MIN_CVPORT_VALUE;
                }

                ContinuousOutputPort outputPort = (ContinuousOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
                outputPort.setCurrentValue(_value);
            }
        }
    }

    @Override
    public void close() {}

    public float getAttackTime() {
        return _attackTime;
    }

    public boolean getManualGateOpen() {
        return _manualGateOpen;
    }

    @Override
    public String getModuleAbbreviation() {
        return "AR";
    }

    @Override
    public String getModuleClass() {
        return "AR Envelope Generator";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.AREnvelopeGenerator;
    }

    public float getReleaseTime() {
        return _releaseTime;
    }

    @Override
    public void reset() {
        _value = Koala.MIN_CVPORT_VALUE;
        _manualGateOpen = false;
    }

    public void setAttackTime(
        final float value
    ) {
        _attackTime = value;
        if (_attackTime <= 0.0) {
            _incrementPerSample = Koala.CVPORT_VALUE_RANGE;
        } else {
            _incrementPerSample = Koala.CVPORT_VALUE_RANGE / (_attackTime / 1000 * Koala.SAMPLE_RATE);
        }
    }

    public void setManualGateOpen(
        final boolean value
    ) {
        if (!_manualGateOpen && value) {
            _value = Koala.MIN_CVPORT_VALUE;
        }
        _manualGateOpen = value;
    }

    public void setTriggered() {
        _triggered = true;
        _value = 0.0f;
    }

    public void setReleaseTime(
        final float value
    ) {
        _releaseTime = value;
        if (_releaseTime <= 0.0) {
            _decrementPerSample = Koala.CVPORT_VALUE_RANGE;
        } else {
            _decrementPerSample = Koala.CVPORT_VALUE_RANGE / (_releaseTime / 1000 * Koala.SAMPLE_RATE);
        }
    }
}
